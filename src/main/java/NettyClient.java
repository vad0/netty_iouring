import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.incubator.channel.uring.IOUringEventLoopGroup;
import io.netty.incubator.channel.uring.IOUringSocketChannel;
import lombok.SneakyThrows;

public class NettyClient {
    @SneakyThrows
    public static void main(final String[] args) {
        System.out.println("Started client");

        final String host = "localhost";
        final int port = 8080;
        final EventLoopGroup loopGroup = new IOUringEventLoopGroup();

        try {
            final Bootstrap b = new Bootstrap()
                    .group(loopGroup)
                    .channel(IOUringSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(final SocketChannel ch) {
                            final var cfg = ch.config();
                            cfg.setAutoClose(false);
                            ch.pipeline().addLast(
                                    new RequestDataEncoder(),
                                    new ResponseDataDecoder(),
                                    new ClientHandler());
                        }
                    });

            final ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } finally {
            loopGroup.shutdownGracefully();
        }
    }
}
