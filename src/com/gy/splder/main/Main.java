package com.gy.splder.main;


import java.util.Map.Entry;
import com.gy.splider.bean.OriginEntity;
import com.gy.splider.server.DirectorService;
import com.gy.splider.server.MovieService;
import com.gy.splider.storage.DataStorage;

public class Main {
	public static void main(String[] args) {
		/*MovieService movieService = new MovieService("https://movie.douban.com","subject","4301662");
		movieService.getActor();
		for(Entry<String, OriginEntity> item : DataStorage.getActorStorage().entrySet()){
			System.out.println(item.getKey());
			System.out.println(item.getValue().getName());
		}*/
		DirectorService directorService= new DirectorService("https://movie.douban.com","celebrity","1303915");
		directorService.getRecentMovie();
		for(Entry<String, OriginEntity> item : DataStorage.getMovieStorage().entrySet()){
			System.out.println(item.getKey());
			System.out.println(item.getValue().getName());
			System.out.println(item.getValue().getFromDoubanId());
		}
	}
}
