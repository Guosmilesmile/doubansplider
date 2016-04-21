package com.gy.splider.storage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.gy.splder.util.DBUtil;
import com.gy.splider.bean.ProxyIpEntity;
import com.sun.xml.internal.ws.wsdl.writer.document.Port;

public class IpStorage {
	private List<ProxyIpEntity> list = new ArrayList<ProxyIpEntity>();

	public IpStorage() {
		super();
		//list = getProxyIpList();
		list.add(new ProxyIpEntity("183.131.104.250", 80));
		//list.add(new ProxyIpEntity("159.226.224.180", 3128));
	}

	public ProxyIpEntity getProxyIpEntity() {
		int number = (int) (Math.random() * (list.size() - 1 - 0 + 1));
		return list.get(number);
	}

	/**
	 * 获取代理ip
	 * 
	 * @return
	 */
	public static List<ProxyIpEntity> getProxyIpList() {
		PreparedStatement prepareStatement = null;
		java.sql.Connection connection = null;
		ResultSet resultSet = null;
		try {
			List<ProxyIpEntity> list = new ArrayList<ProxyIpEntity>();
			connection = DBUtil.openConnection();
			String sql = "select * from iptable";
			prepareStatement = connection.prepareStatement(sql);
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				String ip = resultSet.getString("ip");
				String port = resultSet.getString("port");
				ProxyIpEntity proxyIpEntity = new ProxyIpEntity(ip,
						Integer.parseInt(port));
				list.add(proxyIpEntity);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
				prepareStatement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return null;
	}

	public static void deleteProxyIp(ProxyIpEntity proxyIpEntity) {
		PreparedStatement prepareStatement = null;
		java.sql.Connection connection = null;
		try {
			connection = DBUtil.openConnection();
			String sql = "delete from iptable where ip = ? and port = ?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setString(1, proxyIpEntity.getIp());
			prepareStatement.setString(2, proxyIpEntity.getPort() + "");
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
	}
}
