const connection = require("../database/conexio");



function setReserva(req, res) {
  console.log("\nCreando una reserva");
  const reserva = req.body;

  // Obtener los valores de la reserva
  const dataInici = new Date();
  //convertirn la data de finalizacio en un tipus date per que es pugui insertar correctament
  const dataFIString = reserva.dataFI;
  const dataFI = new Date(Date.parse(dataFIString));
  
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

  // Consulta SQL con placeholders
  const query = `INSERT INTO reserva (id_prestec, ISBN, id_usuari, id_treballador, data_inici, data_fi, id_estat) 
                 VALUES (?, ?, ?, ?, ?, ?, ?)`;
  
  // Valores a insertar
  const values = [idReserva, llibreISBN, usuariId, treballadorId, dataInici, dataFI, estat];

  // Ejecutar la consulta preparada
  connection.query(query, values, (error, result) => {
    if (error) {
      console.error("Error al insertar reserva:", error.message);
      res.status(500).json({ message: "Error al insertar reserva" });
    } else {
      console.log("Reserva insertada correctamente");
      res.status(200).json({ message: "Reserva creada correctamente" });
    }
  });

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

