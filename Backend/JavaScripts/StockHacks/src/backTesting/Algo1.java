package backTesting;

import org.json.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Map;
import java.io.*;
import java.util.*;

public class Algo1 {

	public static String apiKey = "HA1B7557DRPRIY78"; 
	public static void main(String[] args) 
	{
		/*
		 * Get input from webpage
		 */
		Basic run = new Basic("MSFT", "1", 0.05);
		run.runAlgorithmPresent();
		
		
		
		
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
