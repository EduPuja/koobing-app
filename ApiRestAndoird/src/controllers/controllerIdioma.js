const connection = require("../database/conexio");


function getIdiomaById(req,res)
{
    console.log("\nBuscant idioma per el id")
    const idIdioma = req.params.idIdioma;

    const query = "SELECT * from idioma WHERE id_idioma = ?";

    connection.query(query,idIdioma,(error,result) =>
    {
        if(error) 
        {
            console.error("Erorr buscant el idioma: " ,error.message);
        }
        else
        {
            res.json(result[0]);
        }
    })

}

module.exports={
    getIdiomaById
}