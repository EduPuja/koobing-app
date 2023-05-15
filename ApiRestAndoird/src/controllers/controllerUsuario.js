const conection = require('../database/conexio');


function getAllUsers(req, res) {
    const sql = "SELECT * FROM usuaris";
  
    db.query(sql, (err, result) => {
      if (err) {
        console.error("Error al obtener la lista de usuarios: ", error);
        res.status(500).send("Error interno del servidor");
      } 
      else {
        const users = result.map((user) => {
          return {
            ...user,
            avatar: user.avatar.toString("base64"),
          };
        });
  
        res.json(users);
      }
    });
  }
module.exports = {
    getAllUsers
}