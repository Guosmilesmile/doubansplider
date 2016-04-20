package com.gy.splider.bean;

/**
 * 基类
 * @author Gy
 *
 */
public class OriginEntity {
	
	public final static int MOVIETYPE = 0;//电影
	public final static int DIRECTORTYPE = 1;//导演
	public final static int SCREENWRITERTYPE = 2;//编剧
	public final static int ACTORTYPE = 3;//演员
	
	/**
	 * id
	 */
	private int  Id;
	
	/**
	 * 豆瓣对应的id
	 */
	private String DoubanId;
	
	/**
	 * 名字
	 */
	private String Name;
	/**
	 * 类别
	 */
	private int Type = this.MOVIETYPE;
	
	/**
	 * 连接
	 */
	private String Link;
	
	/**
	 * 从什么类型链接过来的
	 */
	private int FromType = this.MOVIETYPE;
	
	/**
	 * 从哪个id过来的
	 */
	private String FromDoubanId ;

	public OriginEntity(int id, String doubanId, String name, int type,
			String link, int fromType, String fromDoubanId) {
		super();
		Id = id;
		DoubanId = doubanId;
		Name = name;
		Type = type;
		Link = link;
		FromType = fromType;
		FromDoubanId = fromDoubanId;
	}

	public OriginEntity(String doubanId, String name, int type, String link,
			int fromType, String fromDoubanId) {
		super();
		DoubanId = doubanId;
		Name = name;
		Type = type;
		Link = link;
		FromType = fromType;
		FromDoubanId = fromDoubanId;
	}

	public OriginEntity() {
		super();
	}


	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getDoubanId() {
		return DoubanId;
	}

	public void setDoubanId(String doubanId) {
		DoubanId = doubanId;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getType() {
		return Type;
	}

	public void setType(int type) {
		Type = type;
	}

	public String getLink() {
		return Link;
	}

	public void setLink(String link) {
		Link = link;
	}

	public int getFromType() {
		return FromType;
	}

	public void setFromType(int fromType) {
		FromType = fromType;
	}

	public String getFromDoubanId() {
		return FromDoubanId;
	}

	public void setFromDoubanId(String fromDoubanId) {
		FromDoubanId = fromDoubanId;
	}
	
	
	
}
