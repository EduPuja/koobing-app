const connection = require("../database/conexio");



function setReserva(req, res) {
  console.log("\nCreando una reserva");
  const reserva = req.body;

  // Obtener los valores de la reserva
  const dataFI = reserva.dataFI;
  const estat = reserva.estat;
  const idReserva = reserva.idReserva;
  const llibreISBN = reserva.llibre.ISBN;
  const treballadorId = reserva.treballador.id;
  const usuariId = reserva.usuari.id;
  

  // Mostrar los valores específicos de la reserva
  console.log("Data FI:", dataFI);
  console.log("Estat:", estat);
  console.log("ID de Reserva:", idReserva);
  console.log("ISBN del Libro:", llibreISBN);
  console.log("ID del Trabajador:", treballadorId);
  console.log("ID del Usuario:", usuariId);

  // Enviar una respuesta al cliente
  res.status(200).json({ message: "Reserva creada correctamente" });
}


function getLlibresReservats(req,res)
{
  console.log("\nLlistat de llibres en Estat: reservat");
  const usuari = req.params.id_usuari;
  
  const query = "Select * from reserva where id_estat = 1 and id_usuari= ?";
  connection.query(query,usuari,(error, results) => {
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

