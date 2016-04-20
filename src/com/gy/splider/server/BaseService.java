package com.gy.splider.server;

import org.jsoup.nodes.Document;

public abstract class BaseService {
	
	protected String baseUrl;

	protected String paramtype;

	protected String paramId;

	protected Document doc;
	/**
	 * 传参方式
	 */
	protected int method;

	/**
	 * post
	 */
	public final static int POSTMETHOD = 1;

	/**
	 * get
	 */
	public final static int GETMETHOD = 0;
	
	/**
	 * 初始化数据
	 */
	public abstract void initGetData();
}
