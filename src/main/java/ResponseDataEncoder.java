import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ResponseDataEncoder
    extends MessageToByteEncoder<ResponseData>
{
    @Override
    protected void encode(final ChannelHandlerContext ctx, final ResponseData msg, final ByteBuf out)
    {
        out.writeInt(msg.getIntValue());
    }
}
