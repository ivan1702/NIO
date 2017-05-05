package com.ivan.nio.test.buffer;

import java.nio.CharBuffer;


/**
 * ���������ĸ�����ָ����״̬��
 * ����(Capacity)���������ܹ����ɵ�����Ԫ�ص������������ʼ�趨���ܸ���
 * �Ͻ�(Limit)���������е�һ�����ܱ�������д��Ԫ��λ�á�����˵�����������ִ�Ԫ�ص��Ͻ�
 * λ��(Position)����������һ����Ҫ������д��Ԫ��λ�ã��ڽ��ж�д������ʱ��λ�û��Զ�����
 * ���(Mark)��һ������λ�ã���ʼʱΪ��δ���塱������markʱmark=position������resetʱ��position=mark��
 * 
 * ���ĸ����������������¹�ϵ��
 * mark<=position<=limit<=capacity
 * 
 * @author Ivan
 *
 */
public class BufferTest {

	
	/**
	 * ��ʾbuffer��position��limit��capacity��buffer�а������ַ���
	 * ���ַ�Ϊ0�����滻Ϊ'.'
	 * @param buffer
	 */
	private static void showBuffer(CharBuffer buffer){
		StringBuilder sb = new StringBuilder();
		for(int i=buffer.position(); i<buffer.limit(); i++){
			char c = buffer.get(i);
			if(c == 0){
				c = '.';
			}
			sb.append(c);
		}
		System.out.printf("position=%d,limit=%d,capacity=%d,content=%s\n", 
				buffer.position(),buffer.limit(),buffer.capacity(),sb.toString());
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//����buffer�ĸ�������
//		testProperties();
		
		//����mark���
//		testMark();
		
		//����put
//		testPut();
		
		//����get
//		testGet();
		
		//��϶�д
//		mixPutAndGet();
		
		//����flip()
//		testFlip();
		
		//����compact()
//		testCompact();
		
		//���Ը���
//		testDuplicate();
		
		//slice��������Ƭ
		testSlice();
		
	}

	
	private static void testSlice(){
		CharBuffer buffer = CharBuffer.allocate(10);
		buffer.put("abcdefghi");
		buffer.flip();
		buffer.position(5);
		CharBuffer slice = buffer.slice();
		showBuffer(buffer);
		showBuffer(slice);
		
	}
	
	
	/**
	 * ���ƻ���������������������ʵ����ָ����ͬһ���ڲ����飬���ֱ������Ե�����
	 * 
	 * buffer��buffer2������ֵ��һ���ģ���position��limit��һ��
	 */
	private static void testDuplicate(){
		CharBuffer buffer = CharBuffer.allocate(10);
		buffer.put("abcdef");
		CharBuffer buffer2 = buffer.duplicate();
		buffer2.clear();
		buffer.clear();
		buffer2.put("alex");
		
		showBuffer(buffer);
		showBuffer(buffer2);
	}
	
	/**
	 * ѹ��compact()������Ϊ�˽���ȡ��һ���ֵ�buffer��
	 * ��ʣ�µĲ�������Ų����buffer��ͷ��(����0��ʼ��һ��λ��)��
	 * ���ں�����д����߶�ȡ���京��Ϊlimit=limit-position��position=0,
	 */
	private static void testCompact(){
		CharBuffer buffer = CharBuffer.allocate(10);
		buffer.put("abc");//postion=3  limit=10
		showBuffer(buffer);
		buffer.flip(); //postion=0  limit=3
		
		//�ȶ�ȡ�����ַ�
		buffer.get();//postion=1  limit=3
		buffer.get();//postion=2  limit=3
		showBuffer(buffer);//c
		
		//ѹ��
		buffer.compact(); //postion=1   limit=10
		showBuffer(buffer);
		//����д��
		buffer.put("defgi");//postion=6   limit=10
		showBuffer(buffer);
		buffer.flip();//postion=0   limit=6
		showBuffer(buffer);
		
		//��ͷ��ȡ�������ַ�
		char[] chars = new char[buffer.remaining()];
		buffer.get(chars);
		System.out.println(chars);
	}
	
