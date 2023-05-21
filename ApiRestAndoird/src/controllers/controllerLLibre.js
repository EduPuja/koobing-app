const connection = require("../database/conexio");

function getAllLlibres(req, res) {


    console.log("\nGetting all the books in the database");

  //consulta inner join amb tota la informacio del llibre i la biblioteca
  const sql =
    "SELECT bl.id, bl.id_biblioteca, b.nom_biblio, l.ISBN, l.titol, l.id_autor, l.id_editor, l.id_idioma, l.id_genere, l.versio, l.data_publi,bl.stock FROM biblio_llibre bl INNER JOIN llibre l ON bl.ISBN = l.ISBN INNER JOIN biblioteca b ON b.id_biblioteca = bl.id_biblioteca";

  connection.query(sql, function (error, result) {
    if (error) {
        console.error("Error al obtener toda la inforamcion de la biblioteca: ", error);
        res.status(500).send("Error interno del servidor");
    }
    else
    {
        res.json(result);
    }
  });
}

module.exports = {
  getAllLlibres,
};
