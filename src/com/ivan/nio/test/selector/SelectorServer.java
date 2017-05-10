package com.ivan.nio.test.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * ����ע����һ��ServerSocketChannel������������1234�˿��ϵ����ӣ�
 * ������������ʱ���������ϵ�SocketChannel��ע�ᵽSelector�ϣ�
 * ��ЩSocketChannelע�����SelectionKey.OP_READ�¼���
 * ����ЩSocketChannel״̬��Ϊ�ɶ�ʱ����ȡ���ݲ���ʾ�� 
 * 
 * @author Ivan
 *
 */
public class SelectorServer {

	private static final int PORT = 1234;
	private static ByteBuffer  buffer = ByteBuffer.allocate(1024);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			ServerSocketChannel ssc = ServerSocketChannel.open();
			ssc.bind(new InetSocketAddress(PORT));
			ssc.configureBlocking(false);
			//1.register
			Selector selector = Selector.open();
			ssc.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("REGISTER CHANNEL , CHANNEL NUMBER IS : " + selector.keys().size());
			
			while(true){
				//2.select
				int n = selector.select();
				if(n == 0){
					continue;
				}
				
				//3.��ѯSelectionKey
				Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
				while(iterator.hasNext()){
					SelectionKey key = iterator.next();
					//�������Acceptable��������ض���һ��ServerSocketChannel
					if(key.isAcceptable()){
						ServerSocketChannel  sscTemp = (ServerSocketChannel) key.channel();
						//�õ�һ�����Ӻõ�SocketChannel��������ע�ᵽSelector�ϣ���Ȥ����ΪREAD
						SocketChannel socketChannel = sscTemp.accept();
						socketChannel.configureBlocking(false);
						socketChannel.register(selector, SelectionKey.OP_READ);
						System.out.println("REGISTRE CHANNEL , CHANNEL NUMBER IS : " + selector.keys().size());
						
					}
					//�������Readable��������ض��� һ��SocketChannel
					if(key.isReadable()){
						//��ȡͨ���е�����
						SocketChannel channel = (SocketChannel) key.channel();
						readFromChannel(channel);
					}
					//4.remove SelectionKey
					iterator.remove();
				}
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void readFromChannel(SocketChannel channel){
		buffer.clear();
		
		try {
			while(channel.read(buffer)>0){
				buffer.flip();
				byte[] bytes = new byte[buffer.remaining()];
				buffer.get(bytes);
				System.out.println("READ FROM CLIENT : " + new String(bytes));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
