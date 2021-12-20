package Project06_Socket;

import java.net.*;
import java.io.*;

public class Project06_A_Server {

	public static void main(String[] args) {
		
		ServerSocket SvrSock = null;
		
		try {
			SvrSock = new ServerSocket(9999);
			System.out.println("Server Raady!!");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		while(true) {
			
			try {
				Socket Sock = SvrSock.accept();
				System.out.println("Client Connect Success!!");
				
//				DataInputStream dis = new DataInputStream(Sock.getInputStream());
				InputStream is = Sock.getInputStream();
				DataInputStream dis = new DataInputStream(is);
				String msg = dis.readUTF();
				
//				DataOutputStream dos = new DataOutputStream(Sock.getOutputStream());
				OutputStream os = Sock.getOutputStream();
				DataOutputStream dos = new DataOutputStream(os);				
				dos.writeUTF("[ECHO]" + msg + "(from server!!)");
				
				dos.close();
				dis.close();
								
				Sock.close();
				System.out.println("Socket Close!!");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

	}

}
