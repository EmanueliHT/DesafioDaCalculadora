fun main() {
    println("Digite uma expressão matemática: ")
    val entrada = readln()
    var conta = entrada.replace(" ", "")


    val regexParenteses = Regex("\\(([^()]+)\\)")
    while (conta.contains("(")) {
        val match = regexParenteses.find(conta)
        if (match != null) {
            val dentro = match.groupValues[1]
            val resultado = calcular(dentro)
            conta = conta.replace("(${dentro})", resultado.toString())
        }
    }

    val resultadoFinal = calcular(conta)
    println("Resultado: $resultadoFinal")
}

fun calcular(exp: String): Int {
    var conta = exp
    val regexMultDiv = "(-?\\d+)([*/])(-?\\d+)"
    val regexSomaSub = "(-?\\d+)([+-])(-?\\d+)"

    while (conta.contains("*") || (conta.contains("/") && !conta.startsWith("-/"))) {
        conta = resolver(conta, regexMultDiv)
    }

    while (conta.contains("+") || (conta.contains("-") && conta.lastIndexOf("-") > 0)) {
        conta = resolver(conta, regexSomaSub)
    }

    return conta.toInt()
}

fun resolver(exp: String, regexStr: String): String {
    val regex = Regex(regexStr)
    val match = regex.find(exp) ?: return exp

    val (a, op, b) = match.destructured
    val numA = a.toInt()
    val numB = b.toInt()

    val resultado = when (op) {
        "*" -> numA * numB
        "/" -> if (numB != 0) numA / numB else 0
        "+" -> numA + numB
        "-" -> numA - numB
        else -> 0
    }

    return exp.replaceFirst(match.value, resultado.toString())
}
