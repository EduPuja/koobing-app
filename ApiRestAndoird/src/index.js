const express = require("express");
const app = express();
const bodyParser = require('body-parser');
const controllerUsuario= require("./controllers/controllerUsuario");
const controllerBiblio = require("./controllers/controllerBiblioteca");
const controllerLlibre = require("./controllers/controllerLLibre");


// Configuration de la api
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());





//rutes 
app.get("/users",controllerUsuario.getAllUsers);

app.get("/user/:email",controllerUsuario.getUserByEmail)

app.get("/biblioteques",controllerBiblio.getAllBiblioteques)

app.get("/books",controllerLlibre.getAllLlibres)

app.listen(3000, () => {
  console.log("L' aplicació està funcionant  per el port: 3000");
});