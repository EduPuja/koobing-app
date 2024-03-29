const os = require('os');
const express = require("express");
const app = express();
const bodyParser = require('body-parser');

//controllers
const controllerUsuario = require("./controllers/controllerUsuario");
const controllerLlibre = require("./controllers/controllerLLibre");
const reservaController = require("./controllers/controllerReserva");
const controllerAutor= require("./controllers/controllerAutor");
const controllerEditorial = require("./controllers/controllerEditorial");
const controllerIdioma = require("./controllers/controllerIdioma");
const controllerGenere = require("./controllers/controllerGenere");



// Configuración de la API
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

// ---- RUTAS ---- //

//usuaris
app.get("/users", controllerUsuario.getAllUsers);
app.get("/user/:email", controllerUsuario.getUserByEmail);
app.post("/register",controllerUsuario.registerUser);

//llibres
app.get("/books", controllerLlibre.getAllLlibres);
app.get("/books_10", controllerLlibre.getTop10Llibres);
app.get("/book/:isbn", controllerLlibre.getLlibreByIsbn)

//reserva
app.post("/reservarLlibre",reservaController.setReserva);
app.get("/llibresReservats/:id_usuari",reservaController.getLlibresReservats);  //llibres reservats
app.get("/llibresCancelats/:id_usuari",reservaController.getLlibresCancelats);  // llibres cancelts
app.get("/llibresTornats/:id_usuari",reservaController.getLlibresTornats);       // llibres tornats
app.get("/llibresPrestec/:id_usuari",reservaController.getLlibresPrestec);       // llibres prestec


//autor
app.get("/autor/:idAutor", controllerAutor.getAutorByID);

//editorail
app.get("/editor/:idEditorial",controllerEditorial.getEditroialById);

//idioma
app.get("/idioma/:idIdioma",controllerIdioma.getIdiomaById);

//genere
app.get("/genere/:idGenere",controllerGenere.getGenereById);


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
