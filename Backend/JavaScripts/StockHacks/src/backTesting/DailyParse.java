package backTesting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.json.JSONObject;

public class DailyParse 
{
    public Map<String, Object> map;
    public Map<String, Object> dates;
    public ArrayList<String> dateKeys;
    public JSONObject obj;
    public boolean init;
    
	public DailyParse(JSONObject obj)
	{
		this.obj = obj;
		this.map = obj.map;
		init = false;
		init = parseInfo();
	}
	
	public boolean parseInfo()
	{
		ArrayList<String> keys = new ArrayList<String>(Arrays.asList(JSONObject.getNames(obj)));
		String s = null;
		for(int i = 0; i < keys.size(); i++)
		{
			if(keys.get(i).equals("Time Series (Daily)"))
			{
				s = map.get(keys.get(i)).toString();
			}
		}
		try
		{
			JSONObject fiveminkeys = null;
			fiveminkeys = new JSONObject(s);
			dates = fiveminkeys.map;
			ArrayList<String> times = new ArrayList<String>(Arrays.asList(JSONObject.getNames(fiveminkeys)));
			Collections.sort(times);
			dateKeys = times;
			return true;
		}
		catch(NullPointerException e)
		{
			System.out.print(s);
			return false;
		}
	}
	
	public double getCloseValue(String key)
	{
		String parsing = dates.get(key).toString();
		int x = parsing.indexOf("close\":\"");
		String firstParse = parsing.substring(x+8);
		x = parsing.indexOf(".");
		int y = firstParse.indexOf("}");
		String secondParse = firstParse.substring(0, y - 1);
		double value = Double.parseDouble(secondParse);
		return value;
	}
	
	public double getOpenValue(String key)
	{
		return 0;
	}
}
