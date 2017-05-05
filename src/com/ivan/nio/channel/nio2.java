package com.ivan.nio.channel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class nio2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			RandomAccessFile aFile = new RandomAccessFile("G:\\Workspace\\NIO\\NIO\\src\\com\\ivan\\nio\\channel\\nio2.java","rw");
			FileChannel inChannel = aFile.getChannel();
			//create buffer with capacity of 48 bytes
			ByteBuffer buf = ByteBuffer.allocate(48);
			//read into buffer
			int byteRead = inChannel.read(buf);
			while(byteRead != -1){
				System.out.println("Read " + byteRead);
				buf.flip();//make buffer ready for read
				while(buf.hasRemaining()){
					//read 1 byte at a time
					System.out.println((char)buf.get());
				}
				buf.clear();//make buffer ready for writing
				//buf.compact(); //保留未读取的数据  只清除已经读过的数据 
				byteRead = inChannel.read(buf);
			}
			aFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
