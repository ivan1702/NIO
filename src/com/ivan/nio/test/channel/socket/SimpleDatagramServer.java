package com.ivan.nio.test.channel.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class SimpleDatagramServer {

	private static final int PORT = 73;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			DatagramChannel channel = DatagramChannel.open();
			channel.socket().bind(new InetSocketAddress(PORT));
			
			ByteBuffer buffer  = ByteBuffer.allocate(64);
			while(true){
				buffer.clear();
				
				SocketAddress sa = channel.receive(buffer);
				if(sa == null){
					continue;
				}
				
				buffer.flip();
				System.out.println("receive data from : " + sa);
				byte[] bytes = new byte[buffer.remaining()];
				buffer.get(bytes);
				
				String str = new String(bytes);
				System.out.println("receive data is : " + str);
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
