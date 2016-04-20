package com.gy.splider.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.omg.CORBA.PUBLIC_MEMBER;

import com.gy.splider.bean.OriginEntity;
import com.gy.splider.global.Global;
import com.gy.splider.storage.DataStorage;

public class DirectorService extends BaseService implements Runnable{

	/**
	 * 该页面抓取的数据
	 */
	private List<OriginEntity> getData = new ArrayList<OriginEntity>();
	
	@Override
	public void initGetData() {
		String directorname = this.doc.getElementById("content")
				.getElementsByTag("h1").first().text();
		OriginEntity originEntity = new OriginEntity(this.paramId, directorname,
				OriginEntity.DIRECTORTYPE, "/" + this.paramtype + "/"
						+ this.paramId, 0, "");
		DataStorage.AddData(originEntity);
		getRecentMovie();
	}
	
	public void getRecentMovie(){
		String id = "recent_movies";
		String Class= "list-s" ;
		Element elementid = this.doc.getElementById(id);
		Elements movielist = elementid.getElementsByClass(Class).first().getElementsByClass("info");
		for(int i=0;i<movielist.size();i++){
			 Element movie = movielist.get(i).getElementsByTag("a").first();
			 String moviename = movie.text();
			 String link = movie.attr("href");
			 String[] splits = link.split("/");
			 String doubanId = splits[splits.length-1];
			 OriginEntity originEntity = new OriginEntity(doubanId, moviename,
						OriginEntity.MOVIETYPE, link, OriginEntity.DIRECTORTYPE, this.paramId);
			 DataStorage.AddData(originEntity);
			 getData.add(originEntity);
		}
	}

	public DirectorService() {
		super();
	}

	public DirectorService(String movieUrl, String paramtype, String paramId) {
		super();
		this.baseUrl = movieUrl;
		this.method = this.GETMETHOD;
		this.paramId = paramId;
		this.paramtype = paramtype;
		Connection connect = Jsoup.connect(movieUrl + "/" + paramtype + "/"
				+ paramId);
		System.out.println(movieUrl + "/" + paramtype + "/"+ paramId);
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
		initGetData();
	}

	public DirectorService(String movieUrl, int method, String paramtype,
			String paramId) {
		super();
		this.baseUrl = movieUrl;
		this.method = method;
		this.paramId = paramId;
		this.paramtype = paramtype;
		Connection connect = Jsoup.connect(movieUrl + "/" + paramtype + "/"
				+ paramId);
		System.out.println(movieUrl + "/" + paramtype + "/"+ paramId);
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
		initGetData();
	}

	@Override
	public void run() {
		if(DataStorage.getTotalNumber()<=Global.TOTALNUMBER){
			for(int i = 0;i<this.getData.size();i++){
				OriginEntity originEntity = this.getData.get(i);
				switch (originEntity.getType()) {
				case OriginEntity.MOVIETYPE:{
					MovieService movieService = new MovieService(Global.WEBURL, Global.WEBMOVIE,originEntity.getDoubanId());
					Thread thread = new Thread(movieService);
					DataStorage.addThread(thread);
					thread.start();
				}
				break;
				case OriginEntity.DIRECTORTYPE:{
					DirectorService directorService = new DirectorService(Global.WEBURL, Global.WEBDIRECTOR,originEntity.getDoubanId());
					Thread thread = new Thread(directorService);
					DataStorage.addThread(thread);
					thread.start();
				}
				break;
				case OriginEntity.ACTORTYPE:{
					ActorService actorService = new ActorService(Global.WEBURL, Global.WEBACTOR,originEntity.getDoubanId());
					Thread thread = new Thread(actorService);
					DataStorage.addThread(thread);
					thread.start();
				}
				case OriginEntity.SCREENWRITERTYPE:{
					ScreenWriterService screenWriterService = new ScreenWriterService(Global.WEBURL, Global.WEBSCREENWIRTER,originEntity.getDoubanId());
					Thread thread = new Thread(screenWriterService);
					DataStorage.addThread(thread);
					thread.start();
				}
				break;
				default:
					break;
				}
			}
		}
	}
}
