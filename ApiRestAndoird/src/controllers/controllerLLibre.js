const connection = require("../database/conexio");

function getAllLlibres(req, res) {


    console.log("\nGetting all the books in the database");

  //consulta inner join amb tota la informacio del llibre i la biblioteca
  /*const sql =
    "SELECT bl.id, bl.id_biblioteca, b.nom_biblio, l.ISBN, l.titol, l.id_autor, l.id_editor, l.id_idioma, l.id_genere, l.versio, l.data_publi,bl.stock FROM biblio_llibre bl INNER JOIN llibre l ON bl.ISBN = l.ISBN INNER JOIN biblioteca b ON b.id_biblioteca = bl.id_biblioteca";*/
    const sql = "SELECT * from llibre where stock > 0";

  connection.query(sql, function (error, result) {
    if (error) {
        console.error("Error al obtener toda la inforamcion de la llibre: ", error);
        res.status(500).send("Error interno del servidor");
    }
    else
    {
        res.json(result);
    }
  });
}


//funcio que et retorna nomes el top 10 de llibers en fromat json 
function getTop10Llibres(req,res)
{
  console.log("\nRetornat el llistat dels 10 primers llibres")
  const sql = "select * from llibre limit 10"
  connection.query(sql, function(error ,result) {
    if(error)
    { 
      console.error("Error al obtener toda la información dels top 10 llibres: ", error.message);
    }
    else{
      res.json(result);
    }
  })
}


function getLlibreByIsbn(req,res)
{
  console.log("\nBuscant el llibre per ISBN")
  const isbn = req.params.isbn;
  //select l.ISBN,a.id_autor,a.nom_autor,a.data_naix,e.id_editorial,e.nom_editorial,g.id_genere,g.descrip, i.id_idioma,i.nom_idioma,l.titol,l.versio,l.data_publi,l.stock FROM llibre l INNER JOIN autor a on a.id_autor= l.id_autor INNER JOIN editorial e on e.id_editorial = l.id_editor  INNER JOIN idioma i on i.id_idioma = l.id_idioma INNER JOIN genere g on g.id_genere =l.id_genere where ISBN =1;
  const query3 = `SELECT l.ISBN, a.id_autor, a.nom_autor, a.data_naix, e.id_editorial, e.nom_editorial, g.id_genere, g.descrip, i.id_idioma, i.nom_idioma, l.titol, l.versio, l.data_publi, l.stock FROM llibre l INNER JOIN autor a ON a.id_autor = l.id_autor INNER JOIN editorial e ON e.id_editorial = l.id_editor INNER JOIN idioma i ON i.id_idioma = l.id_idioma INNER JOIN genere g ON g.id_genere = l.id_genere WHERE ISBN = ${isbn}`;

 // const query2 = `select l.ISBN,a.id_autor,a.nom_autor,a.data_naix,e.id_editorial,e.nom_editorial,l.titol,l.versio,l.data_publi,l.stock FROM llibre l INNER JOIN autor a on a.id_autor= l.id_autor  INNER JOIN editorial e  on e.id_editorial = l.id_editor where ISBN = ${isbn}`;
  const query = `SELECT * FROM llibre WHERE ISBN = ${isbn}`;
  connection.query(query3, (err,results) => {
    if(err) {
      console.error('Error al realizar la consulta: ', err);
      res.status(500).json({ error: 'Error en el servidor' });
    }
    else{
      if(results.length >0)
      {
        const book = results[0];
        res.json(book);
      }
      else{
        //not foun de book
        console.error("NO se encontro el libro");
        res.end()
      }
    }
  });


  
}

module.exports = {
  getAllLlibres,
  getTop10Llibres,
  getLlibreByIsbn
}
