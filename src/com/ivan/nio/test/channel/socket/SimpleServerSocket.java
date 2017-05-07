package com.ivan.nio.test.channel.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServerSocket {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			ServerSocket serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress(1234));
			Socket socket = serverSocket.accept();
			System.out.println("accept connection from :" + socket.getRemoteSocketAddress());
			
			InputStream is = socket.getInputStream();
			byte[] bytes = new byte[4];
			while(is.read(bytes) != -1){
				String str = new String(bytes);
				if("exit".equals(str)){
					break;
				}
				System.out.println(str);
			}
			is.close();
			socket.close();
			serverSocket.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
