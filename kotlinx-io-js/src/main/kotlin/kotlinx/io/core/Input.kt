package kotlinx.io.core

import org.khronos.webgl.*

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

    fun readFully(dst: Int8Array, offset: Int, length: Int)
    fun readFully(dst: ArrayBuffer, offset: Int, length: Int)
    fun readFully(dst: ArrayBufferView, offset: Int, length: Int)

    actual fun readAvailable(dst: ByteArray, offset: Int, length: Int): Int
    actual fun readAvailable(dst: ShortArray, offset: Int, length: Int): Int
    actual fun readAvailable(dst: IntArray, offset: Int, length: Int): Int
    actual fun readAvailable(dst: LongArray, offset: Int, length: Int): Int
    actual fun readAvailable(dst: FloatArray, offset: Int, length: Int): Int
    actual fun readAvailable(dst: DoubleArray, offset: Int, length: Int): Int
    actual fun readAvailable(dst: BufferView, length: Int): Int

    fun readAvailable(dst: Int8Array, offset: Int, length: Int): Int
    fun readAvailable(dst: ArrayBuffer, offset: Int, length: Int): Int
    fun readAvailable(dst: ArrayBufferView, offset: Int, length: Int): Int

    actual fun discard(n: Long): Long
    actual fun close()

    @Deprecated("Non-public API. Use takeWhile or takeWhileSize instead", level = DeprecationLevel.ERROR)
    actual fun `$updateRemaining$`(remaining: Int)

    @Deprecated("Non-public API. Use takeWhile or takeWhileSize instead", level = DeprecationLevel.ERROR)
    actual fun `$ensureNext$`(current: BufferView): BufferView?

    @Deprecated("Non-public API. Use takeWhile or takeWhileSize instead", level = DeprecationLevel.ERROR)
    actual fun `$prepareRead$`(minSize: Int): BufferView?
}
