package day16

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PacketDecoderTest {
    @Test
    internal fun `test convert to binary`() {
        val input = "D2FE28"

        assertEquals("110100101111111000101000", input.toBinaryString())
    }

    @Test
    internal fun `test parse literal packet`() {
        val packet = "110100101111111000101000"
        val expectedPacket = LiteralPacket(6, 21, 2021)

        assertEquals(expectedPacket, packet.toPacket())
    }

    @Test
    internal fun `test parse operator packet`() {
        val packet = "38006F45291200".toBinaryString().toPacket()

        assert(packet is OperatorPacket)
        assertEquals(LiteralPacket(2, 16, 20), (packet as OperatorPacket).body[1])
    }

    @Test
    internal fun `test version sum`() {
        val versionSum = "8A004A801A8002F478".toBinaryString().toPacket().toVersionSum()
        assertEquals(16, versionSum)

        val versionSum2 = "620080001611562C8802118E34".toBinaryString().toPacket().toVersionSum()
        assertEquals(12, versionSum2)

        val versionSum3 = "C0015000016115A2E0802F182340".toBinaryString().toPacket().toVersionSum()
        assertEquals(23, versionSum3)

        val versionSum4 = "A0016C880162017C3686B18A3D4780".toBinaryString().toPacket().toVersionSum()
        assertEquals(31, versionSum4)
    }

    @Test
    internal fun `test sum evaluation`() {
        val sum = "C200B40A82".toBinaryString().toPacket().evaluate()
        assertEquals(3L, sum)
    }

    @Test
    internal fun `test product evaluation`() {
        val sum = "04005AC33890".toBinaryString().toPacket().evaluate()
        assertEquals(54L, sum)
    }

    @Test
    internal fun `test min evaluation`() {
        val sum = "880086C3E88112".toBinaryString().toPacket().evaluate()
        assertEquals(7L, sum)
    }
}
