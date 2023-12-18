# API REST - Gestión de Biblioteca

La API REST, parte esencial del ecosistema Koobing App, proporciona servicios fundamentales para la gestión de libros, usuarios y préstamos de la biblioteca.

## Tecnologías Utilizadas
- Node.js 18.04
- ExpressJS
- MySQL 

## Funcionalidades Principales
- Endpoints para la gestión completa de libros, usuarios y préstamos.
- Servicios para operaciones CRUD (Crear, Leer, Actualizar, Eliminar) en la base de datos.
- Interfaz de comunicación entre la base de datos y las aplicaciones cliente.

## Instrucciones de Uso
1. Clonar este repositorio.
2. Instalar Node.js 18.04 o superior.
3. Instalar todas las dependecias del package.json usando el comando  `npm install -i`
4. Iniciar el servidor con el comando comando. `npm run dev ` o bien `npm run start`

## Estructura de Directorios
- `/src`: Archivos fuente de la API.
    - `/controllers`: Controladores que manejan las peticiones HTTP.
    - `/databases`: Archivos de configuración, como conexiones a la base de datos.
    - `index.js`: Archivo que levante el proyecto de Node
