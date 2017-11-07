package chapter8;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * created by xdCao on 2017/11/6
 */

public class ServerBoot {

//    public static void main(String[] args) {
//
//        ServerBootstrap serverBootstrap=new ServerBootstrap();
//        serverBootstrap.group(new NioEventLoopGroup(),new NioEventLoopGroup())
//                .channel(NioServerSocketChannel.class)
//                .childHandler(new SimpleChannelInboundHandler<ByteBuf>() {
//                    ChannelFuture connectFutre;
//                    @Override
//                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//                        Bootstrap bootstrap=new Bootstrap();
//                        bootstrap.channel(NioSocketChannel.class)
//                                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
//                                    @Override
//                                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
//                                        System.out.println("received data");
//                                    }
//                                });
//                        bootstrap.group(ctx.channel().eventLoop());
//                        connectFutre=bootstrap.connect(new InetSocketAddress("www.baidu.com",80));
//                    }
//
//                    @Override
//                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
//                        if (connectFutre.isDone()){
//
//                        }
//                    }
//                });
//
//        ChannelFuture future=serverBootstrap.bind(8080);
//        future.addListener(new ChannelFutureListener() {
//            public void operationComplete(ChannelFuture future) throws Exception {
//                if (future.isSuccess()){
//                    System.out.println("server bound");
//                }else {
//                    System.err.println("Bind attempt failed");
//                    future.cause().printStackTrace();
//                }
//            }
//        });

//    }


}
