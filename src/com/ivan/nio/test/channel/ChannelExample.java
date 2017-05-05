package com.ivan.nio.test.channel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class ChannelExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		ReadableByteChannel readableByteChannel = Channels.newChannel(System.in);
		WritableByteChannel writableByteChannel = Channels.newChannel(System.out);
		
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		
		try {
			while(readableByteChannel.read(buffer) != -1){
				buffer.flip();
				while(buffer.hasRemaining()){
					writableByteChannel.write(buffer);
				}
				buffer.clear();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
