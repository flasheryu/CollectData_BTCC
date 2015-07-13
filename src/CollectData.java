import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CollectData {
	static long lastRecordedTime=System.currentTimeMillis()/1000; //every 43200 seconds, load trades
	public static void main(String[] args) throws Exception {
		while(true){
			SimpleDateFormat datef = new SimpleDateFormat("yyMMdd");//设置日期格式
			SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
			String sysTime=df.format(new Date());
			String date=datef.format(new Date());
			System.out.println(sysTime);
			System.out.println(date);
			Thread.sleep(30000);

			long currentTime=System.currentTimeMillis()/1000;
			if(currentTime-lastRecordedTime>=43200){
			    String tradeUrl = "https://data.btcchina.com/data/trades";
			    String tradeString = loadJson(tradeUrl);
			    FileOperation.contentToTxt("trade.txt", tradeString);
			    System.out.println("trade.txt written");
			    lastRecordedTime=currentTime;
			}
			String orderUrl = "https://data.btcchina.com/data/orderbook";
		    String orderString = loadJson(orderUrl);
		    FileOperation.contentToTxt("orderbook.txt", orderString);
		    System.out.println("orderbook.txt written");
		}
	}
	public static String loadJson (String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL urlObject = new URL(url);
            URLConnection uc = urlObject.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine+'\n');
            }
            in.close();
        } catch (MalformedURLException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        }
        return json.toString();
    }
	

}
