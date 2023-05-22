const connection = require("../database/conexio");

function getAllUsers(req, res) {
  console.log("\nObtenint tots els usuaris");
  const sql = "SELECT * FROM usuari";

  connection.query(sql, (error, result) => {
    if (error) {
      console.error("Error al obtener la lista de usuarios: ", error);
      res.status(500).send("Error interno del servidor");
    } else {
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


function getUserByEmail(req, res) {
  const email = req.params.email;



  console.log("\nObtenint usuari pel correu electrònic: ", email);

  const sql = "SELECT * FROM usuari WHERE email = ?";

  connection.query(sql, [email], (error, result) => {
    if (error) {
      console.error("Error al obtener el usuario por correo electrónico: ", error);
      return callback(error, null);   //retornar un callback d'error
    } else {
      if (result.length > 0) {
        const usuario = result[0];
        res.json(usuario); // Devuelve el usuario como respuesta return res.json(result);
      } else {
        //return callback(null,null)
        res.status(404).send("Usuario no encontrado");
      }
    }
  });
}


module.exports = {
  getAllUsers,
  getUserByEmail
};

