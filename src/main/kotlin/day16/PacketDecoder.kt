package day16

fun String.toBinaryString(): String {
    val conversionMap = mapOf(
        '0' to "0000",
        '1' to "0001",
        '2' to "0010",
        '3' to "0011",
        '4' to "0100",
        '5' to "0101",
        '6' to "0110",
        '7' to "0111",
        '8' to "1000",
        '9' to "1001",
        'A' to "1010",
        'B' to "1011",
        'C' to "1100",
        'D' to "1101",
        'E' to "1110",
        'F' to "1111"
    )

    val stringBuilder = StringBuilder()

    forEach { stringBuilder.append(conversionMap[it]) }

    return stringBuilder.toString()
}

sealed class Packet(
    open val version: Int,
    open val size: Int
) {
    abstract fun toVersionSum(): Int
    abstract fun evaluate(): Long
}

data class LiteralPacket(override val version: Int, override val size: Int, val value: Long): Packet(version, size) {
    override fun toVersionSum(): Int {
        return version
    }
    override fun evaluate(): Long {
        return value
    }
}
data class OperatorPacket(
    override val version: Int, override val size: Int, val type: Int, val body: List<Packet>
) : Packet(version, size) {
    override fun toVersionSum(): Int {
        return version + body.sumOf { it.toVersionSum() }
    }

    override fun evaluate(): Long {
        return when (type) {
            0 -> body.sumOf { it.evaluate() }
            1 -> body.fold(1L) { product, item -> product * item.evaluate() }
            2 -> body.minOf { it.evaluate() }
            3 -> body.maxOf { it.evaluate() }
            5 -> if (body[0].evaluate() > body[1].evaluate()) 1 else 0
            6 -> if (body[0].evaluate() < body[1].evaluate()) 1 else 0
            7 -> if (body[0].evaluate() == body[1].evaluate()) 1 else 0
            else -> throw Exception("Unknown Operator Type")
        }
    }
}

fun String.toPacket(): Packet {
    return when (substring(3..5).toInt(2)) {
        4 -> extractLiteralPacket()
        else -> parseOperatorPacket()
    }
}

private fun String.toPackets(lengthType: Int, size: Int): List<Packet> {
    var parsedLength = 0
    var packetParsed = 0

    val packetList = mutableListOf<Packet>()

    while (!isEndOfParsing(lengthType, size, parsedLength, packetParsed)) {
        val packet = when (substring(parsedLength + 3, parsedLength + 6).toInt(2)) {
            4 -> substring(parsedLength).extractLiteralPacket()
            else -> substring(parsedLength).parseOperatorPacket()
        }

        packetList.add(packet)
        parsedLength += packet.size
        packetParsed ++
    }

    return packetList.toList()
}

fun isEndOfParsing(lengthType: Int, size: Int, lengthParsed: Int, packetParsed: Int): Boolean {
    return when {
        0 == lengthType && lengthParsed < size -> false
        1 == lengthType && packetParsed < size -> false
        else -> true
    }
}

fun String.parseOperatorPacket(): OperatorPacket {
    val version = substring(0..2).toInt(2)
    val type = substring(3..5).toInt(2)
    val lengthType = substring(6..6).toInt(2)

    val valueBlocStart = when (lengthType) {
        0 -> 21
        else -> 17
    }
    val bodySize = substring(7..valueBlocStart).toInt(2)
    val packets = substring(valueBlocStart + 1).toPackets(lengthType, bodySize)

    val childrenSize = packets.sumOf { it.size }

    return OperatorPacket(version, valueBlocStart + childrenSize + 1, type, packets)
}

fun String.extractLiteralPacket(): LiteralPacket {
    val version = substring(0..2).toInt(2)

    val valueString = StringBuilder()
    var currentParsedLength = 6
    var nextBlockIndicator = "1"

    while ("1" == nextBlockIndicator) {
        nextBlockIndicator = substring(currentParsedLength, currentParsedLength + 1)
        currentParsedLength += 1

        valueString.append(substring(currentParsedLength, currentParsedLength + 4))
        currentParsedLength += 4
    }

    return LiteralPacket(version, currentParsedLength, valueString.toString().toLong(2))
}
