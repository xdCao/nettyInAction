package chapter4;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * created by xdCao on 2017/11/5
 */

public class PlainNioSever {

    public void serve(int port) throws Exception{

        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket=serverSocketChannel.socket();
        InetSocketAddress address=new InetSocketAddress(port);
        serverSocket.bind(address);

        Selector selector=Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        final ByteBuffer msg=ByteBuffer.wrap("Hi!\r\n".getBytes());
        for (;;){
            try {
                selector.select();
            }catch (Exception e){
                e.printStackTrace();
                break;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                iterator.remove();
                try {
                    if (key.isAcceptable()){
                        ServerSocketChannel server= (ServerSocketChannel) key.channel();
                        SocketChannel client=server.accept();
                        client.configureBlocking(false);
                        client.register(selector,SelectionKey.OP_WRITE|SelectionKey.OP_READ,msg.duplicate());
                        System.out.println("Accepted connection from:"+client);
                    }
                    if (key.isWritable()){
                        SocketChannel client= (SocketChannel) key.channel();
                        ByteBuffer buffer= (ByteBuffer) key.attachment();
                        while (buffer.hasRemaining()){
                            if (client.write(buffer)==0){
                                break;
                            }
                        }
                        client.close();
                    }
                }catch (IOException e){
                    key.cancel();
                    try {
                        key.channel().close();
                    }catch (Exception e1){}
                }
            }
        }

    }


}
