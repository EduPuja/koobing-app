const connection = require("../database/conexio");


function setReserva(req,res)
{
    console.log("\nCreant una reserva")
    const reserva = req.body;

    const query = "INSERT INTO reserves (id_reserva, id_usuari, id_treballador, id_biblioteca, ISBN, data_inici, data_fi) VALUES (?, ?, ?, ?, ?, ?, ?)";

    //definir els valors de la reserva
    const valores = [reserva.id_reserva, reserva.id_usuari, reserva.id_treballador, reserva.id_biblioteca, reserva.ISBN, reserva.data_inici, reserva.data_fi];
    // Ejecuta la consulta en la conexión a la base de datos
  connection.query(query, valores, (error, results) => {
    if (error) {
      console.error("Error al insertar la reserva:", error);
      res.status(500).json({ message: "Error al insertar la reserva" });
    } else {
      console.log("Reserva insertada correctamente");
      res.status(200).json({ message: "Reserva insertada correctamente" });
    }
  });

}

module.exports = {
    setReserva
}