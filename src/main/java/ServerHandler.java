import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import lombok.SneakyThrows;

public class ServerHandler
        extends ChannelInboundHandlerAdapter {
    private ChannelPromise promise;
    private ResponseData responseData = new ResponseData();

    @SneakyThrows
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        assert promise == null;
        promise = ctx.voidPromise();
        super.channelRegistered(ctx);
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) {
        final RequestData requestData = (RequestData) msg;
        responseData.setIntValue(requestData.getIntValue() * 2);
        ctx.writeAndFlush(responseData, promise);
    }
}
