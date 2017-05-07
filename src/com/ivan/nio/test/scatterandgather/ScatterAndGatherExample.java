package com.ivan.nio.test.scatterandgather;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import com.ivan.nio.test.channel.file.FileConstance;

/**
 * 在使用各种Channel类时，我们已经观察到read和write方法还有一种以ByteBuffer数组为参数的形式，
 * 这种形式其实是为了支持通道的Scatter和Gather特性。Scatter的意思是从多个ByteBuffer中依次
 * 读取数据到一个Channel中，Gather的意思则是将Channel中的数据依次写入多个ByteBuffer里。
 * 在某些特定场合，Scatter/Gather将大大减轻编程的工作量，例如将某些网络包的包头、内容分别读入不同的变量中
 * 
 * @author Ivan
 *
 */
public class ScatterAndGatherExample {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		ByteBuffer buffer1 = ByteBuffer.allocate(5);
		buffer1.put("hello".getBytes("GBK")).flip();
		
		ByteBuffer buffer2 = ByteBuffer.allocate(6);
		buffer2.put(" world".getBytes("GBK")).flip();
		
		ByteBuffer[] buffers = {buffer1,buffer2};
		
		RandomAccessFile file = new RandomAccessFile(FileConstance.SCATTERFILEPATH,"rw");
		FileChannel channel = file.getChannel();
		
		channel.write(buffers);
		
		channel.force(false);
		channel.close();
		
		showFileContent(FileConstance.SCATTERFILEPATH);
		
		buffer1.clear();
		buffer2.clear();
		
		file = new RandomAccessFile(FileConstance.GATHERFILEPATH,"rw");
		channel = file.getChannel();
		channel.read(buffers);
		String str1 = getBufferContent(buffer1);
		String str2 = getBufferContent(buffer2);
		
		System.out.println("buffer1: " + str1);
		System.out.println("buffer2: " + str2);
		channel.close();
	}

	
	
	private static String getBufferContent(ByteBuffer buffer) throws UnsupportedEncodingException{
		buffer.flip();
		System.out.println(buffer);
		byte[] bytes = new byte[buffer.remaining()];
		buffer.get(bytes);
		return new String(bytes,"GBK");
	}
	
	private static void showFileContent(String filePath){
		
		try {
			FileInputStream fis = new FileInputStream(filePath);
			byte[] bytes = new byte[1024];
			int len = 0;
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while((len=fis.read(bytes))!=-1){
				baos.write(bytes,0,len);
			}
			
			String str = baos.toString("GBK");
			System.out.println("file content: ");
			System.out.println(str);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
