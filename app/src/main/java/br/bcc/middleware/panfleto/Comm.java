package br.bcc.middleware.panfleto;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Comm {
	private int port;
	private String host;
	ServerSocket welcomeSocket;
	Socket connectionSocket;
	Endereco rs;
	
	public Comm(Endereco rs) {
		this.port = Integer.parseInt(rs.getPort());
		this.host = rs.getIp();
	}
	
	public String requestAndReceive(String m) throws Exception {

		Socket clientSocket = null;
		clientSocket = new Socket(host, port);
		ObjectOutputStream outToServer;
		outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
	
		outToServer.writeObject(m);
	
		String retorno = null;
		ObjectInputStream inFromClient; 
		inFromClient = new ObjectInputStream(clientSocket.getInputStream());
		retorno= (String) inFromClient.readObject();
		clientSocket.close();

		return retorno;

}

	public String receive() throws Exception{
		welcomeSocket = new ServerSocket(port);
		connectionSocket = welcomeSocket.accept();
		ObjectInputStream inFromClient; 
		inFromClient = new ObjectInputStream(connectionSocket.getInputStream());
		
		String o = (String)inFromClient.readObject();
		
		Thread.sleep(2);
		
		return o;
		
	}

		public void reply(String msg) throws Exception {
		
		ObjectOutputStream saida = new ObjectOutputStream(connectionSocket.getOutputStream());
		saida.writeObject(msg);
		welcomeSocket.close();
		connectionSocket.close();
		Thread.sleep(2);
	
	}
		
		public Socket receiveThread() throws Exception{
			welcomeSocket = new ServerSocket(port);
			Socket s = welcomeSocket.accept();
			welcomeSocket.close();
			return s;
		}

			
}
