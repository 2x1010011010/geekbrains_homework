/*
 * Homework2 by Sergey Anisimov
 * 10/8/2016
 */
import java.util.*;

public class GBJava1Homework2 {

	public static void main(String[] args) {
		binaryArray(); //Вызов метода 1 и 2 задания
		addThreeArray(); // Вызов метода 3 задания
		arrayMultiplicateToTwo(); //Вызов метода 4 задания
		minmaxArray(); //Вызов метода 5 задания
		calculator(); //Вызов метода 6 задания
	}
// 1 и 2 задание в одном методе =====================================================================================
	static void binaryArray(){
		System.out.println("Задание 1,2:");
		// Задаем массив 0 и 1
		int[] binaryArray = {1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 1};
		//выводим заданный массив
		System.out.println("Начальные данные: ");
		for (int i=0; i<binaryArray.length; i++)
			System.out.print(binaryArray[i] + " ");
		System.out.println();
		//цикл замены 1 и 0
		for (int i = 0; i<binaryArray.length; i++)
			binaryArray[i] = 1 - binaryArray[i];
		//вывод измененного массива
		System.out.println("Измененный массив: ");
		for (int i=0; i<binaryArray.length; i++)
			System.out.print(binaryArray[i] + " ");
		System.out.println();
	}
	
//3 задание ==========================================================================================================
	static void addThreeArray(){
		System.out.println();
		System.out.println("Задание 3:");
		//Создаем массив из 8 элементов
		int[] array = new int [8];
		//присваиваем первому элементу значение 1
		array[0] = 1;
		//в цикле заполняем оставшиеся значения
		for (int i=1; i<8; i++)
			array[i] = array[i-1] + 3;
		//вывод результата
		System.out.println("массив а+3:");
		for (int i=0; i<8; i++)
			System.out.print(array[i] + " ");
		System.out.println();
	}
	
//4 задание ========================================================================================================
	static void arrayMultiplicateToTwo(){
		System.out.println();
		System.out.println("Задание 4:");
		//создаем массив
		int[] mas = { 1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1 };
		//вывод начальных данных
		System.out.println("Начальные данные:");
		for (int i=0; i<mas.length; i++)
			System.out.print(mas[i] + " ");
		//поиск и умножение на 2 элементов меньше 6
		for (int i=0; i<mas.length; i++)
			if (mas[i] < 6) mas[i] *= 2;
		System.out.println();
		//вывод результата
		System.out.println("Измененный массив:");
		for (int i=0; i<mas.length; i++)
			System.out.print(mas[i] + " ");
		System.out.println();
	}

//5* задание ========================================================================================================
	static void minmaxArray(){
		System.out.println();
		System.out.println("Задание 5*:");
		//создаем массив переменных
		int[] array = new int[10];
		int min, max;
		//в цикле заполняем массив случайными значениями
		for(int i=0; i<array.length; i++) {
			array[i] = new Random().nextInt(50);
		}
		//Вывод созданного массива
		System.out.println("Создан массив из " + array.length + " элементов:"); 
		for (int i=0; i<array.length; i++)
			System.out.print(array[i] + " ");
		System.out.println();
		//поиск минимума и максимума
		min = array[0];
		max = array[0];
		for(int i = 1; i<array.length; i++){
			if (array[i]<min) min = array[i];
			if (array[i]>max) max = array[i];
		}
		//вывод найденных значений
		System.out.println("Максимальное значение: " + max);
		System.out.println("Минимальное значение: " + min);
	}
	
//6** задание ==============================================================================================
	static void calculator(){
		int a, b;
	    String c;
		System.out.println();
		System.out.println("Задание 6**:");
		System.out.println("Введите в строку через пробел числа и оператор выражения (например, 2 + 3):");
		Scanner scanner = new Scanner(System.in);
		a = scanner.nextInt();                                  
	    c = scanner.next("\\+|\\-|\\*|\\/");
	    b = scanner.nextInt();
	    scanner.close();
		switch(c){
			case "+":
				System.out.println(a + b);
				break;
			case "-":
				System.out.println(a - b);
				break;
			case "*":
				System.out.println(a * b);
				break;
			case "/":
				if (b>0) 
					System.out.println((double)a/b);
				else
					System.out.println("Деление на 0");
				break;
		}
	}
}