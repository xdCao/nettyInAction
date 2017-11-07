package nettyGuide.chapter1;

/**
 * created by xdCao on 2017/11/7
 */

public class TimeServer {

    public static void main(String[] args) {
        int port=8080;
        MultiplexerTimeServer server=new MultiplexerTimeServer(port);
        new Thread(server,"NioTimseServer").start();
    }

}
