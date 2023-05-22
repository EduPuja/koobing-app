const os = require('os');
const express = require("express");
const app = express();
const bodyParser = require('body-parser');
const controllerUsuario = require("./controllers/controllerUsuario");
const controllerBiblio = require("./controllers/controllerBiblioteca");
const controllerLlibre = require("./controllers/controllerLLibre");

// Configuración de la API
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

// Rutas
app.get("/users", controllerUsuario.getAllUsers);
app.get("/user/:email", controllerUsuario.getUserByEmail);
app.get("/biblioteques", controllerBiblio.getAllBiblioteques);
app.get("/books", controllerLlibre.getAllLlibres);

const port = 3000;
const server = app.listen(port, () => {
  const serverAddress = server.address();
  const ip = serverAddress.address === "::" ? getLocalIpAddress() : serverAddress.address;

  console.log(`La aplicación está funcionando en la dirección IP: ${ip}, puerto: ${port}`);
});

function getLocalIpAddress() {
  const interfaces = os.networkInterfaces();

  for (const iface in interfaces) {
    const addresses = interfaces[iface];
    for (const address of addresses) {
      if (address.family === 'IPv4' && !address.internal) {
        return address.address;
      }
    }
  }

  return 'Dirección IP no encontrada';
}
