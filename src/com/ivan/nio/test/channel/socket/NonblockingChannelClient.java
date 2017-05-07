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
			 * SocketChannelҲ����ʹ��configureBlocking(false)�������Լ����ڷ�����ģʽ��
			 * ��ģʽ�µ���connect�������������أ����غ���Ե���finishConnect()������һ��ȷ��
			 * �Ƿ������ڷ�����ģʽ�£�����һ����Ҫע�����configureBlocking��������Ӧ�ñ�����
			 * ���á�Ϊ�ˣ�������ͨ�����ṩ��isBlocking()������������ģʽ�Ĳ�ѯ������Լ�blockingLock()
			 * ��������������һ��������ӵ�д���������̲߳��ܵ���configureBlocking����
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
