package com.ikasoa.core.nifty.handler.impl;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.protocol.TProtocolUtil;
import org.apache.thrift.protocol.TType;
import org.apache.thrift.transport.TTransportException;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.jboss.netty.handler.codec.frame.TooLongFrameException;

import com.ikasoa.core.nifty.TNiftyTransport;
import com.ikasoa.core.nifty.ThriftMessage;
import com.ikasoa.core.nifty.ThriftTransportType;

/**
 * ThriftFrameDecoder
 * 
 * @author <a href="mailto:larry7696@gmail.com">Larry</a>
 * @version 0.6
 */
public class ThriftFrameDecoder extends FrameDecoder {

	public static final int MESSAGE_FRAME_SIZE = 4;
	private final int maxFrameSize;
	private final TProtocolFactory inputProtocolFactory;

	public ThriftFrameDecoder(int maxFrameSize, TProtocolFactory inputProtocolFactory) {
		this.maxFrameSize = maxFrameSize;
		this.inputProtocolFactory = inputProtocolFactory;
	}

	@Override
	protected ThriftMessage decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
		if (!buffer.readable())
			return null;

		short firstByte = buffer.getUnsignedByte(0);
		if (firstByte >= 0x80) {
			ChannelBuffer messageBuffer = tryDecodeUnframedMessage(ctx, channel, buffer, inputProtocolFactory);

			if (messageBuffer == null)
				return null;

			// A non-zero MSB for the first byte of the message implies the message starts
			// with a
			// protocol id (and thus it is unframed).
			return new ThriftMessage(messageBuffer, ThriftTransportType.UNFRAMED);
		} else if (buffer.readableBytes() < MESSAGE_FRAME_SIZE)
			// Expecting a framed message, but not enough bytes available to read the frame
			// size
			return null;
		else {
			ChannelBuffer messageBuffer = tryDecodeFramedMessage(ctx, channel, buffer, true);

			if (messageBuffer == null)
				return null;

			// Messages with a zero MSB in the first byte are framed messages
			return new ThriftMessage(messageBuffer, ThriftTransportType.FRAMED);
		}
	}

	protected ChannelBuffer tryDecodeFramedMessage(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer,
			boolean stripFraming) {
		// Framed messages are prefixed by the size of the frame (which doesn't include
		// the
		// framing itself).

		int messageStartReaderIndex = buffer.readerIndex();
		int messageContentsOffset = stripFraming ? messageStartReaderIndex + MESSAGE_FRAME_SIZE
				: messageStartReaderIndex;

		// The full message is larger by the size of the frame size prefix
		int messageLength = buffer.getInt(messageStartReaderIndex) + MESSAGE_FRAME_SIZE;
		int messageContentsLength = messageStartReaderIndex + messageLength - messageContentsOffset;

		if (messageContentsLength > maxFrameSize)
			Channels.fireExceptionCaught(ctx,
					new TooLongFrameException("Maximum frame size of " + maxFrameSize + " exceeded ."));

		if (messageLength == 0) {
			// Zero-sized frame: just ignore it and return nothing
			buffer.readerIndex(messageContentsOffset);
			return null;
		} else if (buffer.readableBytes() < messageLength)
			// Full message isn't available yet, return nothing for now
			return null;
		else {
			// Full message is available, return it
			ChannelBuffer messageBuffer = extractFrame(buffer, messageContentsOffset, messageContentsLength);
			buffer.readerIndex(messageStartReaderIndex + messageLength);
			return messageBuffer;
		}
	}

	protected ChannelBuffer tryDecodeUnframedMessage(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer,
			TProtocolFactory inputProtocolFactory) throws TException {
		// Perform a trial decode, skipping through
		// the fields, to see whether we have an entire message available.

		int messageLength = 0;
		int messageStartReaderIndex = buffer.readerIndex();

		try {
			TNiftyTransport decodeAttemptTransport = new TNiftyTransport(channel, buffer, ThriftTransportType.UNFRAMED);
			int initialReadBytes = decodeAttemptTransport.getReadByteCount();
			TProtocol inputProtocol = inputProtocolFactory.getProtocol(decodeAttemptTransport);

			// Skip through the message
			inputProtocol.readMessageBegin();
			TProtocolUtil.skip(inputProtocol, TType.STRUCT);
			inputProtocol.readMessageEnd();

			messageLength = decodeAttemptTransport.getReadByteCount() - initialReadBytes;
		} catch (TTransportException | IndexOutOfBoundsException e) {
			// No complete message was decoded: ran out of bytes
			return null;
		} finally {
			if (buffer.readerIndex() - messageStartReaderIndex > maxFrameSize)
				Channels.fireExceptionCaught(ctx,
						new TooLongFrameException("Maximum frame size of " + maxFrameSize + " exceeded ."));

			buffer.readerIndex(messageStartReaderIndex);
		}

		if (messageLength <= 0)
			return null;

		// We have a full message in the read buffer, slice it off
		ChannelBuffer messageBuffer = extractFrame(buffer, messageStartReaderIndex, messageLength);
		buffer.readerIndex(messageStartReaderIndex + messageLength);
		return messageBuffer;
	}

	protected ChannelBuffer extractFrame(ChannelBuffer buffer, int index, int length) {
		// Slice should be sufficient here (and avoids the copy in
		// LengthFieldBasedFrameDecoder)
		// because we know no one is going to modify the contents in the read buffers.
		return buffer.slice(index, length);
	}
}
