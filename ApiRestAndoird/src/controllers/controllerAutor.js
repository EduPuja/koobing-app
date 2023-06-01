const connection = require("../database/conexio");

function getAutorByID(req,res)
{
    const idAutor = req.params.idAutor;
    console.log("\nBuscant el autor per el id");

    const query = "select * from autor where id = ?";
    connection.query(query,idAutor,(error,result) => {
        if(error) {
            console.error("Not found the autor: " + error.message);
        }
        else{
            res.json(result[0]);
        }
    })
}