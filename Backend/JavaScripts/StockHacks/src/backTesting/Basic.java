package backTesting;

import java.util.Calendar;
import java.util.Date;
import automation.Automated;

public class Basic {
	public String stock;
	public String shares;
	public boolean purchased;
	public String retVal;
	public double stopLoss;
	public int initValue;
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
	
	public void waitUnilValid() 
	{
		Calendar c = Calendar.getInstance();
		Date d = new Date();
		c.setTime(d);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		int time = 0;
	}
}
