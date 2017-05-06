package com.ivan.nio.test.channel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class FileLockExample {

	private static Random rand = new Random();

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		if (args.length < 1) {
			System.out.println("Usage: [-r | -w]");
			System.exit(1);
		}

		boolean isWriter = "-w".equals(args[0]);

		try {
			RandomAccessFile file = new RandomAccessFile(FileConstance.filePath, isWriter ? "rw" : "r");
			FileChannel channel = file.getChannel();

			if (isWriter) {
				lockAndWrite(channel);
			} else {
				lockAndRead(channel);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void lockAndRead(FileChannel channel){
		
		try{
			ByteBuffer buffer = ByteBuffer.allocate(4);
			while(true){
				System.out.println("Reader try to lock file...");
				//共享锁   堵塞式锁   非堵塞式为tryLock()方法
				FileLock lock = channel.lock(0, 4, true);
				buffer.clear();
				channel.read(buffer,0);
				buffer.flip();
				System.out.println("buffer is : " + buffer);
				int i = buffer.getInt(0);
				System.out.println("Reader read : " + i);
				//解锁
				lock.release();
				System.out.println("Sleeping...");
				TimeUnit.SECONDS.sleep(rand.nextInt(30));
			}
			
		}catch(IOException e){
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	private static void lockAndWrite(FileChannel channel) {

		try {
			ByteBuffer buffer = ByteBuffer.allocate(4);
			int i = 0;
			while (true) {
				System.out.println("Writer try to lock file...");
				//鎖住一個我文件的一段内容  模式為false，意味著這是一個獨占鎖
				FileLock lock = channel.lock(0,4,false);
				
				buffer.putInt(0,i);
				buffer.position(0).limit(4);
				System.out.println("buffer is : " + buffer);
				channel.write(buffer,0);
				channel.force(true);
				buffer.clear();
				System.out.println("Writer write : " + i++);
				
				lock.release();
				System.out.println("Sleeping...");
				TimeUnit.SECONDS.sleep(rand.nextInt(30));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
