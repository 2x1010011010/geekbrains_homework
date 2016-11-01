/*
 * Homework 4 by Sergey Anisimov
 */

public class GBJava1Homework4 {

	public static void main(String[] args) {

		Employee[] employeeArr = new Employee[5];
		employeeArr[0] = new Employee("Tolstoy Lion", "Writer", "l.tolstoy@corp.com", "3214598", 60000, 50);
		employeeArr[1] = new Employee("Schumacher Mikhael", "Driver", "m.schumacher@corp.com", "3214599", 42000, 39);
		employeeArr[2] = new Employee("Kats Abram", "Accountant", "a.kats@corp.com", "3214578", 155000, 55);
		employeeArr[3] = new Employee("Gogol Nikolai", "Writer", "n.gogol@corp.com", "3214579", 75000, 46);
		employeeArr[4] = new Employee("Ivanov Ivan", "Assistant", "i.ivanov@mail.com", "3214577", 22000, 23);

	    for (int i = 0; i<employeeArr.length; i++) {
	        if (employeeArr[i].getAge() > 40) {
	        	employeeArr[i].showEmployee();
	        }
	    }
	}
}

class Employee {
    private String name;
    private String position;
    private String email;
    private String phone;
    private int salary;
    private int age;

    Employee(String name, String position, String email, String phone, int salary, int age) {
        this.name = name;
        this.position = position;
        this.email = email;
        this.phone = phone;
        this.salary = salary;
        this.age = age;
    }

	public int getAge() {
	    return this.age;
	}

    public void showEmployee() {
        System.out.println(this.name +"\n Position: " + this.position + "\n Email: " + this.email + "\n Phone: " + this.phone + "\n Salary: " + this.salary + "\n Age: " + this.age + "\n" + "=================================");
    }
}
