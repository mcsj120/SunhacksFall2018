package backTesting;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONObject;

public class TestCode {

	public static int compareSMATodayOpen(String stock, Date d) throws Exception 
	{
		
		String functionSma = "SMA";
		String intervalSma = "daily";
		String series_typeSma = "close";
		String timeSma50 = "50";
		String timeSma200 = "200";
		
		String symbol = stock;
		
		
		JSONObject sma501 = null;
		sma501 = ApiCall.sma(functionSma, symbol, intervalSma, timeSma50, series_typeSma, Algo1.apiKey);
		SmaParse data50 = new SmaParse(sma501);
			
		JSONObject sma2001 = null;
		sma2001 = ApiCall.sma(functionSma, symbol, intervalSma, timeSma200, series_typeSma, Algo1.apiKey);
		SmaParse data200 = new SmaParse(sma2001);
		int gain;
		try
		{
			gain = data50.todaySMA(data200, symbol);
		}
		catch(Exception e)
		{
			throw new Exception();
		}
		return gain;
	}
	
	
		//ArrayList<Double> gain = data50.startTrendFollowing(data200, symbolSma, "full", apiKey, 2750);
		
		/*JSONObject dailyTest = ApiCall.daily("TIME_SERIES_DAILY", symbolIntra, outputsizeIntra, apiKey);
		DailyParse testing = new DailyParse(dailyTest);
		testing.getCloseValue("2018-11-08");*/

}
