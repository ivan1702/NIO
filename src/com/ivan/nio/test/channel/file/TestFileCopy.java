package com.ivan.nio.test.channel.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class TestFileCopy {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			RandomAccessFile source = new RandomAccessFile(FileConstance.filePath,"rw");
			RandomAccessFile dest = new RandomAccessFile(FileConstance.destFilePath,"rw");
			
			FileChannel sourceChannel = source.getChannel();
			FileChannel destChannel = dest.getChannel();
			
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			while(sourceChannel.read(buffer) != -1){
				buffer.flip();
				while(buffer.hasRemaining()){
					destChannel.write(buffer);
				}
				buffer.clear();
				
			}
			
			dest.close();
			source.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
