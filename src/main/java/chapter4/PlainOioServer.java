package chapter4;

import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * created by xdCao on 2017/11/5
 */

public class PlainOioServer {

    public void serve(int port) throws IOException {
        final ServerSocket socket=new ServerSocket(port);
        try {
            for (;;){
                final Socket clientSocket=socket.accept();
                System.out.println("Accepted connection from: "+clientSocket);
                new Thread(new Runnable() {
                    public void run() {
                        OutputStream outputStream;
                        try {
                            outputStream=clientSocket.getOutputStream();
                            outputStream.write("Hi!\r\n".getBytes(Charset.forName("utf-8")));
                            outputStream.flush();
                            clientSocket.close();
                        }catch (IOException e){
                            e.printStackTrace();
                        }finally {
                            try {
                                clientSocket.close();
                            }catch (Exception e){}
                        }
                    }
                }).start();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
