const connection = require("../database/conexio");


function getAllBiblioteques(req,res)
{
    console.log("Getting all the Libraris")
    const sql = "SELECT * FROM biblioteca"

    connection.query(sql,(error, result) => {

        if(error) 
        {
            console.error("Error al obtener la lista de BIBLIOTECAS: ", error);
            res.status(500).send("Error interno del servidor");
        }
        else{
           res.json(result);
        }
    });
}


module.exports = {
    getAllBiblioteques
}