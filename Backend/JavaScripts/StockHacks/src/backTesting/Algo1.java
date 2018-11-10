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
		try
		{
			URL url = new URL("https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=MSFT&interval=5min&outputsize=full&apikey=HA1B7557DRPRIY78");
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
		IntradayParse test = new IntradayParse(obj);
	}

}
