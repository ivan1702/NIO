package com.ivan.nio.test.channel.file;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class TestFileHole {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			FileOutputStream fos = new FileOutputStream(FileConstance.filePath);
			StringBuilder sb =  new StringBuilder();
			for(char c = 'a'; c<='z'; c++){
				sb.append(c);
			}
			
			fos.write(sb.toString().getBytes());
			fos.flush();
			fos.close();
			
			RandomAccessFile file = new RandomAccessFile(FileConstance.filePath,"rw");
			System.out.println("file length is :" + file.length());
			FileChannel channel = file.getChannel();
			channel.position(100);
			
			channel.write((ByteBuffer) ByteBuffer.allocate(5).put("00".getBytes()).flip());
			
			System.out.println("file position in RandomAccessFile is : " + file.getFilePointer());
			System.out.println("file length is :" + file.length());
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