	private static void testFlip(){
		CharBuffer buffer = CharBuffer.allocate(10);
		buffer.put("abc");
		buffer.flip();
		char[] chars = new char[buffer.remaining()];
		buffer.get(chars);
//		System.out.println(chars);
		
		//һ�²�����flip��ͬ
		buffer.clear();
		buffer.put("abc");
		buffer.limit(buffer.position());
		buffer.position(0);
		chars = new char[buffer.remaining()];
		buffer.get(chars);
		System.out.print(chars);
	}
	
	private static void mixPutAndGet(){
		CharBuffer buffer = CharBuffer.allocate(10);
		buffer.put("abc");
		System.out.println(buffer.get());
		
		buffer.put("def");
		showBuffer(buffer); //��ȡ��buffer������
		buffer.flip();
		/**
		 * remaining()���صĽ�����limit-position��ֵ
		 * hasRemaining()�ĺ����ǲ�ѯ���������Ƿ���Ԫ�أ���������ĺô��������̰߳�ȫ��
		 */
		char[] chars = new char[buffer.remaining()];
	    buffer.get(chars);
		System.out.println(chars);
		showBuffer(buffer); //��ȡ��buffer������
	}
	
	private static void testGet(){
		CharBuffer buffer = CharBuffer.allocate(10);
		buffer.put("abc");
		buffer.flip();
		
		//��һ��ȡ��
		char char1 = buffer.get();
		char char2 = buffer.get();
		char char3 = buffer.get();
		System.out.println("ȡ���� " + char1 + char2 + char3);
		buffer.clear();
		
		//�ڶ���ȡ��
		buffer.put("abc");
		buffer.flip();
		
		char[] chars = new char[buffer.remaining()];
		buffer.get(chars);
		System.out.println(chars);
		
	}
	
	private static void testPut(){
		CharBuffer buffer = CharBuffer.allocate(10);
		//��һ��put����
		buffer.put('a').put('b').put('c');
		buffer.clear();
		showBuffer(buffer);
		//�ڶ���put����
		char[] chars = {'a','b','c'};
		buffer.put(chars);
		buffer.clear();
		showBuffer(buffer);
		//CharBuffer������ʹ��String
		buffer.put("abc");
//		buffer.clear();
		showBuffer(buffer);
		
	}
	
	private static void testMark(){
		CharBuffer buffer = CharBuffer.allocate(10);
		
		showBuffer(buffer);
		
		//����markΪ3
		buffer.position(3).mark().position(5);
		showBuffer(buffer);
		
		buffer.reset();
		showBuffer(buffer);
		
		
//		buffer.flip();
//		showBuffer(buffer);
//		
//		buffer.clear();
//		showBuffer(buffer);
		
		
	}
	
	
	/**
	 * ����buffer�ĸ�������
	 */
	private static void testProperties(){
		CharBuffer buffer = CharBuffer.allocate(10);//buffer�ĳ�ʼ��
		
		showBuffer(buffer);
		
		buffer.put("abc");//���������ַ����״̬
		showBuffer(buffer);
		
		/**
		 * flip���״̬
		 * limit=position, position=0
		 * 
		 * Flips this buffer. The limit is set to the current position 
		 * and then the position is set to zero. If the mark is defined 
		 * then it is discarded. 
		 * 
		 */
		buffer.flip();
		showBuffer(buffer);
		
		char c1 = buffer.get();
		char c2 = buffer.get();
		System.out.println("c1=" + c1 + ",  c2=" + c2);
		showBuffer(buffer);
		
		/**
		 * clear���״̬
		 * limit=capacity, position=0
		 * 
		 * Clears this buffer. The position is set to zero, the limit is set to the capacity, 
		 * and the mark is discarded. 
		 */
		buffer.clear();
		showBuffer(buffer);
		
	}
	
}
