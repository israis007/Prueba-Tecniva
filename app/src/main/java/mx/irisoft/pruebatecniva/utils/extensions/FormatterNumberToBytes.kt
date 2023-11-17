package mx.irisoft.pruebatecniva.utils.extensions

import java.text.DecimalFormat

private val simpleDecimalFormat = DecimalFormat("###,###,###,###.00")

fun Long.formatKyloBytes(): String =
    simpleDecimalFormat.format(this / 1000)

fun Long.formatMegaBytes(): String =
    simpleDecimalFormat.format(this / 1000000)

fun Long.formatGigaBytes(): String =
    simpleDecimalFormat.format(this / 1000000000)

fun Long.formatNumber(): String =
    simpleDecimalFormat.format(this)

fun Int.formatNumber(): String =
    simpleDecimalFormat.format(this.toLong())

fun Float.formatNumber(): String =
    simpleDecimalFormat.format(this.toDouble())

fun Double.formatNumber(): String =
    simpleDecimalFormat.format(this)

fun Long.formatKyloBytesInt(): Int =
    (this / 1000).toInt()

fun Long.formatMegaBytesInt(): Int =
    (this / 1000000).toInt()