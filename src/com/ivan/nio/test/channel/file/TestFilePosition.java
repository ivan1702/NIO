package com.ivan.nio.test.channel.file;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import com.ivan.nio.test.buffer.BufferTest;

public class TestFilePosition {

//	final static String filePath = "G:\\Workspace\\NIO\\NIO\\testFile\\channel.txt";
	
	public static void main(String[] args) throws IOException {
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
			FileChannel channel = file.getChannel();
			System.out.println("file position in fileChannel is :" + channel.position());
			file.seek(5);
			System.out.println("file position in fileChannel is : " + channel.position());
			channel.position(7);
			System.out.println("file position in fileChannel is : " + file.getFilePointer());
			
			ByteBuffer buffer = ByteBuffer.allocate(30);
			channel.read(buffer);
			buffer.flip();
//			BufferTest.showBuffer(buffer.asCharBuffer());
			System.out.println("read file position :" + new String(buffer.array(),"utf-8"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
