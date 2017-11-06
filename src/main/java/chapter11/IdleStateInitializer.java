package chapter11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * created by xdCao on 2017/11/6
 */

public class IdleStateInitializer extends ChannelInitializer<Channel> {

    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new IdleStateHandler(0,0,60, TimeUnit.SECONDS));
        pipeline.addLast(new HeartBeatHandler());

    }
}
