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

public class ScreenWriterService extends BaseService implements Runnable {

	/**
	 * 该页面抓取的数据
	 */
	private List<OriginEntity> getData = new ArrayList<OriginEntity>();

	@Override
	public void initGetData() {
		String directorname = this.doc.getElementById("content")
				.getElementsByTag("h1").first().text();
		OriginEntity originEntity = new OriginEntity(this.paramId,
				directorname, OriginEntity.SCREENWRITERTYPE, "/"
						+ this.paramtype + "/" + this.paramId, 0, "");
		DataStorage.AddData(originEntity);
		getRecentMovie();
	}

	public void getRecentMovie() {
		String id = "recent_movies";
		String Class = "list-s";
		Element elementid = this.doc.getElementById(id);
		Elements movielist = elementid.getElementsByClass(Class).first()
				.getElementsByClass("info");
		for (int i = 0; i < movielist.size(); i++) {
			Element movie = movielist.get(i).getElementsByTag("a").first();
			String moviename = movie.text();
			String link = movie.attr("href");
			String[] splits = link.split("/");
			String doubanId = splits[splits.length - 1];
			OriginEntity originEntity = new OriginEntity(doubanId, moviename,
					OriginEntity.MOVIETYPE, link,
					OriginEntity.SCREENWRITERTYPE, this.paramId);
			DataStorage.AddData(originEntity);
			getData.add(originEntity);
		}
	}

	public ScreenWriterService() {
		super();
	}

	public ScreenWriterService(String movieUrl, String paramtype, String paramId) {
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
				System.err.println("代理无法使用");
				new ScreenWriterService(movieUrl, paramtype,paramId);
			}
		} else {
			try {
				this.doc = connect.post();
			} catch (IOException e) {
				System.err.println("代理无法使用");
				new ScreenWriterService(movieUrl, paramtype,paramId);
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

	public ScreenWriterService(String movieUrl, int method, String paramtype,
			String paramId) {
		super();
		this.baseUrl = movieUrl;
		this.method = method;
		this.paramId = paramId;
		this.paramtype = paramtype;
		/*Connection connect = Global.getProxyConnection(movieUrl, paramtype,
				paramId);
		System.out.println(movieUrl + "/" + paramtype + "/" + paramId);
		if (this.method == this.GETMETHOD) {
			try {
				this.doc = connect.get();
			} catch (IOException e) {
				System.err.println("代理无法使用");
				new ScreenWriterService(movieUrl, paramtype,paramId);
			}
		} else {
			try {
				this.doc = connect.post();
			} catch (IOException e) {
				System.err.println("代理无法使用");
				new ScreenWriterService(movieUrl, paramtype,paramId);
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
		for (int i = 0; i < this.getData.size(); i++) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (DataStorage.getTotalNumber() >= Global.TOTALNUMBER) {
				synchronized (Global.OBJECT) {
					Global.OBJECT.notifyAll();
				}
				break;
			}
			OriginEntity originEntity = this.getData.get(i);
			switch (originEntity.getType()) {
			case OriginEntity.MOVIETYPE: {
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
					ActorService actorService = new ActorService(Global.WEBURL,
							Global.WEBACTOR, originEntity.getDoubanId());
					Thread thread = new Thread(actorService);
					thread.start();
				//}
			}
			case OriginEntity.SCREENWRITERTYPE: {
				//if (!DataStorage.IsContain(originEntity)) {
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
