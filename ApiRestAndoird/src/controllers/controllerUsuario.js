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


function registerUser(req,res)
{
  console.log("\nRegister user")
  const user = req.body;

  const sql = "INSERT INTO usuari (dni, avatar, nom, cognom, data_naix, email, password) VALUES (?, NULL, ?, ?, ?, ?, ?)";

  connection.query(sql, [user.dni, user.nom, user.cognom, user.data_naix, user.email, user.password],(error,result) =>{
    if(error)
    {
      console.error("Error al insertar el usuari :",error.message);
      res.status(500).json({ message: 'Error al insertar el usuario' });
    }
    else {
      console.log('Usuari insertat correctament');
      res.status(200).json({ message: 'Usuari insertat correctament' });
    }
  })
  //console.log("Usuari ? :" ,user)
}


module.exports = {
  getAllUsers,
  getUserByEmail
  ,registerUser
};

