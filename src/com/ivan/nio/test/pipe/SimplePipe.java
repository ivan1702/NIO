package com.ivan.nio.test.pipe;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.concurrent.TimeUnit;

/**
 * Pipe管道是指同一个Java进程内，不同线程间的一种单向数据管道，其sink端通道写入数据，
 * source端通道则读出数据，其间可以保证数据按照写入顺序到达
 * 
 * @author Ivan
 *
 */
public class SimplePipe {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		//创建一个管道，并 拿到管道两端的channel
		Pipe pipe = Pipe.open();
		
		WritableByteChannel write = pipe.sink();
		ReadableByteChannel read = pipe.source();
		
		//创建一个线程从sink端写入数据
		WorkerThread thread = new WorkerThread(write);
		thread.start();
		
		//主线程送source端读取数据，并组成string打印
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		while(read.read(buffer) >= 0){
			
//			TimeUnit.SECONDS.sleep(10);
			
			buffer.flip();
			byte[] bytes = new byte[buffer.remaining()];
			
			buffer.get(bytes);
			
			String str  = new String(bytes);
			
			System.out.println(str);
			buffer.clear();
		}
		
		read.close();
		
	}

	
	private static class WorkerThread extends Thread {
		
		WritableByteChannel channel;
		
		public WorkerThread(WritableByteChannel writableByteChannel){
			this.channel = writableByteChannel;
		}
		
		@Override
		public void run(){
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			for(int i=0; i<10; i++){
				String str = "pipe sink data " + i;
				
				buffer.put(str.getBytes());
				buffer.flip();
				
				try {
					channel.write(buffer);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				buffer.clear();
				
			}
			
			try {
				channel.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
