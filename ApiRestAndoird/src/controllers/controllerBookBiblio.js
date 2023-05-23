const connection = require("../database/conexio");

function getAllBooksWithBiblioteca(req,res)
{
    console.log("\nGetting all libraries with books")

    const query = "select  * from biblio_llibre";

    connection.query(query ,function(error,result){

        if(error)
        {
            console.error("Error al obtener la lista de de biblio_llibre: ", error.message);
            res.status(500).send("Error interno del servidor");
        }
        else{
            res.json(result);
        }

    });
}

module.exports ={
    getAllBooksWithBiblioteca
}
