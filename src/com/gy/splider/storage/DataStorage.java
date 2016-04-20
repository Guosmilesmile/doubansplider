package com.gy.splider.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.gy.splider.bean.OriginEntity;

public class DataStorage {
	
	private static List<Thread> threadlist = new ArrayList<Thread>();
	private static int number = 0;
	private static Map<String,OriginEntity> DirectorStorage = new ConcurrentHashMap<String, OriginEntity>();
	private static Map<String,OriginEntity> MovieStorage = new ConcurrentHashMap<String, OriginEntity>();
	private static Map<String,OriginEntity> ScreenWriteStorage = new ConcurrentHashMap<String, OriginEntity>();
	private static Map<String,OriginEntity> ActorStorage = new ConcurrentHashMap<String, OriginEntity>();
	
	public static List<Thread> getThreadList(){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return threadlist;
	}
	/**
	 * 添加线程
	 * @param thread
	 */
	public static void addThread(Thread thread){
		threadlist.add(thread);
	}
	/**
	 * 添加数据
	 * @param originEntity
	 */
	public  static void AddData(OriginEntity originEntity){
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
	
	
}
