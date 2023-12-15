package org.example
import java.util.*


data class pos (
    var x:Int,
    var y:Int
)


fun main() {
    val scan = Scanner(System.`in`)
    val tablero = crearTablero(scan)
    val pieza = crearPieza(tablero)
    mourePieza(pieza,tablero,scan)
}

fun colision(pieza: Array<pos>, tablero: Array<Array<Char>>,input:Char):Boolean {
    if (input == 'a' || input == 'A' || input == 'd' || input == 'D') {
        var respuesta:Boolean
        val bordes_y = pos(0,0)
        for (i in pieza) {
            if (i.y > bordes_y.y ) bordes_y.y = i.y
            if (i.y < bordes_y.y) bordes_y.x = i.y
        }
    }
    return true
}

fun mostrarTablero (tablero:Array<Array<Char>>) {

    println("Controles: D = derecha, A = izquierda, espcio = bajar (Solo cojera el primer caracter)")
    for (x in 0 until tablero.size) {
        for (y in 0 until tablero[x].size) {
            print("${tablero[x][y]} ")
        }
        println("")
    }
}


fun mourePieza(pieza: Array<pos>, tablero: Array<Array<Char>>,scan:Scanner) {
    do {
        for (i in 0..3) {
            for (e in 0 until tablero[i].size) tablero[i][e] = '-'
        }
        for (i in pieza) {
            tablero[i.x][i.y] = '1'
        }
        mostrarTablero(tablero)
        val input = scan.next()[0]
        if (colision(pieza,tablero,input)) {
            when(input) {
                'a','A' -> {for (i in pieza) i.y -= 1}
                'd','D' -> {for (i in pieza) i.y += 1}
            }
        }

    } while(input != ' ')
}

fun crearPieza(tablero:Array<Array<Char>>):Array<pos> {
    val pieza = Array(4) { pos(0,0) }
    val piezas = arrayOf("quadrado","linea","lInversa","lAcostada")
    when (piezas[Math.floor(Math.random()*piezas.size).toInt()]) {
        "quadrado" -> {
            pieza[0] = pos(0,0)
            pieza[1] = pos(0,1)
            pieza[2] = pos(1,0)
            pieza[3] = pos(1,1)
        }
        "linea" -> {
            pieza[0] = pos(0,0)
            pieza[1] = pos(1,0)
            pieza[2] = pos(2,0)
            pieza[3] = pos(3,0)
        }
        "lInversa" -> {
            pieza[0] = pos(0,1)
            pieza[1] = pos(1,1)
            pieza[2] = pos(2,1)
            pieza[3] = pos(2,0)
        }
        "lAcostada" -> {
            pieza[0] = pos(0,0)
            pieza[1] = pos(1,0)
            pieza[2] = pos(1,1)
            pieza[3] = pos(1,2)
        }
    }
    return pieza
}



fun crearTablero(scan:Scanner): Array<Array<Char>> {
    println("Di las medidas del tabler (minimo ha de ser de 4x4): ")
    var x:Int
    var y:Int
    do {
        println("Di el tamaño horizontal: ")
        x = comprobarInt("El valor introducido no es correcto",scan)
        println("Di el tamaño vertical: ")
        y = comprobarInt("El valor introducido no es correcto",scan)
        if (x < 4 || y < 4) println("Uno de los dos valores introducidos no cumple con las medidas minimas")
    }while (x < 4 || y < 4)

    return Array(x+4) { Array(y){ '0' } }
}



/**
 * Esta funcion comprueba si un input es un Int y no para de preguntar hasta que se introduzca un Int
 *
 * @param mensajeError es el mensaje que damos al usuario cunado da un valor que no cumple lo indicado
 *  * @param scan es el scaner que usamos en el programa
 *
 * @return retorna un numero Int
 */

fun comprobarInt(mensajeError: String,scan:Scanner):Int {
    while (!scan.hasNextInt()) {
        scan.nextLine()
        println(mensajeError)
    }
    return scan.nextInt()
}

