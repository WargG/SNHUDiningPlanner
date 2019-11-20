import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Prediction {
    private List<ArrayList<String>> info = new ArrayList<ArrayList<String>>();
    public static Prediction predict = new Prediction();
    
	
	private List<String> months = new ArrayList<String>();
	private int startMonth;
	private int endMonth;
	private int userID;
	private int breakfastStart, breakfastEnd, lunchStart, lunchEnd, dinnerStart, dinnerEnd;
	private double[] mealTypeAverage = {0, 0, 0, 0};
	private double[] monthAverage;
	private double spentPerDay;
	private int daysOffCampus;
	private double estAmountLeft;
	private int daysLeft;
	private String startDate;
	private String endDate;
	//private ReadWriteSQL test = new ReadWriteSQL();
	//private SQLConnect connection;
	
	//spending by month (avg)
	//spending by meal (avg)
	//funds remaining
	//est amount left
	//
	public Prediction(int userID)
	{
		this.userID = userID;
		populateMonths();
	}
	
	public Prediction()
	{
		populateMonths();
	}
	
	private void populateMonths()
	{
		months.add("January");
		months.add("February");
		months.add("March");
		months.add("April");
		months.add("May");
		months.add("June");
		months.add("July");
		months.add("August");
		months.add("September");
		months.add("October");
		months.add("November");
		months.add("December");
	}
	
	public void getData()
	{
		
	}
	
	public void calcDaysLeft()
	{
			
		//Parsing the date
		LocalDate dateBefore = LocalDate.parse(startDate);
		LocalDate dateAfter = LocalDate.parse(endDate);
			
		//calculating number of days in between
		long numDays = ChronoUnit.DAYS.between(dateBefore, dateAfter);
		
		daysLeft = (int)numDays - daysOffCampus;
	}
	
	private int parseMonth(String date)
	{
		String parsedDate[] = date.split(" ", 1);
		return months.indexOf(parsedDate[0]);
	}
	
	private int parseDay(String date)
	{
		String parsedDate[] = date.split(" ", 3);
		String parsedDate2[] = parsedDate[1].split(",", 2);
		
		return Integer.parseInt(parsedDate2[0]);
	}
	
	private int parseTime(String time)
	{
		return convertTime(time);
	}
	
	private double parseAmount(String amount)
	{
		String parsedAmount = amount.substring(1);
		double tester = Double.parseDouble(parsedAmount);
		return tester;
	}
	
	private int convertTime(String time)
	{
		int newTime = 0;
		time = time.replaceAll("([:])", "");
		newTime = Integer.parseInt(time.substring(0, time.length() - 2));
		if(time.substring(time.length() - 2).compareTo("PM") == 0 && (newTime < 1200))
		{
			newTime+=1200;
		}
		return newTime;
	}
	
	public void predictionSettings(String startDate, String endDate, int daysOffCampus, int breakfastStart, int breakfastEnd, int lunchStart, int lunchEnd, int dinnerStart, int dinnerEnd)
	{		
		this.startMonth = parseMonth(startDate);
		this.endMonth = parseMonth(endDate);
		this.startDate = startDate;
		this.endDate = endDate;
		this.breakfastStart = breakfastStart;
		this.breakfastEnd = breakfastEnd;
		this.lunchStart = lunchStart;
		this.lunchEnd = lunchEnd;
		this.dinnerStart = dinnerStart;
		this.dinnerEnd = dinnerEnd;
		this.daysOffCampus = daysOffCampus;
	}
	
	public void calcMealTypeAverage()
	{
		int numBreakfast = 0;
		int numLunch = 0;
		int numDinner = 0;
		int numSnack = 0;
		int transactionTime = 0;
		double breakfastAmount = 0;
		double lunchAmount = 0;
		double dinnerAmount = 0;
		double snackAmount = 0;
		
		//ReadWriteSQL mealAvg = new ReadWriteSQL();
		//mealAvg.createConnection();
		//test.createConnection();
		
		List<String> times = new ArrayList<String>();
		List<String> amounts = new ArrayList<String>();
		for(int j = 0; j < SNHULogOn.dataScrape.info.get(0).size(); j++)
        {
        		times.add(SNHULogOn.dataScrape.info.get(j).get(1));
        		amounts.add(SNHULogOn.dataScrape.info.get(j).get(2));
        }

		for(int i = 0; i < amounts.size(); i++)
		{
			transactionTime = parseTime(times.get(i));
			if(transactionTime >= breakfastStart && transactionTime <= breakfastEnd)
		 	{
		 		numBreakfast++;
		 		breakfastAmount += parseAmount(amounts.get(i));
		 	}
		 	else if(transactionTime >= lunchStart && transactionTime <= lunchEnd)
		 	{
		 		numLunch++;
		 		lunchAmount += parseAmount(amounts.get(i));
		 	}
		 	else if(transactionTime >= dinnerStart && transactionTime <= dinnerEnd)
		 	{
		 		numDinner++;
		 		dinnerAmount += parseAmount(amounts.get(i));
		 	}
		 	else
		 	{
		 		numSnack++;
		 		snackAmount += parseAmount(amounts.get(i));
		 	}
		}
		    
		  	
		
		mealTypeAverage[0] = breakfastAmount/numBreakfast;
		mealTypeAverage[1] = lunchAmount/numLunch;
		mealTypeAverage[2] = dinnerAmount/numDinner;
		mealTypeAverage[3] = snackAmount/numSnack;
		
		if(breakfastAmount == 0 || numBreakfast == 0)
		{
			mealTypeAverage[0] = 0;
		}
		if(lunchAmount == 0 || numLunch == 0)
		{
			mealTypeAverage[1] = 0;
		}
		if(dinnerAmount == 0 || numDinner == 0)
		{
			mealTypeAverage[2] = 0;
		}
		if(snackAmount == 0 || numSnack == 0)
		{
			mealTypeAverage[3] = 0;
		}
	}
	
	public void calcMonthAverage() //Incomplete
	{
		double[] monthAverage = {endMonth - startMonth};
		
		
		this.monthAverage = monthAverage;
		
		List<String> dates = new ArrayList<String>();
		List<String> amounts = new ArrayList<String>();
        for(int j = 0; j < SNHULogOn.dataScrape.info.get(0).size(); j++)
        {
        		dates.add(SNHULogOn.dataScrape.info.get(j).get(0));
        		amounts.add(SNHULogOn.dataScrape.info.get(j).get(2));
        }
        
        int currMonth = parseMonth(dates.get(0));
        /*for(int i = 1; i < amounts.size(); i++)
        {
        	if (currMonth != parseMonth(dates.get(i)))
        	{
        		
        	}
        }*/
		
		
	}
	
	public void calcSpentPerDay()
	{
		//ReadWriteSQL perDay = new ReadWriteSQL();
		//perDay.createConnection();
		
		List<String> dates = new ArrayList<String>();
		List<String> amounts = new ArrayList<String>();
        for(int j = 0; j < SNHULogOn.dataScrape.info.size(); j++)
        {
        		dates.add(SNHULogOn.dataScrape.info.get(j).get(0));
        		amounts.add(SNHULogOn.dataScrape.info.get(j).get(2));
        		System.out.println(dates.get(j) + "\t" + amounts.get(j));
        }
        
		double totalSpent = 0;
		int totalDays = 1;
		totalSpent += parseAmount(amounts.get(0));
		for(int i = 1; i < amounts.size(); i++)
		{
			System.out.println("Current Amount: " + amounts.get(i-1) + "\t\tTotal: " + totalSpent + "\t\tCurrent Day: " + parseDay(dates.get(i)) + "\t\tDay count: " + totalDays);
			totalSpent += parseAmount(amounts.get(i-1));
			
			if(parseDay(dates.get(i)) != parseDay(dates.get(i-1)))
			{
				totalDays++;
			}
			
			if(startMonth == parseMonth(dates.get(i-1)))
			{
			}
		}

		
		spentPerDay = totalSpent/totalDays;
		
	}
	
	public void calcEstAmountLeft()
	{
		estAmountLeft = parseBalance(SNHULogOn.dataScrape.currBalance)/daysLeft;	
	}
	
	public double[] getMealTypeAverage()
	{
		return mealTypeAverage;
	}
	
	public double[] getMonthAverage()
	{
		return monthAverage;
	}
	
	public double getSpentPerDay()
	{
		return spentPerDay;
	}
	
	public double getEstAmountLeft()
	{
		return estAmountLeft;
	}
	
	public int getDaysLeft()
	{
		return daysLeft;
	}
	
	public double parseBalance(String balance)
	{
		return Double.parseDouble(balance.substring(1));
	}
}