package backTesting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.json.JSONObject;

public class SmaParse {

	 public Map<String, Object> map;
	    public Map<String, Object> dates;
	    public ArrayList<String> dateKeys;
	    public JSONObject obj;
	    
		public SmaParse(JSONObject obj)
		{
			this.obj = obj;
			this.map = obj.map;
			boolean x = false;
			while(!x)
			{
				x = parseInfo();
				try 
				{
					if(x == false)
					{
						Thread.sleep(1000);
					}
				} 
				catch (InterruptedException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
		}
		
		public boolean parseInfo()
		{
			ArrayList<String> keys = new ArrayList<String>(Arrays.asList(JSONObject.getNames(obj)));
			String s = null;
			for(int i = 0; i < keys.size(); i++)
			{
				if(keys.get(i).equals("Technical Analysis: SMA"))
				{
					s = map.get(keys.get(i)).toString();
				}
			}
			JSONObject fiveminkeys = null;
			try
			{
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
		
		public int compareTo(SmaParse other, String date)
		{
			String smaObj = this.dates.get(date).toString();
			double value = Double.parseDouble(smaObj.substring(8, smaObj.length()-2));
			
			String smaObjOther = other.dates.get(date).toString();
			double valueOther = Double.parseDouble(smaObjOther.substring(8, smaObjOther.length()-2));
			
			if(value > valueOther)
			{
				return 1;
			}
			else if(value == valueOther)
			{
				return 0;
			}
			else
			{
				return -1;
			}
		}
		
		public ArrayList<Double> startTrendFollowing(SmaParse other, String symbol, String outputsize, String apiKey)
		{
			ArrayList<Double> values = new ArrayList<Double>();
			boolean solved = false;
			JSONObject data = ApiCall.daily("TIME_SERIES_DAILY", symbol, outputsize, apiKey);
			DailyParse first = new DailyParse(data);

			for(int i = 2625; i < dateKeys.size()-1 && i < other.dateKeys.size()-1 && solved == false; i++)
			{
				String date = dateKeys.get(i);
				int x = compareTo(other, date);
				if(values.size() == 0)
				{
					if(x == 1)
					{
						double closing = first.getCloseValue(dateKeys.get(i));
						values.add(0, closing);
						System.out.println(closing);
					}
				}
				else if(values.size() == 1)
				{
					if(x == -1)
					{
						double closing2 = first.getCloseValue(dateKeys.get(i));
						
						values.add(0, closing2);
						solved = true;
						System.out.println(closing2);
					}
				}
			}
			double last = first.getCloseValue(dateKeys.get(dateKeys.size()-1));
			values.add(last);			
			return values;
		}
		
		public ArrayList<Double> startTrendFollowing(SmaParse other, String symbol, String outputsize, String apiKey, int y)
		{
			ArrayList<Double> values = new ArrayList<Double>();
			boolean solved = false;
			JSONObject data = ApiCall.daily("TIME_SERIES_DAILY", symbol, outputsize, apiKey);
			DailyParse first;
			do {
				first = new DailyParse(data);
			}while(first.init ==false);
			for(int i = y; i < dateKeys.size()-1 && i < other.dateKeys.size()-1 && solved == false; i++)
			{
				String date = dateKeys.get(i);
				int x = compareTo(other, date);
				if(values.size() == 0)
				{
					if(x == 1)
					{
						double closing = first.getCloseValue(dateKeys.get(i));
						values.add(0, closing);
					}
				}
				else if(values.size() == 1)
				{
					if(x == -1)
					{
						double closing2 = first.getCloseValue(dateKeys.get(i));
						values.add(0, closing2);
						solved = true;
					}
				}
			}
			double last = first.getCloseValue(dateKeys.get(dateKeys.size()-1));
			if(values.size() == 1)
			{
				values.add(last);	
			}
			return values;
		}
		

}
