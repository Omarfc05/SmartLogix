describe('Flujo de Compra SmartLogix', () => {

  it('Debería navegar, agregar un producto y completar el checkout exitosamente', () => {
    // 1. Entrar al Home y verificar que cargue
    cy.visit('/');
    cy.contains('Bienvenido a SmartLogix').should('be.visible');

    // 2. Ir al catálogo usando el botón del Home
    cy.contains('Explorar Catálogo').click();

    // 3. Esperar a que el spinner desaparezca y carguen los productos
    cy.contains('Cargando catálogo...').should('not.exist');
    cy.contains('Nuestros Productos').should('be.visible');

    // 4. Agregar el primer producto disponible al carrito
    cy.contains('Agregar al carrito').first().click();

    // 5. Navegar al carrito haciendo clic en el Navbar (¡NO USAR cy.visit!)
    // Reemplaza 'Carrito' por el texto exacto que tenga el botón en tu menú superior
    cy.contains('Carrito').click();

    // 6. Verificar que estamos en el carrito y proceder
    cy.contains('Tu carrito').should('be.visible');
    cy.contains('Proceder al pago').click();

    // 7. Llenar el formulario de Checkout usando tus placeholders exactos
    cy.contains('Checkout Seguro').should('be.visible');
    cy.get('input[placeholder="Tu Nombre Completo"]').type('Omar Filun');
    cy.get('input[placeholder="Dirección de Envío (Ej: Los Leones 123)"]').type('La Cisterna, Santiago');

    // 8. Confirmar la compra
    cy.contains('Confirmar y Pagar').click();

    // 9. Verificar el mensaje de éxito dinámico con el nombre que ingresamos
    cy.contains('¡Pago exitoso, Omar Filun!').should('be.visible');
    cy.contains('La Cisterna, Santiago').should('be.visible');
  });

  it('Debería mostrar un error si se intenta pagar sin ingresar datos de envío', () => {
    cy.visit('/');
    cy.contains('Explorar Catálogo').click();
    cy.contains('Cargando catálogo...').should('not.exist');
    cy.contains('Agregar al carrito').first().click();

    cy.get('a[href="/cart"]').click(); // Asumiendo que usas este selector para el navbar
    cy.contains('Proceder al pago').click();

    // Hacemos clic en pagar directamente SIN llenar los inputs
    cy.contains('Confirmar y Pagar').click();

    // Verificamos que tu estado de error funcione y muestre el mensaje exacto
    cy.contains('Por favor, ingresa tu nombre y dirección.').should('be.visible');
  });

  it('Debería permitir vaciar el carrito y mostrar la vista de carrito vacío', () => {
    cy.visit('/');
    cy.contains('Explorar Catálogo').click();
    cy.contains('Cargando catálogo...').should('not.exist');

    // Agregamos un producto
    cy.contains('Agregar al carrito').first().click();
    cy.get('a[href="/cart"]').click();

    // Verificamos que estamos en el carrito con al menos un ítem
    cy.contains('Tu carrito').should('be.visible');

    // Hacemos clic en el botón de vaciar
    cy.contains('Vaciar carrito').click();

    // Verificamos que aparezca tu componente <EmptyCart />
    cy.contains('¡Tu carrito está esperando!').should('be.visible');
    cy.contains('No hay productos por aquí').should('be.visible');
  });

  it('Debería bloquear el acceso al checkout si el carrito está vacío', () => {
    // Forzamos la visita directa a la ruta de pago
    cy.visit('/checkout');

    // Verificamos que tu condición de items.length === 0 funcione
    cy.contains('No hay nada que pagar').should('be.visible');
    cy.contains('Tu carrito está vacío.').should('be.visible');
  });

  // El Administrador en Acción (Auditoría de Stock)
  it('Debería permitir reponer stock y registrar el movimiento en el historial', () => {
    // 1. Entramos directo al panel de admin
    cy.visit('/admin');

    // Verificamos que la página cargó correctamente
    cy.contains('Panel de Administración').should('be.visible');
    cy.contains('Reposición e Historial').should('be.visible');

    // 2. Opcional: Si el test 1 dejó una orden "CREADO", la aprobamos
    // Usamos un bloque condicional seguro por si la base de datos está vacía
    cy.get('body').then(($body) => {
      if ($body.find('button:contains("✅ Aprobar y Despachar")').length > 0) {
        cy.contains('✅ Aprobar y Despachar').first().click();
      }
    });

    // 3. Escribimos 25 unidades en el primer input de stock
    cy.get('input[placeholder="0"]').first().type('25');

    // 4. Hacemos clic en el botón de suma (➕)
    cy.get('button[title="Agregar Stock"]').first().click();

    // 5. Abrimos el pergamino del historial (📜)
    cy.get('button[title="Ver Historial"]').first().click();

    // 6. Verificamos que el sistema auditó el ingreso correctamente
    cy.contains('Últimos Movimientos').should('be.visible');

    // Buscamos que aparezca el +25 en color verde indicando el ingreso (IN)
    cy.contains('+25').should('be.visible');
  });

});