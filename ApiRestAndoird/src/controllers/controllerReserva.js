const connection = require("../database/conexio");



function setReserva(req, res) {
  console.log("\nCreando una reserva");
  const reserva = req.body;

  console.log("Info reserva: ",reserva);

  
  //const libroISBN = reserva.llibre.ISBN;


 
  const data_inici = new Date();
  //const data_fi = new Date();

  //data_fi.setMonth(data_fi.getMonth() + 1);

  // Insertar los valores en la consulta
  //const query = "INSERT INTO reserves (id_reserva, id_usuari, id_treballador, id_biblioteca, ISBN, data_inici, data_fi) VALUES (?, ?, ?, ?, ?, ?, ?)";


  // Definir los valores de la reserva
  /*const valores = [
    reserva.idReserva,
    reserva.usuari.id,
    reserva.treballador.id,
    libroISBN,
    data_inici,
    data_fi
    
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
  });*/


}

function getLlibresReservats(req,res)
{
  console.log("\nLlistat de llibres en estat reservat");
  const query = "Select * from reserva where id_estat = 1";
  connection.query(query, (error, results) => {
    if(error)
    {
      console.error("Error al buscar el llistat de llibres reservats:" + error.message);
    }
    else{
      res.json(results)
    }

  });
}


module.exports = {
  setReserva,
  getLlibresReservats
};

