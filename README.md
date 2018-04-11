# Trabajo Práctico Card's Game

## Breve Resumen del Juego

En este proyecto el juego se desarrolla de la siguiente manera:
* **Antes de empezar:** Se abre una mesa. El Main Player carga y mezcla el mazo para una mesa especifica. Los jugadores se suman a la mesa.
* **Durante el juego:** El Main Player comienza a tirar cartas en la mesa. Los jugadores pelean entre ellos por agarrar la Carta. Un Relator mira lo que hace cada jugador y va relatando lo que sucede en el juego, es decir, que cada jugador agarra cada carta que el Main Player tira y quien va ganando. El juego se termina cuando el mazo del Main Player se termina.
* **Al finalizar el Juego:** Se muestra quien fue el ganador. Gana el jugador que obtiene la mayor puntuación al sumar los números de las cartas que obtuvo durante el juego.

## Puntos teóricos a desarrollar.
### Persistencia utilizada y porque eligio dicho lenguaje (lenguaje, Framework, etc).

Lenguaje de Base de Datos:
```
MySql
```
Tipo de conexión:
```
JDBC
```
* [MySql](https://es.wikipedia.org/wiki/MySQL) - . Como MySql es una base de datos relacional, es lo más optimo para mi proyecto ya que para poder persistir la informacion del ganador, sus cartas y los resultados de cada partido voy a necesitar relacionar las diferentes tablas. Ademas es escalable para mi proyecto ya que ante el aumento de requerimientos en el mismo, la base de datos seguira siendo compatible.

* [JDBC](https://es.wikipedia.org/wiki/Java_Database_Connectivity)-Decidí utilizar este método ya que es nativo de Java, es flexible, su curva de aprendizaje es rápida y es escalable para mi proyecto 

