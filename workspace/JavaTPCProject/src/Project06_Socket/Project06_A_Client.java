package Project06_Socket;

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Project06_A_Client {

	public static void main(String[] args) {

		try {
			Socket Sock = new Socket("127.0.0.1", 9999);
			System.out.println("Connection Success!!");
			
			Scanner scanner = new Scanner(System.in);
			String msg = scanner.nextLine();
			OutputStream os = Sock.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			dos.writeUTF(msg);
			
			InputStream is = Sock.getInputStream();
			DataInputStream dis = new DataInputStream(is);
			System.out.println("RCV : " + dis.readUTF());
			
			dis.close();
			dos.close();
			
			Sock.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
