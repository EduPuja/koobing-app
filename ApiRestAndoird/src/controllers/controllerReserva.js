const connection = require("../database/conexio");



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
    reserva.id_biblioteca,
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

