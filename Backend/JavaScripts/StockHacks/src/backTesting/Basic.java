package backTesting;

import java.util.*;

import org.json.JSONObject;

import automation.Automated;

public class Basic {
	public String stock;
	public String shares;
	public boolean purchased;
	public String retVal;
	public double stopLoss;
	public double initValue;
	public double finalValue;
	public Basic(String stock, String shares, double stopLoss)
	{
		this.stock = stock;
		this.shares = shares;
		this.stopLoss = stopLoss;
		purchased = false;
	}
	
	public void runAlgorithmPresent()
	{
		while(true)
		{
			waitUntilValid();
			Date d = new Date();
			int change = 0;
			try {
				change = TestCode.compareSMATodayOpen(stock, d);
			} catch (Exception e) {
				//dates dont match
			}
			boolean ran = false;
			
			while(!ran)
			{
				if(purchased == false)
				{
					if(change == 1)
					{
						try
						{
							retVal = automation.Automated.automatedETrade(stock, shares, "buy");
							try {
								String curString = automation.Automated.automatedMarketWatch(stock);
								System.out.println(curString);
								initValue = Integer.parseInt(curString);
							}
							catch(Exception e){}
							ran = true;
							purchased = true;
						}
						catch(Exception e)
						{
							ran = false;
							try {Thread.sleep(600000);} catch (InterruptedException e1) {e1.printStackTrace();}
						}
					}
				}
				else if(purchased == true)
				{
					if(change == -1)
					{
						try
						{
							retVal = automation.Automated.automatedETrade(stock, shares, "sell");
							try {
								String curString = automation.Automated.automatedMarketWatch(stock);
								System.out.println(curString);
								finalValue = Integer.parseInt(curString);
							}
							catch(Exception e){}
							ran = true;
							System.out.println(finalValue-initValue);
							return;
						}
						catch(Exception e)
						{
							ran = false;
							try {Thread.sleep(600000);} catch (InterruptedException e1) {e1.printStackTrace();}
						}
					}
				}
			}
			boolean stockMarketOpen = true;
			while(stockMarketOpen)
			{
				int currentPrice;
				try {
					String curString = automation.Automated.automatedMarketWatch(stock);
					try {
						currentPrice = Integer.parseInt(curString);
						if(currentPrice < initValue * (1-stopLoss))
						{
							System.out.println(currentPrice);
							while(true)
							{
								try
								{
									retVal = automation.Automated.automatedETrade(stock, shares, "sell");
									ran = true;
									return;
								}
								catch(Exception e)
								{
									ran = false;
									try {Thread.sleep(600000);} catch (InterruptedException e1) {e1.printStackTrace();}
								}
							}
						}
					}
					catch(Exception e) {}
				} catch (InterruptedException e) {}
				stockMarketOpen = validTime();
			}
		
		}
	}	
		
		/*Calendar c = Calendar.getInstance();
		Date d = new Date();
		c.setTime(d);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		while(dayOfWeek == 1 || dayOfWeek == 7)
		{
			try {Thread.sleep(21600000);} catch (InterruptedException e) {e.printStackTrace();}
			dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		}*/
		
		/*
		 * Time is good
		 */	
	@SuppressWarnings("deprecation")
	public boolean validTime()
	{
		
		Calendar c = Calendar.getInstance();
		Date d = new Date();
		c.setTime(d);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		if(dayOfWeek == 1 || dayOfWeek == 7)
		{
			return false;
		}
		if((d.getHours() < 7 || d.getHours() > 1) || ((d.getHours() == 7 && d.getMinutes() > 30)))
		{
			return false;
		}
		return true;
	}
	
	public void waitUntilValid() 
	{
		Calendar c = Calendar.getInstance();
		Date d = new Date();
		c.setTime(d);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		int time = 0;
	}
	
	public void runBackTest()
	{
		String functionSma = "SMA";
		String intervalSma = "daily";
		String series_typeSma = "close";
		String timeSma50 = "50";
		String timeSma200 = "200";
		
		JSONObject sma501 = null;
		sma501 = ApiCall.sma(functionSma, stock, intervalSma, timeSma50, series_typeSma, Algo1.apiKey);
		SmaParse data50 = new SmaParse(sma501);
		
		JSONObject sma2001 = null;
		sma2001 = ApiCall.sma(functionSma, stock, intervalSma, timeSma200, series_typeSma, Algo1.apiKey);
		SmaParse data200 = new SmaParse(sma2001);
		/*
		ArrayList<Double> gain = data200.startTrendFollowing(data50, stock, "full", Algo1.apiKey, 2000);
		System.out.println("Start:" + gain.get(0) + '\n' + "Final:" + gain.get(1));
		System.out.println(gain.get(1) - gain.get(0));
		*/
		double gain = data200.startCompleteTrendFollowing(data50, stock, "full", Algo1.apiKey, 0, 1000);
		System.out.println(gain - 1000);
		//System.out.println((gain-1000)/(20*1000));
	}
}
