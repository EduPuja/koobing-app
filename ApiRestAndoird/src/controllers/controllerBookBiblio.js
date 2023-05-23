const connection = require("../database/conexio");

function getAllBooksWithBiblioteca(req,res)
{
    console.log("\nGetting all libraries with books")

    /**
     * 
     */

    const query = "SELECT biblio_llibre.id ,llibre.ISBN ,llibre.titol,biblioteca.id_biblioteca ,biblioteca.nom_biblio ,biblio_llibre.stock FROM llibre INNER JOIN biblio_llibre ON llibre.ISBN = biblio_llibre.ISBN INNER JOIN biblioteca ON biblioteca.id_biblioteca = biblio_llibre.id_biblioteca";

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
