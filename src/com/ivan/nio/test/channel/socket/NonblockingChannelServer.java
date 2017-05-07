package com.ivan.nio.test.channel.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

public class NonblockingChannelServer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			ServerSocketChannel ssc = ServerSocketChannel.open();
			ssc.configureBlocking(false);
			ssc.bind(new InetSocketAddress(123));
			SocketChannel sc = null;
			while((sc=ssc.accept())==null){
				TimeUnit.SECONDS.sleep(1);
				System.out.println("try to accept again...");
			}
			System.out.println("accept connection from : " + sc.getRemoteAddress());
			
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			
			while(sc.read(buffer)!= -1){
				buffer.flip();
				byte[] bytes = new byte[buffer.remaining()];
				buffer.get(bytes);
				System.out.println(new String(bytes));
				buffer.clear();
			}
			
			sc.close();
			ssc.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
