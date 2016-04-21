package com.gy.splider.bean;

public class ProxyIpEntity {
	public String Ip;
	public int Port;
	public ProxyIpEntity(String ip, int port) {
		super();
		Ip = ip;
		Port = port;
	}
	public ProxyIpEntity() {
		super();
	}
	public String getIp() {
		return Ip;
	}
	public void setIp(String ip) {
		Ip = ip;
	}
	public int getPort() {
		return Port;
	}
	public void setPort(int port) {
		Port = port;
	}
	
}
