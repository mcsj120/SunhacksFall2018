package backTesting;

import org.json.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Map;
import java.io.*;

public class Algo1 {

	public static void main(String[] args) 
	{
		JSONObject obj = null;
		String functionIntra = "TIME_SERIES_INTRADAY";
		String symbolIntra = "MSFT";
		String intervalIntra = "5min";
		String outputsizeIntra = "full";
		String apiKey = "HA1B7557DRPRIY78";
		
		obj = ApiCall.intraday(functionIntra, symbolIntra, intervalIntra, outputsizeIntra, apiKey);
		//modify test to accept multiple intervals
		IntradayParse test = new IntradayParse(obj);
		
		JSONObject sma50 = null;
		String functionSma = "SMA";
		String symbolSma = "MSFT";
		String intervalSma = "daily";
		String timeSma = "50";
		String series_typeSma = "close";
		sma50 = ApiCall.sma(functionSma, symbolSma, intervalSma, timeSma, series_typeSma, apiKey);
		SmaParse data = new SmaParse(sma50);
		
	}

}
