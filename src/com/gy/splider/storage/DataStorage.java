package com.gy.splider.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.gy.splider.bean.OriginEntity;
import com.gy.splider.dao.ActorDao;
import com.gy.splider.dao.DirectDao;
import com.gy.splider.dao.MovieDao;
import com.gy.splider.dao.ScreenWriterDao;
import com.gy.splider.global.Global;

public class DataStorage {
	
	//private static List<Thread> threadlist = new ArrayList<Thread>();
	private static int number = 0;
	private static Map<String,OriginEntity> DirectorStorage = new ConcurrentHashMap<String, OriginEntity>();
	private static Map<String,OriginEntity> MovieStorage = new ConcurrentHashMap<String, OriginEntity>();
	private static Map<String,OriginEntity> ScreenWriteStorage = new ConcurrentHashMap<String, OriginEntity>();
	private static Map<String,OriginEntity> ActorStorage = new ConcurrentHashMap<String, OriginEntity>();
	
	/*public static List<Thread> getThreadList(){
		
		return threadlist;
	}
	*//**
	 * 添加线程
	 * @param thread
	 *//*
	public static void addThread(Thread thread){
		threadlist.add(thread);
	}*/
	/**
	 * 添加数据
	 * @param originEntity
	 */
	public  static void AddData(OriginEntity originEntity){
		if(getTotalNumber()>=Global.TOTALNUMBER)
			return ;
		switch (originEntity.getType()) {
		case OriginEntity.DIRECTORTYPE:
			if(!DirectorStorage.containsKey(originEntity.getDoubanId())){
				DirectorStorage.put(originEntity.getDoubanId(), originEntity);
				number++;
			}
			break;
		case OriginEntity.MOVIETYPE:
			if(!MovieStorage.containsKey(originEntity.getDoubanId())){
				MovieStorage.put(originEntity.getDoubanId(), originEntity);
				number++;
			}
			break;
		case OriginEntity.SCREENWRITERTYPE:
			if(!ScreenWriteStorage.containsKey(originEntity.getDoubanId())){
				ScreenWriteStorage.put(originEntity.getDoubanId(), originEntity);
				number++;
			}
			break;
		case OriginEntity.ACTORTYPE:
			if(!ActorStorage.containsKey(originEntity.getDoubanId())){
				ActorStorage.put(originEntity.getDoubanId(), originEntity);
				number++;
			}
			break;
		default:
			break;
		}
	}
	/**
	 * 获取数据总数
	 * @return
	 */
	public static synchronized int getTotalNumber(){
		return DirectorStorage.size() + MovieStorage.size() + ScreenWriteStorage.size() + ActorStorage.size();
	}
	public static Map<String,OriginEntity> getDirectorStorage(){
		return DirectorStorage;
	}
	
	public static Map<String,OriginEntity> getMovieStorage(){
		return MovieStorage;
	}
	
	public static Map<String,OriginEntity> getScreenWriteStorage(){
		return ScreenWriteStorage;
	}
	
	public static Map<String,OriginEntity> getActorStorage(){
		return ActorStorage;
	}
	
	public static void PrintAllData(){
		MovieDao movieDao = new MovieDao();
		for(Entry<String, OriginEntity> temp:MovieStorage.entrySet()){
			System.out.println(temp.getKey()+" "+temp.getValue().getName());
			movieDao.addMovie(temp.getValue());
		}
		
		DirectDao directDao = new DirectDao();
		for(Entry<String, OriginEntity> temp:DirectorStorage.entrySet()){
			System.out.println(temp.getKey()+" "+temp.getValue().getName());
			directDao.addMovie(temp.getValue());
		}
		
		ActorDao actorDao = new ActorDao();
		for(Entry<String, OriginEntity> temp:ActorStorage.entrySet()){
			System.out.println(temp.getKey()+" "+temp.getValue().getName());
			actorDao.addMovie(temp.getValue());
		}
		
		ScreenWriterDao screenWriterDao = new ScreenWriterDao();
		for(Entry<String, OriginEntity> temp:ScreenWriteStorage.entrySet()){
			System.out.println(temp.getKey()+" "+temp.getValue().getName());
			screenWriterDao.addMovie(temp.getValue());
		}
	}
	
	
	/**
	 * 判断是否存在
	 * @param originEntity
	 * @return
	 */
	public static boolean IsContain(OriginEntity originEntity){
		switch (originEntity.getType()) {
		case OriginEntity.MOVIETYPE:{
			if(MovieStorage.containsKey(originEntity.getDoubanId()))
				return true;
			return false;
		}
		case OriginEntity.SCREENWRITERTYPE:{
			if(ScreenWriteStorage.containsKey(originEntity.getDoubanId()))
				return true;
			return false;
		}
		case OriginEntity.ACTORTYPE:{
			if(ActorStorage.containsKey(originEntity.getDoubanId()))
				return true;
			return false;
		}
		case OriginEntity.DIRECTORTYPE:{
			if(DirectorStorage.containsKey(originEntity.getDoubanId()))
				return true;
			return false;
		}
		default:
			return false;
		}
	}
}
