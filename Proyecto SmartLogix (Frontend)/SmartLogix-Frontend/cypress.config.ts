import { defineConfig } from "cypress";

export default defineConfig({
  e2e: {
    baseUrl: 'http://localhost:5173', // El puerto de tu Vite
    setupNodeEvents(on, config) {
      // implement node event listeners here
    },
  },
});