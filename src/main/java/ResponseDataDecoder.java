import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class ResponseDataDecoder
        extends ReplayingDecoder<ResponseData> {
    @Override
    protected void decode(final ChannelHandlerContext ctx, final ByteBuf in, final List<Object> out) {
        final ResponseData data = new ResponseData();
        data.setIntValue(in.readInt());
        out.add(data);
    }
}
