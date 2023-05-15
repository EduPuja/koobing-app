const mysql = require("mysql");

// Crear la conexión a la base de datos
const connection = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "",
  database: "koobing_app",
});

// Conectar a la base de datos
connection.connect((error) => {
  if (error) {
    console.error("Error al conectarse a la base de datos: ", error);
  } else {
    console.log("Conexión a la base de datos establecida.");
  }
});

// Exportar la conexión
module.exports = connection;