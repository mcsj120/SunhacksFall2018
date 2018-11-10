package backTesting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.json.JSONObject;
import org.openqa.selenium.Keys;

public class IntradayParse {

    public Map<String, Object> map;
    public Map<String, Object> dates;
    public ArrayList<String> dateKeys;
    public JSONObject obj;
    
	public IntradayParse(JSONObject obj)
	{
		this.obj = obj;
		this.map = obj.map;
		parseInfo();
	}
	
	public void parseInfo()
	{
		ArrayList<String> keys = new ArrayList<String>(Arrays.asList(JSONObject.getNames(obj)));
		String s = null;
		for(int i = 0; i < keys.size(); i++)
		{
			if(keys.get(i).equals("Time Series (5min)"))
			{
				s = map.get(keys.get(i)).toString();
			}
		}
		JSONObject fiveminkeys = new JSONObject(s);
		dates = fiveminkeys.map;
		ArrayList<String> times = new ArrayList<String>(Arrays.asList(JSONObject.getNames(fiveminkeys)));
		Collections.sort(times);
		dateKeys = times;
		for(int i = 0; i<dateKeys.size();i++)
		{
			System.out.println(dateKeys.get(i));
		}
	}

}
