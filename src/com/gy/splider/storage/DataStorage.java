package com.gy.splider.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.gy.splider.bean.OriginEntity;

public class DataStorage {
	
	private static int number = 0;
	private static Map<String,OriginEntity> DirectorStorage = new ConcurrentHashMap<String, OriginEntity>();
	private static Map<String,OriginEntity> MovieStorage = new ConcurrentHashMap<String, OriginEntity>();
	private static Map<String,OriginEntity> ScreenWriteStorage = new ConcurrentHashMap<String, OriginEntity>();
	private static Map<String,OriginEntity> ActorStorage = new ConcurrentHashMap<String, OriginEntity>();
	
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
