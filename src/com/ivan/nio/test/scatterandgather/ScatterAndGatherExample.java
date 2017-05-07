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
 * ��ʹ�ø���Channel��ʱ�������Ѿ��۲쵽read��write��������һ����ByteBuffer����Ϊ��������ʽ��
 * ������ʽ��ʵ��Ϊ��֧��ͨ����Scatter��Gather���ԡ�Scatter����˼�ǴӶ��ByteBuffer������
 * ��ȡ���ݵ�һ��Channel�У�Gather����˼���ǽ�Channel�е���������д����ByteBuffer�
 * ��ĳЩ�ض����ϣ�Scatter/Gather���������̵Ĺ����������罫ĳЩ������İ�ͷ�����ݷֱ���벻ͬ�ı�����
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
