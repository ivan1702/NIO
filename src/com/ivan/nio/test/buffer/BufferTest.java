package com.ivan.nio.test.buffer;

import java.nio.CharBuffer;


/**
 * 缓冲区有四个属性指明其状态。
 * 容量(Capacity)：缓冲区能够容纳的数据元素的最大数量。初始设定后不能更改
 * 上界(Limit)：缓冲区中第一个不能被读或者写的元素位置。或者说，缓冲区内现存元素的上界
 * 位置(Position)：缓冲区下一个将要被读或写的元素位置，在进行读写缓冲区时，位置会自动更新
 * 标记(Mark)：一个备忘位置，初始时为“未定义”，调用mark时mark=position，调用reset时，position=mark。
 * 
 * 这四个属性总是满足如下关系：
 * mark<=position<=limit<=capacity
 * 
 * @author Ivan
 *
 */
public class BufferTest {

	
	/**
	 * 显示buffer的position、limit、capacity和buffer中包含的字符，
	 * 若字符为0，则替换为'.'
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

		//测试buffer的各种属性
//		testProperties();
		
		//测试mark标记
//		testMark();
		
		//测试put
//		testPut();
		
		//测试get
//		testGet();
		
		//混合读写
//		mixPutAndGet();
		
		//测试flip()
//		testFlip();
		
		//测试compact()
//		testCompact();
		
		//测试复制
//		testDuplicate();
		
		//slice缓冲区切片
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
	 * 复制缓冲区，两个缓冲区对象实际上指向了同一个内部数组，但分别管理各自的属性
	 * 
	 * buffer和buffer2两个的值是一样的，但position和limit不一样
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
	 * 压缩compact()方法是为了将读取了一部分的buffer，
	 * 其剩下的部分整体挪动到buffer的头部(即从0开始的一段位置)，
	 * 便于后续的写入或者读取。其含义为limit=limit-position，position=0,
	 */
	private static void testCompact(){
		CharBuffer buffer = CharBuffer.allocate(10);
		buffer.put("abc");//postion=3  limit=10
		showBuffer(buffer);
		buffer.flip(); //postion=0  limit=3
		
		//先读取两个字符
		buffer.get();//postion=1  limit=3
		buffer.get();//postion=2  limit=3
		showBuffer(buffer);//c
		
		//压缩
		buffer.compact(); //postion=1   limit=10
		showBuffer(buffer);
		//继续写入
		buffer.put("defgi");//postion=6   limit=10
		showBuffer(buffer);
		buffer.flip();//postion=0   limit=6
		showBuffer(buffer);
		
		//从头读取后续的字符
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
		
		//一下操作与flip等同
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
		showBuffer(buffer); //读取此buffer的内容
		buffer.flip();
		/**
		 * remaining()返回的仅仅是limit-position的值
		 * hasRemaining()的含义是查询缓冲区中是否还有元素，这个方法的好处是它是线程安全的
		 */
		char[] chars = new char[buffer.remaining()];
	    buffer.get(chars);
		System.out.println(chars);
		showBuffer(buffer); //读取此buffer的内容
	}
	
	private static void testGet(){
		CharBuffer buffer = CharBuffer.allocate(10);
		buffer.put("abc");
		buffer.flip();
		
		//第一种取法
		char char1 = buffer.get();
		char char2 = buffer.get();
		char char3 = buffer.get();
		System.out.println("取法： " + char1 + char2 + char3);
		buffer.clear();
		
		//第二种取法
		buffer.put("abc");
		buffer.flip();
		
		char[] chars = new char[buffer.remaining()];
		buffer.get(chars);
		System.out.println(chars);
		
	}
	
	private static void testPut(){
		CharBuffer buffer = CharBuffer.allocate(10);
		//第一种put方法
		buffer.put('a').put('b').put('c');
		buffer.clear();
		showBuffer(buffer);
		//第二种put方法
		char[] chars = {'a','b','c'};
		buffer.put(chars);
		buffer.clear();
		showBuffer(buffer);
		//CharBuffer还可以使用String
		buffer.put("abc");
//		buffer.clear();
		showBuffer(buffer);
		
	}
	
	private static void testMark(){
		CharBuffer buffer = CharBuffer.allocate(10);
		
		showBuffer(buffer);
		
		//设置mark为3
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
	 * 测试buffer的各种属性
	 */
	private static void testProperties(){
		CharBuffer buffer = CharBuffer.allocate(10);//buffer的初始化
		
		showBuffer(buffer);
		
		buffer.put("abc");//存入三个字符后的状态
		showBuffer(buffer);
		
		/**
		 * flip后的状态
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
		 * clear后的状态
		 * limit=capacity, position=0
		 * 
		 * Clears this buffer. The position is set to zero, the limit is set to the capacity, 
		 * and the mark is discarded. 
		 */
		buffer.clear();
		showBuffer(buffer);
		
	}
	
}
