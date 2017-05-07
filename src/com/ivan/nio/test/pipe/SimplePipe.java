package com.ivan.nio.test.pipe;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.concurrent.TimeUnit;

/**
 * Pipe�ܵ���ָͬһ��Java�����ڣ���ͬ�̼߳��һ�ֵ������ݹܵ�����sink��ͨ��д�����ݣ�
 * source��ͨ����������ݣ������Ա�֤���ݰ���д��˳�򵽴�
 * 
 * @author Ivan
 *
 */
public class SimplePipe {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		//����һ���ܵ����� �õ��ܵ����˵�channel
		Pipe pipe = Pipe.open();
		
		WritableByteChannel write = pipe.sink();
		ReadableByteChannel read = pipe.source();
		
		//����һ���̴߳�sink��д������
		WorkerThread thread = new WorkerThread(write);
		thread.start();
		
		//���߳���source�˶�ȡ���ݣ������string��ӡ
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
