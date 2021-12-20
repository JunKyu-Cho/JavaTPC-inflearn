package Project06_Socket;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Project06_MultiChatClient {

	public static void main(String[] args) {
		try {
			Socket Sock = new Socket("127.0.0.1", 9999);
			Scanner scanner = new Scanner(System.in);
			
			System.out.print("name : ");
			String name = scanner.nextLine();
			
			Thread sender = new Thread(new ClientSender(Sock, name));
			Thread receiver = new Thread(new ClientRcv(Sock));
			
			sender.start();
			receiver.start();
			
		} catch (Exception e) { e.printStackTrace(); }

	}
	
	static class ClientSender extends Thread {
		Socket Sock;
		DataOutputStream os;
		String name;
		ClientSender(Socket Sock, String name){
			this.Sock = Sock;
			this.name = name;
			try {
				os = new DataOutputStream(Sock.getOutputStream());				
			} catch (Exception e) { e.printStackTrace(); }
		}
		
		public void run() {
			Scanner scanner = new Scanner(System.in);
			try {
				if(os != null)	os.writeUTF(name);
				while(os != null) {
					String message = scanner.nextLine();
					if(message.equals("quit"))	break;
					os.writeUTF("[" + name + "]" + message);					
				}
				
				os.close();
				Sock.close();
			} catch (Exception e) { e.printStackTrace(); }
		}		
	}

	static class ClientRcv extends Thread {
		Socket Sock;
		DataInputStream is;
		ClientRcv(Socket Sock) {
			this.Sock = Sock;
			try {
				is = new DataInputStream(Sock.getInputStream());
			} catch (IOException e) { e.printStackTrace(); }
		}
		
		public void run() {
			while(is != null) {
				try {
					System.out.println(is.readUTF());					
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}
			
			try {
				is.close();
				Sock.close();
			} catch (Exception e) { e.printStackTrace(); }

		}
	}	
}
