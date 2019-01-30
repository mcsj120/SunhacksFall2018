package backTesting;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
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
			
			if(value < valueOther)
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
			for(int i = 0; i < dateKeys.size()-1 && i < other.dateKeys.size()-1 && solved == false; i++)
			{
				
				String date = dateKeys.get(i);
				int x = compareTo(other, date);
				if(values.size() == 0)
				{
					if(x == 1)
					{
						double closing = first.getCloseValue(dateKeys.get(i));
						values.add(0, closing);
						System.out.println(dateKeys.get(i));
					}
				}
				else if(values.size() == 1)
				{
					if(x == -1)
					{
						double closing2 = first.getCloseValue(dateKeys.get(i));
						
						values.add(0, closing2);
						solved = true;
						System.out.println(dateKeys.get(i));
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
						System.out.println(date);
					}
				}
				else if(values.size() == 1)
				{
					if(x == -1)
					{
						double closing2 = first.getCloseValue(dateKeys.get(i));
						values.add(1, closing2);
						System.out.println(date);
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
		
		public int todaySMA(SmaParse other, String symbol) throws Exception
		{
			/*JSONObject data = ApiCall.daily("TIME_SERIES_DAILY", symbol, "full", "HA1B7557DRPRIY78");
			DailyParse first;
			do {
				first = new DailyParse(data);
				if(first.dates == null)
				{
					try {System.out.println("overload");Thread.sleep(120000);} catch (InterruptedException e) {e.printStackTrace();}
				}
			}while(first.init ==false);*/
			
			String date = dateKeys.get(dateKeys.size()-1);
			Date d = new Date();
			SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
			String s = form.format(d);
			if(s.equals(date))
			{
				return compareTo(other, date);
			}
			throw new Exception();
		}
		
		public double startCompleteTrendFollowing(SmaParse other, String symbol, String outputsize, String apiKey, int offset, int startMoney)
		{
			//System.out.println(dateKeys.get(offset));
			ArrayList<Double> values = new ArrayList<Double>();
			//double total = 0;
			JSONObject data = ApiCall.daily("TIME_SERIES_DAILY", symbol, outputsize, apiKey);
			DailyParse first = new DailyParse(data);
			//double startMoney = 100;
			int stocksOwned = 0;
			int i;
			for(i = offset; i < dateKeys.size()-1 && i < other.dateKeys.size()-1; i++)
			{
				
				String date = dateKeys.get(i);
				int x = compareTo(other, date);
				if(values.size() == 0)
				{
					if(x == 1)
					{
						double closing = first.getCloseValue(dateKeys.get(i));
						boolean fin = false;
						while(!fin)
						{
							if(startMoney - closing > 0)
							{
								stocksOwned += 1;
								startMoney -= closing;
							}
							else
							{
								fin = true;
							}
						}
						values.add(0, closing);
					}
				}
				else if(values.size() == 1)
				{
					if(x == -1)
					{
						double closing2 = first.getCloseValue(dateKeys.get(i));
						startMoney += (closing2 * stocksOwned);
						stocksOwned = 0;
						values.add(0, closing2);
						//total += (values.get(0) - values.get(1));
						values.clear();
					}
				}
			}
			if(stocksOwned > 0)
			{
				startMoney += (first.getCloseValue(dateKeys.get(i)) * stocksOwned);
			}
			//System.out.println(dateKeys.get(i));
			return startMoney;
		}

}
