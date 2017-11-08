package nettyGuide.http;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedFile;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URLDecoder;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderUtil.isKeepAlive;
import static io.netty.handler.codec.http.HttpHeaderUtil.setContentLength;
import static io.netty.handler.codec.http.HttpResponseStatus.*;

/**
 * created by xdCao on 2017/11/8
 */

public class FileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {



    public FileServerHandler(String url) {

    }

    protected void messageReceived(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {
        if (!fullHttpRequest.decoderResult().isSuccess()){
            sendError(channelHandlerContext,BAD_REQUEST);
            return;
        }
        if (fullHttpRequest.method()!= HttpMethod.GET){
            sendError(channelHandlerContext,METHOD_NOT_ALLOWED);
            return;
        }
        final String uri=fullHttpRequest.uri();
        final String path=sanitizeUri(uri);
        if (path==null){
            sendError(channelHandlerContext,FORBIDDEN);
            return;
        }
        File file=new File(path);
        if (file.isHidden()||!file.exists()){
            sendError(channelHandlerContext,NOT_FOUND);
            return;
        }
        if (!file.isFile()){
            sendError(channelHandlerContext,FORBIDDEN);
            return;
        }
        RandomAccessFile randomAccessFile=null;
        try {
            randomAccessFile=new RandomAccessFile(file,"r");
        }catch (Exception e){
            sendError(channelHandlerContext,NOT_FOUND);
            return;
        }
        long fileLength=randomAccessFile.length();
        HttpResponse response=new DefaultHttpResponse(HttpVersion.HTTP_1_1,OK);
        setContentLength(response,fileLength);
        if (isKeepAlive(fullHttpRequest)){
            response.headers().set(CONNECTION,HttpHeaderValues.KEEP_ALIVE);
        }
        channelHandlerContext.write(response);
        ChannelFuture sendFileFuture;
        sendFileFuture=channelHandlerContext.write(new ChunkedFile(randomAccessFile,0,fileLength,8192),channelHandlerContext.newProgressivePromise());
        sendFileFuture.addListener(new ChannelProgressiveFutureListener() {
            public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) throws Exception {
                System.out.println("文件传输中....");
            }

            public void operationComplete(ChannelProgressiveFuture future) throws Exception {
                System.out.println("文件传输完毕...");
            }
        });
    }

    private String sanitizeUri(String uri) {
        try {
            uri= URLDecoder.decode(uri,"utf-8");
        }catch (Exception e){
            try {
                uri=URLDecoder.decode(uri,"iso-8859-1");
            }catch (Exception e1){
                throw new Error();
            }
        }
        uri=uri.replace('/',File.separatorChar);
        if (uri.contains(File.separator+'.')||uri.contains('.'+File.separator)||uri.startsWith(".")||uri.endsWith(".")){
            return null;
        }
        return System.getProperty("user.dir")+File.separator+uri ;
    }

    private void sendError(ChannelHandlerContext channelHandlerContext, HttpResponseStatus badRequest) {

    }
}
