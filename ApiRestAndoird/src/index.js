const express = require("express");
const bodyParser = require("body-parser");
const cors = require("cors");
const mysql = require("mysql");

const app = express();

//connexion mysql

const con = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "",
  database: "koobing_app",
});

// crear la connexion
con.connect((error) => {
  if (error) {
    console.error("Error al conectarse a la base de datos: ", error);
  } else {
    console.log("Conexión a la base de datos establecida.");
  }
});

// Habilitar cors
app.use(cors());

// Parsear solicitudes con body-parser
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

//ruta de usuarios
app.get("/users", (req, res) => {
  // Consulta para obtener la lista de usuarios
  const sql = "SELECT * FROM usuari";

  con.query(sql, (error, results) => {
    if (error) {
      console.error("Error al obtener la lista de usuarios: ", error);
      res.status(500).send("Error interno del servidor");
    } else {
      res.json(results);
      console.log("Successs! ",results);
    }
  });
});

// Escuchar el puerto 3000
app.listen(3000, () => {
  console.log("La aplicación está corriendo en el puerto 3000.");
});
