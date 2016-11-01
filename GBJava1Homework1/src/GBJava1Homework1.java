/*
 * homework1 by Sergey Anisimov
 * 10\07\2016 
 */
import java.io.*;

public class GBJava1Homework1 {
    public static void main(String[] args) throws NumberFormatException, IOException {
        allTypes();
        
        System.out.println("Enter a:");
        int a = setVariable();
        System.out.println("Enter b:");
        int b = setVariable();
        System.out.println("Enter c:");
        int c = setVariable();
        System.out.println("Enter d:");
        int d = setVariable();
        System.out.println("a * (b + (c / d)) = " + equation(a, b, c, d));
        
        System.out.println("10< b + d < 20 is " + addCheck(b, d));
        
        System.out.print("Enter year: ");
        int year = setVariable();	
        if (isYearLeap(year))
            System.out.println("Year is leap");
        else
			System.out.println("Year isn't leap");
	}
	
	// 2 not for use =============================================================
	public static void allTypes(){
		byte byteVar = 120; 
		short shortVar = 32000; 
		int intVar = 264587;
		long longVar = 46598984651L;
		float floatVar = 2.64f;
		double doubleVar = 320.56d;
		boolean someFlag = true;
		char character = 'c';
		System.out.println("byte - " + byteVar + ", Short - " + shortVar + ", Integer - " + intVar + ", Long - " + longVar);
		System.out.println("float - " + floatVar + ", double - " + doubleVar + ", boolean - " + someFlag + ", char - " + character);
		
	}
	
	// 3 =====================================================================
	static double equation(int a, int b, int c, int d){
		return a * (b + ((double)c / d));		
	}
	
	// 4 ======================================================================
	static boolean addCheck(int f, int g){
		return (f+g > 10 && f+g < 20);
	}
	
	// 5* =====================================================================
	static boolean isYearLeap(int y){
		return (y%4 == 0 && y%100 != 0 || y%400 == 0);
	}
	
	//=Method for reading input data===========================================
	public static int setVariable() throws NumberFormatException, IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(reader.readLine());
		return n;
	}
}
