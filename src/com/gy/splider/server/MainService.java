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
		for(Thread thread2 : DataStorage.getThreadList()){
			try {
				thread2.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(DataStorage.getTotalNumber());
	}
	
	public MainService(String movieUrl, String paramtype, String paramId) {
		super();
		this.baseUrl = movieUrl;
		this.method = this.GETMETHOD;
		this.paramId = paramId;
		this.paramtype = paramtype;
		Connection connect = Jsoup.connect(movieUrl + "/" + paramtype + "/"
				+ paramId);
		if (this.method == this.GETMETHOD) {
			try {
				this.doc = connect.get();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				this.doc = connect.post();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
