package com.ivan.nio.test.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 客户端创建了三个线程，每个线程创建一个SocketChannel通道，并连接到服务器，并向服务器发送5条消息。
 * @author Ivan
 *
 */
public class SelectorClient {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ExecutorService executorService = Executors.newCachedThreadPool();
		executorService.submit(new Client("Clinet-1"));
		executorService.submit(new Client("Clinet-2"));
		executorService.submit(new Client("Clinet-3"));
		executorService.shutdown();
	}
	
	static class Client extends Thread {
		private String name;
		private Random random = new Random();
		
		Client(String name){
			this.name = name;
		}
		
		@Override
		public void run(){
			
			try {
				SocketChannel channel = SocketChannel.open();
				channel.configureBlocking(false);
				channel.connect(new InetSocketAddress(1234));
				while(!channel.finishConnect()){
					TimeUnit.SECONDS.sleep(100);
				}
				ByteBuffer buffer = ByteBuffer.allocate(1024);
				for(int i=0; i<5; i++){
					TimeUnit.MICROSECONDS.sleep(100 * random.nextInt(10));
					String str = "Message from " + name + ", number : " + i;
					buffer.put(str.getBytes());
					buffer.flip();
					while(buffer.hasRemaining()){
						channel.write(buffer);
					}
					buffer.clear();
				}
				channel.close();
				
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

}
