package org.example
import java.util.*


data class pos (
    var x:Int,
    var y:Int
)


fun main() {
    val scan = Scanner(System.`in`)
    val tablero = crearTablero(scan)
    do {
        val pieza = crearPieza(tablero)
        mourePieza(pieza,tablero,scan)
        hacerLinea(tablero)
    } while (!gameOver(tablero,pieza))
    println("GAME OVER!!")
}


fun gameOver (tablero: Array<Array<Char>>, pieza: Array<pos>):Boolean {
    for (y in 0..3) {
        for (x in tablero[y].indices) if (tablero[y][x] == '1') return true
    }
    return false
}

fun colision(pieza: Array<pos>, tablero: Array<Array<Char>>,input:Char):Boolean {
    if (input == 'a' || input == 'A' || input == 'd' || input == 'D') {
        val bordes_y = pos(tablero[0].lastIndex,0)
        for (i in pieza) {
            if (i.x > bordes_y.y ) bordes_y.y = i.x
            if (i.x < bordes_y.x) bordes_y.x = i.x
        }
        if (bordes_y.y < (tablero[0].size - 1) && (input == 'd' || input == 'D')) return true
        if (bordes_y.x > 0 && (input == 'a' || input == 'A')) return true
    }


    else if (input == 's' || input == 'S') {
        var bordes_x = 0
        for (i in pieza) {
            if (i.y > bordes_x) bordes_x = i.y
        }
        if (bordes_x < tablero.size - 1) {
            for (i in pieza) {
                if (tablero[bordes_x + 1][i.x] == '1' && i.y == bordes_x) return false
            }
            return true
        }
    }
    return false
}

fun mostrarTablero (tablero:Array<Array<Char>>) {

    println("Controles: D = derecha, A = izquierda, S = bajar (Solo cojera el primer caracter)")
    for (y in 0 until tablero.size) {
        for (x in 0 until tablero[y].size) {
            print("${tablero[y][x]} ")
        }
        println("")
    }
}



fun mourePieza(pieza: Array<pos>, tablero: Array<Array<Char>>,scan:Scanner) {
    do {
        for (i in 0..3) {
            for (e in 0 until tablero[i].size) tablero[i][e] = '-'
        }
        for (i in pieza) tablero[i.y][i.x] = '1'
        mostrarTablero(tablero)
        val input = scan.next()[0]
        if (colision(pieza,tablero,input)) {
            when(input) {
                'a','A' -> {for (i in pieza) i.x -= 1}
                'd','D' -> {for (i in pieza) i.x += 1}
            }
        }

    } while(input != 's')
    val input = 's'
    do {
        var bajando = colision(pieza,tablero,input)
        if (bajando) {
            for (i in pieza) tablero[i.y][i.x] = '0'
            for (i in 0..3) for (e in 0 until tablero[i].size) tablero[i][e] = '-'
            for (i in pieza) i.y += 1
            for (i in pieza) tablero[i.y][i.x] = '1'
        }
    } while (bajando)

}

fun crearPieza(tablero:Array<Array<Char>>):Array<pos> {
    val pieza = Array(4) { pos(0,0) }
    val piezas = arrayOf("quadrado","linea","lInversa","lAcostada")
    when (piezas[Math.floor(Math.random()*piezas.size).toInt()]) {
        "quadrado" -> {
            pieza[0] = pos(0,0)
            pieza[1] = pos(1,0)
            pieza[2] = pos(0,1)
            pieza[3] = pos(1,1)
        }
        "linea" -> {
            pieza[0] = pos(0,0)
            pieza[1] = pos(0,1)
            pieza[2] = pos(0,2)
            pieza[3] = pos(0,3)
        }
        "lInversa" -> {
            pieza[0] = pos(1,0)
            pieza[1] = pos(1,1)
            pieza[2] = pos(1,2)
            pieza[3] = pos(0,2)
        }
        "lAcostada" -> {
            pieza[0] = pos(0,0)
            pieza[1] = pos(0,1)
            pieza[2] = pos(1,1)
            pieza[3] = pos(2,1)
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
        y = comprobarInt("El valor introducido no es correcto",scan)
        println("Di el tamaño vertical: ")
        x = comprobarInt("El valor introducido no es correcto",scan)
        if (x < 4 || y < 4) println("Uno de los dos valores introducidos no cumple con las medidas minimas")
    }while (x < 4 || y < 4)

    return Array(y+4) { Array(x){ '0' } }
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

fun hacerLinea(tablero: Array<Array<Char>>) {
    var linea: Int = 0
    var hayLinea: Boolean
    for (y in tablero.lastIndex downTo 0) {
        hayLinea = true
        for (x in tablero[y]) {
            linea = y
            if (x != '1') hayLinea = false
        }

        if (hayLinea) {
            for (i in tablero[linea].indices) tablero[linea][i] = '0'

            for (y in linea downTo  4) for (x in tablero[y].indices) {
                if (tablero[y][x] == '1') {
                    var posY = y
                    while (posY != tablero.lastIndex || tablero[posY + 1][x] == '1') {
                        tablero[posY][x] = '0'
                        tablero[posY + 1][x] = '1'
                        posY += 1
                    }
                }
            }
        }
    }
}