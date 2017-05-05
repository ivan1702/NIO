package com.ivan.nio.test.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * FileInputStream������ͨ��ֻ�ܶ���FileOutputStream������ͨ��ֻ��д��
 * ��RandomAccessFile���Դ���ͬʱ���ж�д���ܵ�ͨ����ʹ��"rw"����������
 * @author Ivan
 *
 */
public class TestChannelCreate {
	
	final static String filePath = "G:\\Workspace\\NIO\\NIO\\testFile\\channel.txt";

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		RandomAccessFile randomAccessFile  = new RandomAccessFile(filePath,"rw");
		
		FileChannel readAndWriteChannel = randomAccessFile.getChannel();
		
		ByteBuffer buffer1 = ByteBuffer.allocate(1024);
		readAndWriteChannel.read(buffer1);
		buffer1.flip();
		readAndWriteChannel.write(buffer1);
		
		FileInputStream fis = new FileInputStream(filePath);
		FileChannel readChannel = fis.getChannel();
		FileOutputStream fos = new FileOutputStream(filePath);
		FileChannel writeChannel = fos.getChannel();
		
		
		randomAccessFile.close();
		readChannel.close();
		writeChannel.close();
		
	}

}
