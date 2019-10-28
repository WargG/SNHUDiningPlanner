import java.util.Scanner;

public class MainClass {

	public static void main(String[] args) {
		//SNHULogOn a = new SNHULogOn();
		//a.logOn();
		
		Prediction predict = new Prediction(2);
		predict.predictionSettings("October 20, 2019", "December 20, 2019", 5, 600, 1000, 1000, 1600, 1600, 2000);
		System.out.println("Settings (set in settings tab):\n\tSchool year: October 20, 2019-December 20, 2019\n\tDays off: 5\n\tBreakfast Times: 6:00AM - 10:00AM\n\t Lunch Times: 10:00AM - 4:00PM\n\tDinner Times: 4:00PM - 8:00PM");
		
		predict.calcMealTypeAverage();
		predict.calcSpentPerDay();
		//predict.calcEstAmountLeft();
		
		double[] mealTypeAverage = predict.getMealTypeAverage();
		double avgDay = predict.getSpentPerDay();
		
		System.out.println("\n-----------------------------------------------\nAverage expense per meal type (breakfast, lunch, etc.): ");
		System.out.println("Breakfast average: " + mealTypeAverage[0]);
		System.out.println("Lunch average: " + mealTypeAverage[1]);
		System.out.println("Dinner average: " + mealTypeAverage[2]);
		System.out.println("Snack average: " + mealTypeAverage[3]);
		
		System.out.println("\n------------------------------------------------\nAverage spent per day: " + avgDay);
		
		System.out.println("\n------------------------------------------------\nEnter a string to be encrypted");
		Scanner in = new Scanner(System.in);
		
		String toBeEncrypted = in.nextLine();
	
		PasswordEncryption encryp = new PasswordEncryption();
		PasswordDecryption decryp = new PasswordDecryption();
		
		String encrypted = encryp.encryptionAES(toBeEncrypted);
		
		System.out.println("Your encrypted string is: " + encrypted);
		
		System.out.println("Your decrypted string is: " + decryp.decryptAES(encrypted));
		
	}
}