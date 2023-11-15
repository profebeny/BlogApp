#BlogApp

Aplicación para Android desarrollada en Kotlin con un servicio web Firebase que
posee un conjunto reducido de características propias de un Blog de Internet.

###  APK para la instalación del aplicativo:
https://drive.google.com/file/d/13m57vVVguOdqTE104TUFpZuvPD2DN4eM/view?usp=sharing

###  Video demostrativo:
**https://www.youtube.com/watch?v=Anj7Jd7PnWE

## Prototipo Figma
Elaboración de un prototipo que permita identificar las interfaces principales y secundarias, elementos a programar y la funcionalidad de los mismos. Correcciones y simulación de como funcionará el proyecto.
Liga: https://www.figma.com/file/bgAqFS5kwBDG19qJxXyPBH/BlogApp?type=design&node-id=0%3A1&mode=design&t=xH6F5qM785XD8OjY-1


## Servidor de Base de Datos (Nube)
###  Realtime Firebase




###  Nueva Entrada con Postman
Para generar una entrada nueva y visualizar la actualización automática en la app se puede utilizar postman

**URL POST:
**https://blogapp-78cc1-default-rtdb.firebaseio.com/entradas.json

**body raw en formato json**
```json
{
    "titulo":"Entrada nueva con Postman",
    "autor":"Beny Roman",
    "contenido":"Esta es una entrada para demostrar que puede funcionar de manera offline el aplicativo y una vez que cuenta con conexión se actualizará de manera automática, cargando los datos en SQLite por medio de la librería Room ",
    "fechaHora":"2023-11-12T01:56:03.080562"
}
```
**Respuesta esperada**
```json
{
    "name": "-NjGrc-HjSFIp9DKSuPq"
}
```

###  Servicio GET
Puede acceder a los valores de realtime database por medio de este servicio que devuelve un JSON objet
https://blogapp-78cc1-default-rtdb.firebaseio.com/entradas.json


###  Funcionamiento offline / online

