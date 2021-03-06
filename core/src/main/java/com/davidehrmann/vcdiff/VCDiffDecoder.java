package com.davidehrmann.vcdiff;

import com.davidehrmann.vcdiff.util.Objects;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * A simpler (non-streaming) interface to the VCDIFF decoder that can be used
 * if the entire delta file is available.
 */
public class VCDiffDecoder {
    private final VCDiffStreamingDecoder decoder;

    public VCDiffDecoder(VCDiffStreamingDecoder decoder) {
        this.decoder = Objects.requireNotNull(decoder, "decoder was null");
    }

    /**
     * @deprecated use {@link #decode(byte[], byte[], OutputStream)}
     *
     * decode the contents of encoding using the specified dictionary, writing the decoded data to target
     *
     * @param dictionary dictionary
     * @param encoding data to decode
     * @param offset offset into encoding to start decoding
     * @param len number of bytes to decode
     * @param target output writer for decoded data
     * @throws IOException if there was an exception decoding or writing to the output target
     */
    @Deprecated
    public void decode(byte[] dictionary, byte[] encoding, int offset, int len, OutputStream target) throws IOException {
        decode(ByteBuffer.wrap(dictionary), ByteBuffer.wrap(encoding, offset, len), target);
    }

    /**
     * decode the contents of encoding using the specified dictionary, writing the decoded data to target
     *
     * @param dictionary dictionary
     * @param encoding data to decode
     * @param target output writer for decoded data
     * @throws IOException if there was an exception decoding or writing to the output target
     */
    public void decode(ByteBuffer dictionary, ByteBuffer encoding, OutputStream target) throws IOException {
        decoder.startDecoding(dictionary);
        decoder.decodeChunk(encoding, target);
        decoder.finishDecoding();
    }

    /**
     * Convenience method equivalent to decode(ByteBuffer.wrap(dictionary), ByteBuffer.wrap(encoding), target)
     *
     * @param dictionary dictionary
     * @param encoding data to decode
     * @param target output writer for decoded data
     * @throws IOException if there was an exception decoding or writing to the output target
     */
    public void decode(byte[] dictionary, byte[] encoding, OutputStream target) throws IOException {
        decode(ByteBuffer.wrap(dictionary), ByteBuffer.wrap(encoding), target);
    }
}
