import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class RequestDataEncoder
        extends MessageToByteEncoder<RequestData> {
    private final Charset charset = StandardCharsets.UTF_8;

    @Override
    protected void encode(final ChannelHandlerContext ctx, final RequestData msg, final ByteBuf out) {
        out.writeInt(msg.getIntValue());
        out.writeInt(msg.getStringValue().length());
        out.writeCharSequence(msg.getStringValue(), charset);
    }
}
