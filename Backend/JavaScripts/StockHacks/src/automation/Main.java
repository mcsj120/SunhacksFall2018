package automation;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		System.out.println(Automated.automatedETrade("AAPL", "10", "buy"));
		System.out.println(Automated.automatedETrade("AMZN", "3", "sell"));
		System.out.println(Automated.automatedMarketWatch("AAPL"));
		System.out.println(Automated.automatedMarketWatch("AMZN"));
	}

}
