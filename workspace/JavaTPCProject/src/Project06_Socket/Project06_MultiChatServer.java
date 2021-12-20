package Project06_Socket;

import java.io.*;
import java.net.*;
import java.util.*;

public class Project06_MultiChatServer {
	
	HashMap clients;	
	public Project06_MultiChatServer() {		
		clients = new HashMap();
		Collections.synchronizedMap(clients);
	}

	public void start()	{		
		ServerSocket serverSock = null;
		Socket Sock = null;
		try {
			serverSock = new ServerSocket(9999);
			System.out.println("Start Server!!");
			
			while(true) {
				Sock = serverSock.accept();
				System.out.println(Sock.getInetAddress() + " : " + Sock.getPort() + "Connect!!");
				ServerRcv thread = new ServerRcv(Sock);
				thread.start();	// run() 실행
			}			
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	void sendToAll(String msg) {	// 브로드캐스팅
		Iterator iterator = clients.keySet().iterator();
		while(iterator.hasNext()) {
			try {				
				DataOutputStream os = (DataOutputStream) clients.get(iterator.next());
				os.writeUTF(msg);				
			} catch (Exception e) { e.printStackTrace(); }
		}
	}
	

	public static void main(String[] args) {		
		new Project06_MultiChatServer().start();
	}
	
	// inner class
	class ServerRcv extends Thread {
		
		Socket Sock;
		DataInputStream is;
		DataOutputStream os;
		
		ServerRcv(Socket Sock) {		
			this.Sock = Sock;
			try {				
				is = new DataInputStream(Sock.getInputStream());
				os = new DataOutputStream(Sock.getOutputStream());				
			} catch (Exception e) { e.printStackTrace(); }
		}
		
		public void run() {		
			String name = "";
			try {				
				name = is.readUTF();
				if(clients.get(name) != null) {		// 같은 이름의 사용자 존재
					os.writeUTF("#Already exist name : " + name);
					os.writeUTF("#please reconnect by other name !!");
					System.out.println(Sock.getInetAddress() + " : " + Sock.getPort() + " Disconnect!!");
					
					is.close();
					os.close();
					Sock.close();
					Sock = null;					
				} else {					
					sendToAll("#" + name + " join!!");
					clients.put(name, os);
					while(is != null) {
						sendToAll(is.readUTF());
					}					
				}				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {				
				if(Sock != null) {					
					sendToAll("#" + name + " Exit!!");
					clients.remove(name);
					System.out.println(Sock.getInetAddress() + " : " + Sock.getPort() + " Disconnect!!");
				}					
			}			
		}
	}
}

