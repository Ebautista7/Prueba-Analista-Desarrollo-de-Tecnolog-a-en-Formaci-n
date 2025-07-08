# Proyecto de Gestión de Gastos

Este proyecto permite analizar los gastos de empleados.

## Tecnologia
    - Java 17
    - SpingBoot 3.5.3 con Maven
### Dependencias
    - Lombok
    - Spring Web
    - SprinBoot Dev Tools

    - Se uso la libreria de JUnit para las pruebas unitarias


## Cómo ejecutar
### Requisitos
    - Java 17 o superior
    - Conexion a internet para descargar las dependencias por primera vez
    - Git
### Pasos
- Si deseas hacerlo por consola debes seguir estos paso:

Clonar el repositorio con: 
- git clone https://github.com/Ebautista7/Prueba-Analista-Desarrollo-de-Tecnolog-a-en-Formaci-n.git

Ingresar al repo
- cd Prueba-Analista-Desarrollo-de-Tecnolog-a-en-Formaci-n

Ejecutar el proyecto
- .\mvnw spring-boot:run

puedes probar desde el navegador o un cliente que permita lanzar las peticiones los endpoints disponibles son:
localhost:8080/api/expenses/reportExpenses
localhost:8080/api/expenses/totalForEmployee
localhost:8080/api/expenses/totalExpenses
localhost:8080/api/expenses/getEmployes

- Si deseas clonar el repo y abrirlo desde un IDE debes buscar en la ruta src/main el archivo TecnicaApplication.java y ejecutarlo

## Observaciones
En la raiz hay un archivo llamado EndPoints de Prueba Tecnica.postman_collection.json que es una coleccion de postman la cual luego de ejecutado tambien puedes usar para probar
