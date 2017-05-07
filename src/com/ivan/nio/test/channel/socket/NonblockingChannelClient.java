package com.ivan.nio.test.channel.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

public class NonblockingChannelClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			SocketChannel sc = SocketChannel.open();
			/**
			 * SocketChannel也可以使用configureBlocking(false)方法将自己置于非阻塞模式，
			 * 此模式下调用connect方法会立即返回，返回后可以调用finishConnect()方法进一步确认
			 * 是否建立。在非阻塞模式下，还有一点需要注意的是configureBlocking方法并不应该被随意
			 * 调用。为此，这两个通道还提供了isBlocking()用来返回阻塞模式的查询结果；以及blockingLock()
			 * 方法，用来返回一个锁对象，拥有此锁对象的线程才能调用configureBlocking方法
			 */
			sc.configureBlocking(false);
			sc.connect(new InetSocketAddress("127.0.0.1",123));
			while(!sc.finishConnect()){
				System.out.println("connection has not finished,wait...");
				
				TimeUnit.SECONDS.sleep(100);
				
			}

			ByteBuffer buffer = ByteBuffer.allocate(1024);
			buffer.put("hello".getBytes()).flip();
			sc.write(buffer);

			sc.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block3
			System.out.println("io exception");
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
