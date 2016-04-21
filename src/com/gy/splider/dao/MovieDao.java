package com.gy.splider.dao;

import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gy.splder.util.DBUtil;
import com.gy.splider.bean.OriginEntity;

public class MovieDao {

	public void addMovie(OriginEntity originEntity) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		try {
			connection = DBUtil.openConnection();
			String sql = "insert into m_movie (doubanid,name,link,fromtype,fromdoubanid) values(?,?,?,?,?)";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setString(1, originEntity.getDoubanId());
			prepareStatement.setString(2, originEntity.getName());
			prepareStatement.setString(3, originEntity.getLink());
			prepareStatement.setString(4, originEntity.getFromType() + "");
			prepareStatement.setString(5, originEntity.getFromDoubanId());
			prepareStatement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				prepareStatement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		addMovieRelation(originEntity);
	}

	
	public void addMovieRelation(OriginEntity originEntity){
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String tablename = "";
		switch (originEntity.getFromType()) {
		case OriginEntity.ACTORTYPE:{
			tablename = "am_relation";
		}
		break;	
		case OriginEntity.SCREENWRITERTYPE:{
			tablename = "sm_relation";
		}
		break;
		case OriginEntity.DIRECTORTYPE:{
			tablename = "dm_relation";
		}
		break;
		default:
			break;
		}
		Connection connection  = null ;
		PreparedStatement prepareStatement  =null;
		ResultSet resultSet = null;
		try {
			if(null == originEntity.getFromDoubanId() || "".equals(originEntity.getFromDoubanId()))
				originEntity.setFromDoubanId(0+"");
			connection = DBUtil.openConnection();
			String sql = "select count(*) from "+tablename+" where mid=? and aid = ?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setString(1, originEntity.getDoubanId());
			prepareStatement.setString(2, originEntity.getFromDoubanId());
			resultSet = prepareStatement.executeQuery();
			int number = 0;
			while(resultSet.next()){
				number = resultSet.getInt(1);
			}
			System.out.println("the same number is "+number);
			if(number==0){
				sql = "insert into "+tablename+" (mid,aid) values(?,?)";
				prepareStatement = connection.prepareStatement(sql);
				prepareStatement.setString(1, originEntity.getDoubanId());
				prepareStatement.setString(2, originEntity.getFromDoubanId());
				prepareStatement.execute();
			}
		} catch (Exception e) {
			System.out.println("mid is null or aid is null");
		}finally{
			try {
				prepareStatement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
}
