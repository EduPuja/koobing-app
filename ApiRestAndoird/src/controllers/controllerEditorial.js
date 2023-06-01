const connection = require("../database/conexio");

function getEditroialById(req,res)
{
    console.log("\n Getting editroial by id");
    const idEditorial = req.params.idEditorial;

    const query = "SELECT * from editorial where id_editorial =?";

    connection.query(query,idEditorial,(error,result) =>{
        if(error) {
            console.error("Error buscant la editroial: "+error.message);
        }
        else{
            res.json(result[0])
        }
    })


}

module.exports ={
    getEditroialById
}