package edu.kit.common.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class ExchangeRate 
{
	
	//该API所支持的所有货币
	public static final String ApiSurpporttedCurrency[] = {"EUR","GBP","AUD","BRL","CAD","CHF","CNY","DKK","HKD","INR","JPY","KRW","LKR","MYR"
								,"NZD","SGD","TWD","ZAR","THB","SEK","NOK","MXN","BGN","CZK","HUF","LTL","LVL","PLN","RON","ISK","HRK","RUB",
								"TRY","PHP","COP","ARS","CLP","SVC","TND","PYG","MAD","JMD","SAR","QAR","HNL","SYP","KWD","BHD","EGP","OMR","NGN","PAB","PEN","UYU"};
	//AccountManagement所用到的支持货币图片的所有货币
	public static final String [] CURRENCY={"AUD","GBP","BRL","CAD","CHF","CNY","DKK","EUR","HKD","JPY","KRW","LKR","MYR","NOK","NZD","PHP","SEK","SGD","THB","TWD","USD"};
	
	/**
	 * 查询当前时刻汇率
	 * @param fromCurrency 本位币
	 * @param toCurrency 原币
	 * @return 汇率值
	 */
	public static double exchage(String fromCurrency,String toCurrency) 
	{
		String urlStr = "http://api.aoikujira.com/kawase/get.php?format=csv&code="+fromCurrency+"&to="+toCurrency;
		URL url = null;
	    BufferedReader in = null;
	    String str[] = null ;
	    try
	    {
	    	url = new URL(urlStr);
	    	in = new BufferedReader( new InputStreamReader(url.openStream(),"UTF-8") );
	    	for(int i=0;i<6;i++)
				if(in.readLine()!=null)continue;
	    	str = in.readLine().split(",");
	    } 
	    catch (Exception e) 
	    {
	    	e.printStackTrace();
	    } 
	    return Double.valueOf(str[1]);
	}
	
	public static final String historyURL="http://fx.sauder.ubc.ca/cgi/fxdata";
	
	/**
	 * 查询指定时间的汇率历史记录，最大支持4年的时间段
	 * @param from 本位币
	 * @param to 原币
	 * @param type 查询类型 空：指定时间联系查询 1：上一个交易日 7：过去一周 28：过去四周
	 * @param frequency 间隔频率 "daily","weekly","monthly"
	 * @param fd 起始日
	 * @param fm 起始月
	 * @param fy 起始年
	 * @param ld 结束日
	 * @param lm 结束月
	 * @param ly 结束年
	 * @return 以TreeMap<日期,汇率值>的键值对形式返回汇率历史记录
	 */
	public static Map<Date, Double> history(String from,String to,String type,String frequency,int fd,int fm,int fy,int ld,int lm,int ly)
	{
		String sCurrentLine="";
		String sTotalString="";
		String request="b="+from+
				"&c="+to+
				"&rd="+type+
				"&fd="+fd+
				"&fm="+fm+
				"&fy="+fy+
				"&ld="+ld+
				"&lm="+lm+
				"&ly="+ly+
				"&y="+frequency+
				"&q=volume&f=csv&o=C.T";
		Map<Date, Double> data=new TreeMap<Date, Double>();
		try
		{
			URL url = new URL(historyURL);
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "8859_1");
			out.write(request); 
			out.flush();
			out.close();
			InputStream l_urlStream;
			l_urlStream = connection.getInputStream();
			BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream));
			while ((sCurrentLine = l_reader.readLine()) != null)
				sTotalString += sCurrentLine + "\r\n";
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return data;
		}
		String [] values=sTotalString.split("\n");
		String [] dates=values[1].split(",");
		String [] rates=values[2].split(",");
		for(int i=1;i<dates.length;i++)
			data.put(DateTool.transferDate(dates[i].split("\"")[1], "yyyy/MM/dd"), Double.parseDouble(rates[i]));
		return data;
	}
	
	public static void main(String[] args) {
		System.out.println(history("CNY", "JPY", "", "daily", 1, 10, 2014, 20, 11, 2014).isEmpty());
	}
}
