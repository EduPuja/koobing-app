const connection = require("../database/conexio");


function getIdiomaById(req,res)
{
    const idIdioma = req.params.idIdioma;

    const query = "SELECT * from idioma WHERE idIdioma= ?";

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