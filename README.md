# THE MOVIE DATABASE Challenge

Este repositorio contiene el challenge técnico de MercadoLibre, creado por Gonzalo Guzmán.
Contacto https://www.linkedin.com/in/gonzalo-guzman/

# Primeros pasos

**IMPORTANTE!:** El API Key de TMDB no se incluyó en el repositorio, este debe cargarse dentro del
package util/Constants API_KEY = ""

Para poder correr el proyecto se debe contar con el IDE Android Studio

# Pre-requisitos

El proyecto cuenta con dependencias, las cuales se encuentran en el archivo build.gradle, la
instalación de estas dependencias se realiza automaticamente por el IDE.

# Instalación

Pasos para la ejecución del proyecto.

1. Clonar el repositorio

2. git clone https://github.com/GonzaGuzman/movieApp.git

3. Abrir con el IDE Android Studio

4.Buscar package y reemplazar texto por API Key
util/Constants/API_KEY = "reemplazarEstoPorAPI_KEY"

# Caracteristicas

- home: Clases para crear el home de la app el cual está compuesto de un activity con recyclerView y
  un toolbar de busqueda.
- detail: Aquí tenemos las clase para el detalle de las peliculas.
- search: Este Activity se encarga de presentar las búsquedas, ya sea desde home, o cualquier intent
  de búsqueda que reciba la aplicación. y presenta la lista de resultados en un recyclerView
- splash: Esta clase se encarga de mostrar el logo al iniciar la aplicación.
- La app tambien cuenta con una database limitada a 100 items en la que se guardan las peliculas vistas y al superar el limite comienza a reemplazar desde el comiezo
- para asi tener siempre las ultimas peliculas vistas y ademas no sobrecargar la memoria local con demaciadas peliculas.


# Tecnologias

- kotlin
- MVP pattern
- RxJava
- Retrofit2
- Room Database
- sharedPreference
- Mockito 
- -Glide

# Librerías utilizadas

* Las librerías estándar (Kotlin, AppCompat y Material)
* Libreria de prueba Mockito, utilizada para realizar unit test generando creando objetos simulados
* La librería RxJava, utilizada para manejar las tareas asíncronas y concurrentes
* La librería Retrofit, utilizada para realizar solicitudes REST a los endpoints de la API de
  MercadoLibre
* La librería Glide, utilizada para manejar la carga de imágenes en las views
* La libreria Roon, utilizada para crear y manipular base de datos local
