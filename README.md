# ecommerce.backend
Ecommerce Backend hecho en Java con SpringBoot

# Instalacion
## Requisitos
- IntelliJ Idea CE
- MysqlServer

## Pasos a seguir:

- Instalar IntelliJ Idea CE y abrir la carpeta del proyecto
- Instalar MysqlServer
- Dentro del proyecto hay un archivo llamado application.properties, el cual esta en esta ruta:
src/main/resources/application.properties
Dentro de este archivo tienen que cambiar las credenciales por las que ustedes modificaron al instalar MysqlServer
spring.datasource.username={TU USUARIO}
spring.datasource.password={TU CONTRASENA}

MysqlServer viene por defecto con el usuario root, el cual puedes cambiar su contrasenia en la instalacion
Se tiene que crear una DB con nombre ecommerce.

CREATE DATABASE ecommerce;

Luego de esto en IntelliJ Idea CE, en Project Structure se tiene que elegir el SDK de Java a usar, elegir el 17

Finalmente van a este archivo src/main/java/com.pascal.ecommerce.backend/Application y corren la aplicacion

# LLamadas al front

Para que se puedan hacer llamadas al front, dentro de este archivo src/main/java/com.pascal.ecommerce.backend/controller/Product Controller

Linea 15 : @CrossOrigin(origins = "http://localhost:3000")

Se tiene que poner la ruta donde se esta ejecutando el Front
