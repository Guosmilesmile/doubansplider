package com.gy.splider.server;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import com.gy.splider.global.Global;
import com.gy.splider.storage.DataStorage;

public class MainService extends BaseService{

	@Override
	public void initGetData() {
		
	}
	
	public void startActivity(){
		MovieService movieService = new MovieService(this.baseUrl, this.paramtype, this.paramId);
		Thread thread = new Thread(movieService);
		thread.start();
		try{
			new Thread(){
				public void run() {
					synchronized (Global.OBJECT) {
						try {
							Global.OBJECT.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					System.out.println("totalnumber is "+DataStorage.getTotalNumber());
					DataStorage.PrintAllData();
				};
			}.start();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public MainService(String movieUrl, String paramtype, String paramId) {
		super();
		this.baseUrl = movieUrl;
		this.method = this.GETMETHOD;
		this.paramId = paramId;
		this.paramtype = paramtype;
		if (this.method == this.GETMETHOD) {
			try {
				this.doc = Global.getProxyConnectionDocument(movieUrl, paramtype, paramId);
			} catch (IOException e) {
				System.out.println("doc has problem");
				e.printStackTrace();
			}
		} else {
			try {
				this.doc = Global.postProxyConnectionDocument(movieUrl, paramtype, paramId);
			} catch (IOException e) {
				System.out.println("doc has problem");
				e.printStackTrace();
			}
		}
		//Connection connect = Global.getProxyConnection(movieUrl, paramtype, paramId);
		//Connection connect = Jsoup.connect(movieUrl + "/" + paramtype + "/"+ paramId).userAgent(Global.USERAGENT);
		
		/*if (this.method == this.GETMETHOD) {
			try {
				this.doc = connect.get();
			} catch (IOException e) {
				System.err.println("代理无法使用");
				new MainService(movieUrl, paramtype, paramId);
			}
		} else {
			try {
				this.doc = connect.post();
			} catch (IOException e) {
				new MainService(movieUrl, paramtype, paramId);
			}
		}*/
	}
}
