package kotlinx.io.core

actual interface Output : Appendable {
    actual var byteOrder: ByteOrder

    actual fun writeByte(v: Byte)
    actual fun writeShort(v: Short)
    actual fun writeInt(v: Int)
    actual fun writeLong(v: Long)
    actual fun writeFloat(v: Float)
    actual fun writeDouble(v: Double)

    actual fun writeFully(src: ByteArray, offset: Int, length: Int)
    actual fun writeFully(src: ShortArray, offset: Int, length: Int)
    actual fun writeFully(src: IntArray, offset: Int, length: Int)
    actual fun writeFully(src: LongArray, offset: Int, length: Int)
    actual fun writeFully(src: FloatArray, offset: Int, length: Int)
    actual fun writeFully(src: DoubleArray, offset: Int, length: Int)
    actual fun writeFully(src: BufferView, length: Int)

    actual fun append(csq: CharArray, start: Int, end: Int): Appendable

    actual fun fill(n: Long, v: Byte)
    actual fun flush()
    actual fun close()

    @Deprecated("Non-public API. Use writeWhile instead", level = DeprecationLevel.ERROR)
    actual fun `$prepareWrite$`(n: Int): BufferView

    @Deprecated("Non-public API. Use writeWhile instead", level = DeprecationLevel.ERROR)
    actual fun `$afterWrite$`()
}
