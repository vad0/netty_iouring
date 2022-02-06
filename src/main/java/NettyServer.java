import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.incubator.channel.uring.IOUringEventLoopGroup;
import io.netty.incubator.channel.uring.IOUringServerSocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class NettyServer {
    private final int port;

    public static void main(final String[] args) {
        System.out.println("Started server");

        final int port = args.length > 0
                ? Integer.parseInt(args[0])
                : 8080;

        new NettyServer(port).run();
    }

    @SneakyThrows
    public void run() {
        final EventLoopGroup loopGroup = new IOUringEventLoopGroup();
        try {
            final ServerBootstrap b = new ServerBootstrap();
            b.group(loopGroup)
                    .channel(IOUringServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(final SocketChannel ch) {
                            ch.pipeline().addLast(
                                    new RequestDecoder(),
                                    new ResponseDataEncoder(),
                                    new ServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            final ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            loopGroup.shutdownGracefully();
        }
    }
}
