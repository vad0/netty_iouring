import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.time.Duration;
import java.time.Instant;

public class ClientHandler
        extends ChannelInboundHandlerAdapter {
    private static final int N = 100_000;
    private final RequestData requestData = new RequestData();
    private int count = 0;
    private ChannelPromise promise;
    private Instant previous = Instant.now();

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        assert promise == null;
        promise = ctx.voidPromise();
        sendMessage(ctx);
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) {
        sendMessage(ctx);
    }

    public void sendMessage(final ChannelHandlerContext ctx) {
        if (count % N == 0) {
            final Instant now = Instant.now();
            final var nanos = Duration.between(previous, now).toNanos();
            System.out.println("Latency " + nanos / N + " ns");
            previous = now;
        }
        requestData.setIntValue(count++);
        requestData.setStringValue("all work and no play makes jack a dull boy");
        ctx.writeAndFlush(requestData, promise);
    }
}
