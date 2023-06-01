const connection = require("../database/conexio");

function getGenereById(req,res)
{
    const idGenere = req.params.idGenere;  
    const query = "SELECT * from genre where id_genere = ?";

    connection.query(query, idGenere , (error, result) => {
        if(error) 
        {
            console.error("Error buscant genere", error.message);
        }
        else{
            res.json(result[0]);
        }
    });
}

module.exports ={getGenereById} ; 