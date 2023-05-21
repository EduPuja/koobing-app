const express = require("express");
const app = express();
//const connection = require("./database/conexio");
const controllerUsuario= require("./controllers/controllerUsuario");

//utilzar jsons
app.use(express.json());
app.get("/users",controllerUsuario.getAllUsers);

app.get("/user",controllerUsuario.getUserByEmail)

app.listen(3000, () => {
  console.log("La aplicación está corriendo en el puerto 3000.");
});