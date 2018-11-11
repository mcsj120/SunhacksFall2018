package backTesting;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;

public class ApiCall 
{
	/*
	 * TIME_SERIES_INTRADAY
	 */
	//public String function;
	/*
	 * MSFT
	 */
	//public String symbol;
	/*
	 * 5min
	 */
	//public String interval;
	/*
	 * full
	 * compact
	 */
	//public String outputsize;
	/*
	 * HA1B7557DRPRIY78
	 */
	//public String apiKey;
	public static final String intro = "https://www.alphavantage.co/query?";
	
	/*public ApiCall(String function, String apiKey)
	{
		this.function = function;
		this.apiKey = apiKey;
	}*/
	
	public static JSONObject intraday(String function, String symbol, String interval, String outputsize, String apiKey )
	{

		URL url = null;
		try 
		{
			url = new URL(intro + "function="+function+"&symbol="+symbol+"&interval="+interval + 
						"&outputsize=" +outputsize + "&apikey=" + apiKey);
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		}
		JSONObject answer = getObject(url);
		return answer;
	}
	
	public static JSONObject sma(String function, String symbol, String interval, String timePeriod, String seriesType, String apiKey )
	{
		
		URL url = null;
		JSONObject answer = null;
		try 
		{
			url = new URL(intro + "function="+function+"&symbol="+symbol+"&interval="+interval + 
						"&time_period=" +timePeriod + "&series_type=" + seriesType + "&apikey=" + apiKey);
			//System.out.println(url.toString());
			answer = getObject(url);
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		}

		return answer;
	}
	
	public static JSONObject daily(String function, String symbol, String outputsize, String apiKey )
	{

		URL url = null;
		try 
		{
			url = new URL(intro + "function="+function+"&symbol="+symbol+ 
						"&outputsize=" +outputsize + "&apikey=" + apiKey);
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		}
		JSONObject answer = getObject(url);
		return answer;
	}
	
	public static JSONObject getObject(URL url)
	{
		JSONObject obj = null;
		try
		{
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			String content = new String();
			while ((inputLine = in.readLine()) != null) {
			    content+=inputLine;
			}
			in.close();
			con.disconnect();
			obj=new JSONObject(content);
		}
		catch(Exception e)
		{
			
		}
		return obj;
	}
}
