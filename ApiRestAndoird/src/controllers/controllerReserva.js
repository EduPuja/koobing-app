const connection = require("../database/conexio");



/*function setReserva(req, res) {
  console.log("\nCreant una reserva");
  const reserva = req.body;

 // console.log("Datos de la reserva:", reserva);

  //dades llibre
  
  const libroISBN = reserva.llibre.ISBN;
  const libroTitulo = reserva.llibre.titol;
  const libroVersion = reserva.llibre.versio;


  

  //dades usuari
  const usuarioNombre = reserva.usuari.nom;
  const usuarioApellido = reserva.usuari.cognom;
  const usuarioEmail = reserva.usuari.email;

  /*console.log("ISBN del libro:", libroISBN);
  console.log("Título del libro:", libroTitulo);
  console.log("Versión del libro:", libroVersion);
  console.log("Nombre del usuario:", usuarioNombre);
  console.log("Apellido del usuario:", usuarioApellido);
  console.log("Email del usuario:", usuarioEmail);*/

  
 /* console.log("\nCreant una reserva");
  const reserva = req.body;

  const query = "INSERT INTO reserves (id_reserva, id_usuari, id_treballador, id_biblioteca, ISBN, data_inici, data_fi) VALUES (?, ?, ?, ?, ?, ?, ?)";

  // Definir los valores de la reserva
  const valores = [reserva.id_reserva, reserva.id_usuari, reserva.id_treballador, reserva.id_biblioteca, reserva.ISBN, reserva.data_inici, reserva.data_fi];

  // Ejecuta la consulta en la conexión a la base de datos
  connection.query(query, valores, (error, results) => {
    if (error) {
      console.error("Error al insertar la reserva:", error.message);
      res.status(500).json({ message: "Error al insertar la reserva" });
    } else {
      console.log("Reserva insertada correctamente");
      res.status(200).json({ message: "Reserva insertada correctamente" });
    }
  });*/
//}


function setReserva(req, res) {
  console.log("\nCreando una reserva");
  const reserva = req.body;

  //console.log("Datos de la reserva:", reserva);

  // Acceder a los valores individuales del libro
  const libroISBN = reserva.llibre.ISBN;
  const libroTitulo = reserva.llibre.titol;
  const libroVersion = reserva.llibre.versio;

  // Acceder a los valores individuales del usuario
  const usuarioNombre = reserva.usuari.nom;
  const usuarioApellido = reserva.usuari.cognom;
  const usuarioEmail = reserva.usuari.email;

  // Insertar los valores en la consulta
  const query = "INSERT INTO reserves (id_reserva, id_usuari, id_treballador, id_biblioteca, ISBN, data_inici, data_fi) VALUES (?, ?, ?, ?, ?, ?, ?)";

  // Definir los valores de la reserva
  const valores = [
    reserva.idReserva,
    reserva.usuari.id,
    reserva.treballador.id,
    /* Agrega aquí el valor correspondiente a id_biblioteca */,
    libroISBN,
    reserva.dataInici,
    reserva.dataFI
  ];

  // Ejecuta la consulta en la conexión a la base de datos
  connection.query(query, valores, (error, results) => {
    if (error) {
      console.error("Error al insertar la reserva:", error.message);
      res.status(500).json({ message: "Error al insertar la reserva" });
    } else {
      console.log("Reserva insertada correctamente");
      res.status(200).json({ message: "Reserva insertada correctamente" });
    }
  });


}


module.exports = {
  setReserva,
};

