const express = require("express");
const app = express();
const bodyParser = require('body-parser');
//const connection = require("./database/conexio");
const controllerUsuario= require("./controllers/controllerUsuario");


// Configura el middleware body-parser para analizar los datos enviados en las solicitudes
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());


//utilzar jsons
//app.use(express.json());
app.get("/users",controllerUsuario.getAllUsers);

app.get("/user/:email",controllerUsuario.getUserByEmail)

app.listen(3000, () => {
  console.log("La aplicación está corriendo en el puerto 3000.");
});