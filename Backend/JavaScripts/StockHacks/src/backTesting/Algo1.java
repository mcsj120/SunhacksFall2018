package backTesting;

import org.json.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Map;
import java.io.*;

public class Algo1 {

	public static void main(String[] args) 
	{
		
		String apiKey = "HA1B7557DRPRIY78";
		
		String functionSma = "SMA";
		String intervalSma = "daily";
		String series_typeSma = "close";
		String timeSma50 = "50";
		String timeSma200 = "200";
		
		ArrayList<String> symbols = new ArrayList<String>();
		
		String symbolSma1 = "AMZN";
		String symbolSma2 = "BBBY";
		String symbolSma3 = "AAPL";
		String symbolSma4 = "GOOGL";
		String symbolSma5 = "PYPL";
		symbols.add(symbolSma1);
		symbols.add(symbolSma2);
		symbols.add(symbolSma3);
		symbols.add(symbolSma4);
		symbols.add(symbolSma5);
		for(int i=0;i<symbols.size();i++)
		{
			JSONObject sma501 = null;
			sma501 = ApiCall.sma(functionSma, symbols.get(i), intervalSma, timeSma50, series_typeSma, apiKey);
			SmaParse data50 = new SmaParse(sma501);
			
			JSONObject sma2001 = null;
			sma2001 = ApiCall.sma(functionSma, symbols.get(i), intervalSma, timeSma200, series_typeSma, apiKey);
			SmaParse data200 = new SmaParse(sma2001);
			
			ArrayList<Double> gain = data50.startTrendFollowing(data200, symbolSma1, "full", apiKey, 2750);
			
			System.out.println(symbols.get(i) + '\n' + (gain.get(0) - gain.get(1)));
		}
		




		
		
		
		
		/*JSONObject dailyTest = ApiCall.daily("TIME_SERIES_DAILY", symbolIntra, outputsizeIntra, apiKey);
		DailyParse testing = new DailyParse(dailyTest);
		testing.getCloseValue("2018-11-08");*/
		
	}

}

/*JSONObject obj = null;
String functionIntra = "TIME_SERIES_INTRADAY";
String symbolIntra = "AMZN";
String intervalIntra = "5min";
String outputsizeIntra = "full";


obj = ApiCall.intraday(functionIntra, symbolIntra, intervalIntra, outputsizeIntra, apiKey);
//modify test to accept multiple intervals
IntradayParse test = new IntradayParse(obj);*/
