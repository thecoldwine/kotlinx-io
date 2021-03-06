package kotlinx.coroutines.experimental.io

import kotlinx.io.core.*
import org.khronos.webgl.*

/**
 * Creates buffered channel for asynchronous reading and writing of sequences of bytes.
 */
actual fun ByteChannel(autoFlush: Boolean): ByteChannel {
    return ByteChannelJS(BufferView.Empty, autoFlush)
}

/**
 * Creates channel for reading from the specified byte array.
 */
actual fun ByteReadChannel(content: ByteArray): ByteReadChannel {
    if (content.isEmpty()) return ByteReadChannel.Empty
    val head = BufferView.Pool.borrow()
    var tail = head

    var start = 0
    while (true) {
        tail.reserveEndGap(8)
        val size = minOf(content.size - start, tail.writeRemaining)
        tail.writeFully(content, start, size)
        start += size

        if (start == content.size) break
        tail = BufferView.Pool.borrow()
    }

    return ByteChannelJS(head, false)
}

/**
 * Creates channel for reading from the specified [ArrayBufferView]
 */
fun ByteReadChannel(content: ArrayBufferView): ByteReadChannel {
    if (content.byteLength == 0) return ByteReadChannel.Empty
    val head = BufferView.Pool.borrow()
    var tail = head

    var start = 0
    var remaining = content.byteLength - content.byteOffset
    while (true) {
        tail.reserveEndGap(8)
        val size = minOf(remaining, tail.writeRemaining)
        tail.writeFully(content, start, size)
        start += size
        remaining -= size

        if (remaining == 0) break
        tail = BufferView.Pool.borrow()
    }

    return ByteChannelJS(head, false)
}

actual suspend fun ByteReadChannel.joinTo(dst: ByteWriteChannel, closeOnEnd: Boolean) {
    (this as ByteChannelSequentialBase).joinTo((dst as ByteChannelSequentialBase), closeOnEnd)
}

/**
 * Reads up to [limit] bytes from receiver channel and writes them to [dst] channel.
 * Closes [dst] channel if fails to read or write with cause exception.
 * @return a number of copied bytes
 */
actual suspend fun ByteReadChannel.copyTo(dst: ByteWriteChannel, limit: Long): Long {
    return (this as ByteChannelSequentialBase).copyTo((dst as ByteChannelSequentialBase))
}

internal class ByteChannelJS(initial: BufferView, autoFlush: Boolean) : ByteChannelSequentialBase(initial, autoFlush) {
    override suspend fun readAvailable(dst: ArrayBuffer, offset: Int, length: Int): Int {
        return if (readable.isEmpty) {
            readAvailableSuspend(dst, offset, length)
        } else {
            closedCause?.let { throw it }
            readable.readAvailable(dst, offset, length)
        }
    }

    private suspend fun readAvailableSuspend(dst: ArrayBuffer, offset: Int, length: Int): Int {
        if (!await(1)) return -1
        return readAvailable(dst, offset, length)
    }

    override suspend fun readFully(dst: ArrayBuffer, offset: Int, length: Int) {
        if (availableForRead >= length) {
            closedCause?.let { throw it }
            readable.readFully(dst, offset, length)
            return
        }

        return readFullySuspend(dst, offset, length)
    }

    private suspend fun readFullySuspend(dst: ArrayBuffer, offset: Int, length: Int) {
        var start = offset
        val end = offset + length
        var remaining = length

        while (start < end) {
            val rc = readAvailable(dst, start, remaining)
            if (rc == -1) throw EOFException("Premature end of stream: required $remaining more bytes")
            start += rc
            remaining -= rc
        }
    }
}
