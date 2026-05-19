import { useState } from "react";
import { NavLink } from "react-router-dom";

const soloLetrasEspacios = (nombre: string) => /^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$/.test(nombre);
const UserEmail = (correo: string) => /^[A-Za-z0-9-_.]+@gmail.com$/.test(correo);

export const Home = () => {
  const [nombreValido, setNombreValido] = useState<boolean | null>(null);
    const [apellidoValido, setApellidoValido] = useState<boolean | null>(null);
    const [correoValido, setCorreoValido] = useState<boolean | null>(null);
    const [mensajeValido, setMensajeValido] = useState<boolean | null>(null);

    const [form, setForm] = useState({
        nombre: "",
        apellido: "",
        correo: "",
        mensaje: ""
    })

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value, id } = e.target;

        setForm({ ...form, [name]: value});

        if(id === "nombre") {
            if (soloLetrasEspacios(value.trim()) && value.trim().length <= 45 && value.trim().length > 0) {
                setNombreValido(true);
            } else {
                setNombreValido(false);
            }
            if(value.trim().length === 0) setNombreValido(null);
        }

        if(id === "apellido") {
            if (soloLetrasEspacios(value.trim()) && value.trim().length <= 45 && value.trim().length > 0) {
                setApellidoValido(true);
            } else {
                setApellidoValido(false);
            }
            if(value.trim().length === 0) setApellidoValido(null);
        }

        if(id === "correo") {
            if (UserEmail(value.trim()) && value.trim().length <= 50 && value.trim().length > 0) {
                setCorreoValido(true);
            } else {
                setCorreoValido(false);
            }
            if(value.trim().length === 0) setCorreoValido(null);
        }

        if(id === "mensaje") {
            if (value.trim().length <= 200 && value.trim().length > 3) {
                setMensajeValido(true);
            } else {
                setMensajeValido(false);
            }
            if(value.trim().length === 0) setMensajeValido(null);
        }
    }

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            alert("Mensaje enviado");
        } catch (error) {
            alert("Error al enviar el mensaje");
            console.error(error);
        }
    }
 
  return (
    <>
      <header className="py-5 text-center">
        <div className="container">
          <h1 className="display-5">Bienvenido a la plataforma de Smarthlogix</h1>
          <p className="">Un sistema de logística inteligente en tiempo real con microservicios integrado.
            Sin trampas, sin herramientas inutiles ni obsoletas, todo de manera asincronica y moderna. Puedes probarlo hoy mismo pulsando el boton de abajo.</p>
          <NavLink to="/test" className="nav-link">
              <button type="button" className="btn btn-outline-secondary">Probar ahora</button>
            </NavLink>
        </div>
      </header>

      <div className="container"><hr className="border-3"/></div>

      <section className="container" id="estadisticas">
        <div className="row justify-content-center">
          <div className="col-lg-12 text-center py-3">
            <h2>Estadísticas</h2>
          </div>
          <div className="col-4 text-center ">
            <i className="bi bi-download fs-1"></i>
            </div>
            <div className="col-4 text-center ">
              <i className="bi bi-fast-forward-fill fs-1"></i>
              </div>
            <div className="col-4 text-center ">
              <i className="bi bi-diagram-3 fs-1"></i>
              </div>
              <div className="col-4 text-center">
            99,9% uptime
            </div>
            <div className="col-4 text-center">
              +500 eventos/min
              </div>
            <div className="col-4 text-center">
              -80% Latencia
              </div>
        </div>
      </section>

      <div className="container"><hr className="border-3"/></div>
      

      <section className="container py-4" id="microservicios">
        <div className="row">
          <div className="col-lg-12 text-center py-3">
            <h2>Catalogo de servicios a disposición</h2>
          </div>
          <div className="col-4 text-center">
            <div className="card card-gray text-white">
              <i className="bi bi-database fs-1 py-5"></i>
              <div className="card-body">
                <h5 className="card-title">Inventario</h5>
                <p className="card-text">Un apartado para poder guardar todos tus productos, con la ayuda de una base de datos conectada</p>
              </div>
              </div>
            </div>
            <div className="col-4 text-center">
            <div className="card card-gray text-white">
              <i className="bi bi-cart fs-1 py-5"></i>
              <div className="card-body">
                <h5 className="card-title">Pedido</h5>
                <p className="card-text">Se le solicita los productos que se han elegido por el cliente, y despues procede a la trasnferencia de pago</p>
              </div>
              </div>
            </div>
            <div className="col-4 text-center">
            <div className="card card-gray text-white">
              <i className="bi bi-truck fs-1 py-5"></i>
              <div className="card-body">
                <h5 className="card-title">Envío</h5>
                <p className="card-text">Luego de haber pagado por los productos, se envia una notificacion al cliente y luego se envia el producto a su domicilio</p>
              </div>
              </div>
            </div>
        </div>
      </section>

      <div className="container"><hr className="border-3"/></div>

      <section className="container py-5" id="caracteristicas">
        <div className="row">
          <div className="col-12 text-center py-2">
            <h2>Características adicionales</h2>
          </div>
          <div className="col-7 py-3">
            <h4>ApiGateway</h4>
            <p>Funciona como la unica puerta de entrada al sistema, pudiendo controlar el trafico y proteger los microservicios</p>
          </div>
          <div className="col-5 py-3">
            (imagen de referencia)
          </div>
          <div className="col-7 py-3">
            <h4>EurekaServer</h4>
            <p>Es el centro donde se gestiona el levantamiento de cada microservicio, incluido otras herramientas de backend. Sincroniza en tiempo real</p>
          </div>
          <div className="col-5 py-3">
            (imagen de referencia)
          </div>
          <div className="col-7 py-3">
            <h4>KeyCloackAdapter</h4>
            <p>Maneja la autenticación por medio de los Tokens JWT, inicio de sesión y protección de datos</p>
          </div>
          <div className="col-5 py-3">
            (imagen de referencia)
          </div>
          <div className="col-7 py-3">
            <h4>SpringBootAdmin</h4>
            <p>Se encarga de monitorear el sistema, como los estados de los beans, Cpu y Ram, junto con el Uptime</p>
          </div>
          <div className="col-5 py-3">
            (imagen de referencia)
          </div>
        </div>
      </section>

      <section className="container py-5" id="resenas">
        <div className="container text-center">
          <h2>Reseñas</h2>
          <p>En este apartado se presentan los comentarios de parte de nuestros clientes y medios especializados</p>
        </div>
       <div id="carouselExample" className="carousel slide" data-bs-ride="carousel">
        <div className="carousel-inner">
          <div className="carousel-item active">
            <img src="..." className="d-block w-100" alt="..."/>
          </div>
          <div className="carousel-item">
            <img src="..." className="d-block w-100" alt="..."/>
          </div>
          <div className="carousel-item">
            <img src="..." className="d-block w-100" alt="..."/>
          </div>
        </div>
        <button className="carousel-control-prev" type="button" data-bs-target="#carouselExample" data-bs-slide="prev">
          <span className="carousel-control-prev-icon" aria-hidden="true"></span>
          <span className="visually-hidden">Previous</span>
        </button>
        <button className="carousel-control-next" type="button" data-bs-target="#carouselExample" data-bs-slide="next">
          <span className="carousel-control-next-icon" aria-hidden="true"></span>
          <span className="visually-hidden">Next</span>
        </button>
      </div>
      </section>

      <section className="py-5 transparent" id="contacto">
                <div className="container"> 
                    <h1 className="text-center py-3">Contacto</h1>
                    <p className="text-center py-1">Si tienes dudas en cuanto a nuestra pagina o las herramientas que se ocupan, nos dudes en contactarnos</p>
                        <form id="sendMessage" className="needs-validation row g-3 justify-content-center" onSubmit={handleSubmit}>
                            <div className="col-md-4">
                                <label className="form-label">Nombre</label>
                                <input type="text" name="nombre" id="nombre" className={`form-control 
                                    ${nombreValido === true ? 'is-valid' : ''}
                                    ${nombreValido === false ? 'is-invalid' : ''}`
                                } 
                                value={form.nombre}
                                onChange={handleChange}
                                placeholder="Ingrese su nombre" required/>
                                {nombreValido === false && (
                                    <div className="invalid-feedback">
                                        Solo se permiten letras y maximo 45 caracteres
                                    </div>
                                )}
                            </div>

                            <div className="col-md-4">
                                <label className="form-label">Apellido</label>
                                <input type="text" name="apellido" id="apellido" className={`form-control 
                                    ${apellidoValido === true ? 'is-valid' : ''}
                                    ${apellidoValido === false ? 'is-invalid' : ''}`
                                } 
                                value={form.apellido} onChange={handleChange} placeholder="Ingrese su apellido" required/>
                                {apellidoValido === false && (
                                    <div className="invalid-feedback">
                                        Solo se permiten letras y maximo 45 caracteres
                                    </div>
                                )}

                            </div>

                            <div className="col-md-8">
                                <label className="form-label">Correo</label>
                                <input type="email" id="correo" name="correo" className={`form-control 
                                    ${correoValido=== true ? 'is-valid' : ''}
                                    ${correoValido === false ? 'is-invalid' : ''}`
                                } onChange={handleChange} placeholder="ejemplo@correo.com" required/>
                                {correoValido === false && (
                                    <div className="invalid-feedback">
                                        Correo invalido
                                    </div>
                                )}
                            </div>

                            <div className="col-md-8">
                                <label className="form-label">Mensaje</label>
                                <textarea name="mensaje" id="mensaje" className={`form-control 
                                    ${mensajeValido=== true ? 'is-valid' : ''}
                                    ${mensajeValido === false ? 'is-invalid' : ''}`
                                } onChange={handleChange} placeholder="Ingrese su mensaje aqui" required/>
                                {mensajeValido === false && (
                                    <div className="invalid-feedback">
                                        Mensaje demasiado corto o demasiado largo
                                    </div>
                                )}
                            </div>

                            <div className="col-5 text-center p-3 ">
                                <button type="submit" className="btn btn-outline-secondary">Enviar Mensaje</button>
                            </div>
                        </form>
                </div>
            </section>

    </>
  )
}
