fun main() {

    println("Digite uma expressão matemática:")

    val exp = readln()

    if (exp.length < 9) {
        println("Mínimo de 9 caracteres!")
        return
    }

    var conta = exp.replace(" ", "")


    while (conta.contains("(")) {
        val dentro = conta.substringAfter("(").substringBefore(")")
        val resultado = calcular(dentro)
        conta = conta.replace("($dentro)", resultado.toString())
    }


    val resultadoFinal = calcular(conta)

    println("Resultado: $resultadoFinal")
}



fun calcular(exp: String): Int {

    var conta = exp


    while (conta.contains("*") || conta.contains("/")) {
        conta = resolver(conta, "(\\d+)([*/])(\\d+)")
    }


    while (conta.contains("+") || conta.contains("-")) {
        conta = resolver(conta, "(\\d+)([+-])(\\d+)")
    }

    return conta.toInt()
}


fun resolver(exp: String, regexStr: String): String {

    val regex = Regex(regexStr)
    val match = regex.find(exp) ?: return exp

    val (a, op, b) = match.destructured

    val resultado = when (op) {
        "+" -> a.toInt() + b.toInt()
        "-" -> a.toInt() - b.toInt()
        "*" -> a.toInt() * b.toInt()
        "/" -> a.toInt() / b.toInt()
        else -> 0
    }

    return exp.replaceFirst(match.value, resultado.toString())
}