package com.gy.splider.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gy.splider.bean.OriginEntity;
import com.gy.splider.global.Global;
import com.gy.splider.storage.DataStorage;

public class MovieService extends BaseService implements Runnable {

	/**
	 * 该页面抓取的数据
	 */
	private List<OriginEntity> getData = new ArrayList<OriginEntity>();
	/**
	 * 获取导演列表
	 * 
	 * @param Id
	 *            htmlid
	 * @param fromType
	 *            由什么类型的点击过来
	 * @param fromDoubanId
	 *            由什么id点击过来
	 */
	public void getDirectors(String Id, int fromType, String fromDoubanId) {
		Element elementById = this.doc.getElementById(Id);
		Elements SpanTags = elementById.getElementsByTag("span");
		Elements first = SpanTags.first().getElementsByTag("a");
		for (int i = 0; i < first.size(); i++) {
			String diectorname = first.get(i).text();
			String link = first.get(i).attr("href");
			OriginEntity originEntity = new OriginEntity(link.substring(1,
					link.length() - 1).split("/")[1], diectorname,
					OriginEntity.DIRECTORTYPE, link, fromType, fromDoubanId);
			DataStorage.AddData(originEntity);
			getData.add(originEntity);
		}
	}

	/**
	 * 获取导演列表
	 * 
	 * @param Id
	 *            htmlid
	 * @param fromType
	 *            由什么类型的点击过来
	 * @param fromDoubanId
	 *            由什么id点击过来
	 */
	public void getDirectors(int fromType, String fromDoubanId) {
		String id = "info";
		getDirectors(id, fromType, fromDoubanId);
	}

	/**
	 * 获取导演列表
	 * 
	 * @param Id
	 *            htmlid
	 * @param fromType
	 *            由什么类型的点击过来
	 * @param fromDoubanId
	 *            由什么id点击过来
	 */
	public void getDirectors() {
		String id = "info";
		getDirectors(id, OriginEntity.MOVIETYPE, this.paramId);
	}

	/**
	 * 获取编剧列表
	 * 
	 * @param Id
	 * @param fromType
	 * @param fromDoubanId
	 */
	public void getScreenWrites(String Id, int fromType, String fromDoubanId) {
		Element elementById = this.doc.getElementById(Id);
		Elements SpanTags = elementById.getElementsByTag("span");
		Elements second = SpanTags.first().siblingElements().get(1).getElementsByTag("a");
		for (int i = 0; i < second.size(); i++) {
			String screenwritename = second.get(i).text();
			String link = second.get(i).attr("href");
			OriginEntity originEntity = new OriginEntity(link.substring(1,
					link.length() - 1).split("/")[1], screenwritename,
					OriginEntity.SCREENWRITERTYPE, link, fromType, fromDoubanId);
			DataStorage.AddData(originEntity);
			getData.add(originEntity);
		}
	}

	/**
	 * 获取编剧列表
	 * 
	 * @param Id
	 * @param fromType
	 * @param fromDoubanId
	 */
	public void getScreenWrites(int fromType, String fromDoubanId) {
		String id = "info";
		getScreenWrites(id, fromType, fromDoubanId);
	}

	/**
	 * 获取编剧列表
	 * 
	 * @param Id
	 * @param fromType
	 * @param fromDoubanId
	 */
	public void getScreenWrites() {
		String id = "info";
		getScreenWrites(id, OriginEntity.MOVIETYPE, this.paramId);
	}
	/**
	 * 获取演员
	 * 
	 * @param Id
	 * @param fromType
	 * @param fromDoubanId
	 */
	public void getActor() {
		String Class = "actor";
		getActor(Class, OriginEntity.MOVIETYPE, this.paramId);
	}

	/**
	 * 获取演员
	 * 
	 * @param Id
	 * @param fromType
	 * @param fromDoubanId
	 */
	public void getActor(int fromType, String fromDoubanId) {
		String Class = "actor";
		getActor(Class, fromType, fromDoubanId);
	}
	/**
	 * 获取演员
	 * 
	 * @param Id
	 * @param fromType
	 * @param fromDoubanId
	 */
	public void getActor(String Class, int fromType, String fromDoubanId) {
		Elements actorclss = this.doc.getElementsByClass(Class);
		Elements actors = actorclss.first().getElementsByTag("span").get(2).getElementsByTag("a");
		for (int i = 0; i < actors.size(); i++) {
			String screenwritename = actors.get(i).text();
			String link = actors.get(i).attr("href");
			OriginEntity originEntity = new OriginEntity(link.substring(1,
					link.length() - 1).split("/")[1], screenwritename,
					OriginEntity.ACTORTYPE, link, fromType, fromDoubanId);
			DataStorage.AddData(originEntity);
			getData.add(originEntity);
		}
	}
	
	/**
	 * 初始化数据
	 */
	@Override
	public void initGetData() {
		String moviename = this.doc.getElementById("content")
				.getElementsByTag("h1").first().getElementsByTag("span")
				.first().text();
		OriginEntity originEntity = new OriginEntity(this.paramId, moviename,
				OriginEntity.MOVIETYPE, "/" + this.paramtype + "/"
						+ this.paramId, 0, "");
		DataStorage.AddData(originEntity);
		getActor();
		getScreenWrites();
		getDirectors();
	}

	public MovieService(String movieUrl, String paramtype, String paramId) {
		super();
		this.baseUrl = movieUrl;
		this.method = this.GETMETHOD;
		this.paramId = paramId;
		this.paramtype = paramtype;
		Connection connect = Jsoup.connect(movieUrl + "/" + paramtype + "/"
				+ paramId).userAgent(
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31");;
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

	public MovieService(String movieUrl, int method, String paramtype,
			String paramId) {
		super();
		this.baseUrl = movieUrl;
		this.method = method;
		this.paramId = paramId;
		this.paramtype = paramtype;
		Connection connect = Jsoup.connect(movieUrl + "/" + paramtype + "/"
				+ paramId).userAgent(
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31");;
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
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
		}else{
			synchronized (Global.OBJECT) {
				Global.OBJECT.notifyAll();
			}
		}
	}

}
