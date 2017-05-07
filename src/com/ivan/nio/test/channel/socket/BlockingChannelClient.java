package com.ivan.nio.test.channel.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class BlockingChannelClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			SocketChannel sc = SocketChannel.open();
			sc.connect(new InetSocketAddress("127.0.0.1",1234));
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			buffer.put("hello".getBytes());
			buffer.put(" world".getBytes()).flip();
			sc.write(buffer);
//			buffer.put("world".getBytes()).flip();
//			sc.write(buffer);
			sc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
