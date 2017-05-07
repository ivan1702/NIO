package com.ivan.nio.test.channel.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.TimeUnit;

public class SimpleDatagramClient {

	private static final int PORT = 73;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			DatagramChannel channel = DatagramChannel.open();
			int i = 0;
			
			while(true){
				TimeUnit.SECONDS.sleep(4);
				ByteBuffer buffer = ByteBuffer.allocate(64);
				
				String str = "data from client " + i++;
				
				buffer.put(str.getBytes());
				
				InetSocketAddress sa = new InetSocketAddress("127.0.0.1",PORT);
				
				if(sa == null){
					System.out.println("address is null ");
					continue;
				}
				
				buffer.flip();
				channel.send(buffer, sa);
				
				System.out.println("send data : " + str);
				
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
