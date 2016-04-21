import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.gy.splder.util.DBUtil;
import com.gy.splider.bean.ProxyIpEntity;


public class IpTest {
	public static void main(String[] args) {
		List<ProxyIpEntity> list = new ArrayList<ProxyIpEntity>();
		try {
			for(int j=1;j<=10;j++){
				Document document = Jsoup.connect("http://ip84.com/gn/"+j).get();
				Elements elementsByClass = document.getElementsByClass("list").first().getElementsByTag("tr");
				for(int i=1;i<elementsByClass.size();i++){
					Elements elementsByTag = elementsByClass.get(i).getElementsByTag("td");
					list.add(new ProxyIpEntity(elementsByTag.get(0).text(),Integer.parseInt(elementsByTag.get(1).text()) ));
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		for(int i=0;i<list.size();i++){
			ProxyIpEntity proxyIpEntity = list.get(i);
			try {
				Jsoup.connect("https://movie.douban.com/subject/25757186/").proxy(proxyIpEntity.getIp(), proxyIpEntity.getPort()).timeout(10000).get();
				System.out.println(proxyIpEntity.getIp()+":"+proxyIpEntity.getPort());
				try {
					Connection connection = DBUtil.openConnection();
					String sql = "insert into iptable (ip,port) values(?,?)";
					PreparedStatement prepareStatement = connection.prepareStatement(sql);
					prepareStatement.setString(1, proxyIpEntity.getIp());
					prepareStatement.setString(2, proxyIpEntity.getPort()+"");
					prepareStatement.execute();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				
			}
		}
		
	}
}
