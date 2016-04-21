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
		System.out.println("getDirectors");
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
		Elements second = SpanTags.first().siblingElements().get(1)
				.getElementsByTag("a");
		System.out.println("getScreenWrites");
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
		Elements actors = actorclss.first().getElementsByTag("span").get(2)
				.getElementsByTag("a");
		System.out.println("get "+actors.size()+" actors");
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
		/*Connection connect = Global.getProxyConnection(movieUrl, paramtype,
				paramId);
		System.out.println(movieUrl + "/" + paramtype + "/" + paramId);
		if (this.method == this.GETMETHOD) {
			try {
				this.doc = connect.get();
			} catch (IOException e) {
				new MovieService(movieUrl, paramtype, paramId);
			}
		} else {
			try {
				this.doc = connect.post();
			} catch (IOException e) {
				new MovieService(movieUrl, paramtype, paramId);
			}
		}*/
		if (this.method == this.GETMETHOD) {
			try {
				this.doc = Global.getProxyConnectionDocument(movieUrl, paramtype, paramId);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				this.doc = Global.postProxyConnectionDocument(movieUrl, paramtype, paramId);
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
		/*Connection connect = Global.getProxyConnection(movieUrl, paramtype,
				paramId);
		//System.out.println(movieUrl + "/" + paramtype + "/" + paramId);
		if (this.method == this.GETMETHOD) {
			try {
				this.doc = connect.get();
			} catch (IOException e) {
				System.err.println("代理无法使用");
				new MovieService(movieUrl, paramtype, paramId);
			}
		} else {
			try {
				this.doc = connect.post();
			} catch (IOException e) {
				System.err.println("代理无法使用");
				new MovieService(movieUrl, paramtype, paramId);
			}
		}*/
		if (this.method == this.GETMETHOD) {
			try {
				this.doc = Global.getProxyConnectionDocument(movieUrl, paramtype, paramId);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				this.doc = Global.postProxyConnectionDocument(movieUrl, paramtype, paramId);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		initGetData();
	}

	@Override
	public void run() {
		System.out.println("movieService start"+"data size is "+getData.size());
		for (int i = 0; i < this.getData.size(); i++) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("totalnumer now is "+DataStorage.getTotalNumber());
			if (DataStorage.getTotalNumber() >= Global.TOTALNUMBER) {
				synchronized (Global.OBJECT) {
					Global.OBJECT.notifyAll();
				}
				break;
			}else{
				System.out.println("pass the notify");
			}
			OriginEntity originEntity = this.getData.get(i);
			switch (originEntity.getType()) {
			case OriginEntity.MOVIETYPE: {
				System.out.println("new another movieService");
				//if (!DataStorage.IsContain(originEntity)) {
					MovieService movieService = new MovieService(Global.WEBURL,
							Global.WEBMOVIE, originEntity.getDoubanId());
					Thread thread = new Thread(movieService);
					thread.start();
				//}

			}
				break;
			case OriginEntity.DIRECTORTYPE: {
				//if (!DataStorage.IsContain(originEntity)) {
					System.out.println("new another directorService");
					DirectorService directorService = new DirectorService(
							Global.WEBURL, Global.WEBDIRECTOR,
							originEntity.getDoubanId());
					Thread thread = new Thread(directorService);
					thread.start();
				//}
			}
				break;
			case OriginEntity.ACTORTYPE: {
				//if (!DataStorage.IsContain(originEntity)) {
					System.out.println("new another actorService");
					ActorService actorService = new ActorService(Global.WEBURL,
							Global.WEBACTOR, originEntity.getDoubanId());
					Thread thread = new Thread(actorService);
					thread.start();
				//}
			}
			case OriginEntity.SCREENWRITERTYPE: {
				//if (!DataStorage.IsContain(originEntity)) {
					System.out.println("new another screenWriterService");
					ScreenWriterService screenWriterService = new ScreenWriterService(
							Global.WEBURL, Global.WEBSCREENWIRTER,
							originEntity.getDoubanId());
					Thread thread = new Thread(screenWriterService);
					thread.start();
				//}
			}
				break;
			default:
				break;
			}
		}

	}

}
