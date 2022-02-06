import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class RequestDecoder
        extends ReplayingDecoder<RequestData> {
    @Override
    protected void decode(final ChannelHandlerContext ctx, final ByteBuf in, final List<Object> out) {

        final RequestData data = new RequestData();
        data.setIntValue(in.readInt());
        final int strLen = in.readInt();
        data.setStringValue(in.readCharSequence(strLen, StandardCharsets.UTF_8).toString());
        out.add(data);
    }
}
