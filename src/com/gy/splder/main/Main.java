package com.gy.splder.main;


import java.util.Map.Entry;

import com.gy.splider.bean.OriginEntity;
import com.gy.splider.global.Global;
import com.gy.splider.server.DirectorService;
import com.gy.splider.server.MainService;
import com.gy.splider.server.MovieService;
import com.gy.splider.storage.DataStorage;

public class Main {
	public static void main(String[] args) {
		System.out.println("Service start,loading......");
		MainService mainService = new MainService("https://movie.douban.com", Global.WEBMOVIE, "10791575");
		mainService.startActivity();
		//System.out.println(DataStorage.getTotalNumber());
		/*MovieService movieService = new MovieService("https://movie.douban.com","subject","4301662");
		movieService.getActor();
		for(Entry<String, OriginEntity> item : DataStorage.getActorStorage().entrySet()){
			System.out.println(item.getKey());
			System.out.println(item.getValue().getName());
		}*/
		/*DirectorService directorService= new DirectorService("https://movie.douban.com","celebrity","1035672");
		directorService.getRecentMovie();
		for(Entry<String, OriginEntity> item : DataStorage.getMovieStorage().entrySet()){
			System.out.println(item.getKey());
			System.out.println(item.getValue().getName());
			System.out.println(item.getValue().getFromDoubanId());
		}*/
		/*System.setProperty("http.maxRedirects", "50");  
	    System.getProperties().setProperty("proxySet", "true");
	    System.getProperties().setProperty("http.proxyHost", "58.246.242.154");  
        System.getProperties().setProperty("http.proxyPort", "8080");*/
		
		
		
		
		
		/*for(Entry<String, OriginEntity> item : DataStorage.getMovieStorage().entrySet()){
			System.out.println(item.getKey());
			System.out.println(item.getValue().getName());
			System.out.println(item.getValue().getFromDoubanId());
		}*/
	}
}
