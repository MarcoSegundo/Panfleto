package br.bcc.middleware.panfleto;

public class Endereco {
	private String ip;
	private String port;
	
	public Endereco(String ip, String port){
		this.ip = ip;
		this.port = port;
	}
	
	public String getIp(){
		return this.ip;
	}
	
	public void setIp(String ip){
		this.ip = ip;
	}
	
	public String getPort(){
		return this.port;
	}
	
	public void setPort(String port){
		this.port = port;
	}
}
