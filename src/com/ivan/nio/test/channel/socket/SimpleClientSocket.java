package com.ivan.nio.test.channel.socket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SimpleClientSocket {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Socket socket = new Socket();
		
		try {
			socket.connect(new InetSocketAddress("127.0.0.1",1234));
			OutputStream os = socket.getOutputStream();
//			os.write("hello".getBytes());
//			os.write("world".getBytes());
			os.write("exit".getBytes());
//			os.write("agin".getBytes());
			os.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
