package kotlinx.io.core

import kotlinx.cinterop.*

actual interface Input {
    actual var byteOrder: ByteOrder
    actual val endOfInput: Boolean

    actual fun readByte(): Byte
    actual fun readShort(): Short
    actual fun readInt(): Int
    actual fun readLong(): Long
    actual fun readFloat(): Float
    actual fun readDouble(): Double

    actual fun readFully(dst: ByteArray, offset: Int, length: Int)
    actual fun readFully(dst: ShortArray, offset: Int, length: Int)
    actual fun readFully(dst: IntArray, offset: Int, length: Int)
    actual fun readFully(dst: LongArray, offset: Int, length: Int)
    actual fun readFully(dst: FloatArray, offset: Int, length: Int)
    actual fun readFully(dst: DoubleArray, offset: Int, length: Int)
    actual fun readFully(dst: BufferView, length: Int)

    fun readFully(dst: CPointer<ByteVar>, offset: Int, length: Int)
    fun readFully(dst: CPointer<ByteVar>, offset: Long, length: Long)

    actual fun readAvailable(dst: ByteArray, offset: Int, length: Int): Int
    actual fun readAvailable(dst: ShortArray, offset: Int, length: Int): Int
    actual fun readAvailable(dst: IntArray, offset: Int, length: Int): Int
    actual fun readAvailable(dst: LongArray, offset: Int, length: Int): Int
    actual fun readAvailable(dst: FloatArray, offset: Int, length: Int): Int
    actual fun readAvailable(dst: DoubleArray, offset: Int, length: Int): Int
    actual fun readAvailable(dst: BufferView, length: Int): Int

    fun readAvailable(dst: CPointer<ByteVar>, offset: Int, length: Int): Int
    fun readAvailable(dst: CPointer<ByteVar>, offset: Long, length: Long): Long

    actual fun discard(n: Long): Long
    actual fun close()

    @Deprecated("Non-public API. Use takeWhile or takeWhileSize instead", level = DeprecationLevel.ERROR)
    actual fun `$updateRemaining$`(remaining: Int)

    @Deprecated("Non-public API. Use takeWhile or takeWhileSize instead", level = DeprecationLevel.ERROR)
    actual fun `$ensureNext$`(current: BufferView): BufferView?

    @Deprecated("Non-public API. Use takeWhile or takeWhileSize instead", level = DeprecationLevel.ERROR)
    actual fun `$prepareRead$`(minSize: Int): BufferView?
}

fun Input.readAvailable(dst: ByteArray): Int = readAvailable(dst, 0, dst.size)
fun Input.readAvailable(dst: ShortArray): Int = readAvailable(dst, 0, dst.size)
fun Input.readAvailable(dst: IntArray): Int = readAvailable(dst, 0, dst.size)
fun Input.readAvailable(dst: LongArray): Int = readAvailable(dst, 0, dst.size)
fun Input.readAvailable(dst: FloatArray): Int = readAvailable(dst, 0, dst.size)
fun Input.readAvailable(dst: DoubleArray): Int = readAvailable(dst, 0, dst.size)

fun Input.readFully(dst: ByteArray) {
    readFully(dst, 0, dst.size)
}

fun Input.readFully(dst: ShortArray) {
    readFully(dst, 0, dst.size)
}

fun Input.readFully(dst: IntArray) {
    readFully(dst, 0,  dst.size)
}

fun Input.readFully(dst: LongArray) {
    readFully(dst, 0, dst.size)
}

fun Input.readFully(dst: FloatArray) {
    readFully(dst, 0, dst.size)
}

fun Input.readFully(dst: DoubleArray) {
    readFully(dst, 0, dst.size)
}
