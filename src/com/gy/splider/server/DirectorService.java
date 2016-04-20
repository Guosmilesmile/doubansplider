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
import com.gy.splider.storage.DataStorage;

public class DirectorService extends BaseService{

	/**
	 * 该页面抓取的数据
	 */
	private List<OriginEntity> getData = new ArrayList<OriginEntity>();
	
	@Override
	public void initGetData() {
		String directorname = this.doc.getElementById("content")
				.getElementsByTag("h1").first().text();
		OriginEntity originEntity = new OriginEntity(this.paramId, directorname,
				OriginEntity.ACTORTYPE, "/" + this.paramtype + "/"
						+ this.paramId, 0, "");
		DataStorage.AddData(originEntity);
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
}
