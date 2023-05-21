const connection = require("../database/conexio");

function getAllUsers(req, res) {
  console.log("Obtenint tots els usuaris");
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
  const email = req.body.email; // Obtén el valor del email enviado desde Android Studio

  if(email!=undefined && email!==null)

  console.log("Obtenint usuari pel correu electrònic: ", email);

  const sql = "SELECT * FROM usuari WHERE email = ?";

  connection.query(sql, [email], (error, result) => {
    if (error) {
      console.error("Error al obtener el usuario por correo electrónico: ", error);
      res.status(500).send("Error interno del servidor");
    } else {
      if (result.length > 0) {
        const user = {
          ...result[0],
          avatar: result[0].avatar.toString("base64"),
        };
        res.json(user);
      } else {
        res.status(404).send("Usuario no encontrado");
      }
    }
  });
}


module.exports = {
  getAllUsers,
  getUserByEmail
};

