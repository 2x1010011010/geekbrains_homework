/*
 * Homework 5 by Sergey Anisimov v.1.1 (add for (Animal animal : animalArray))
 */

public class GBJava1Homework5 {

    public static void main(String[] args) {
        new GBJava1Homework5().run();
    }
    
    void run(){
        Animal[] animalArray = { new Dog("Rex",29,2,true), new Cat("Barsik",33,3,false), new Horse("Cherokee",60,3,true) };
        for (Animal animal : animalArray) {
            System.out.println("=======================================================");
            System.out.println(animal.getClass().getSimpleName());
            System.out.println("Name = " + animal.getName());
            System.out.println("Run with speed = " + animal.getSpeed() + " km/h");
            System.out.println("Can sweam = " + animal.getCanSweam());
            System.out.println("Jump over " + animal.getJumpHight() + " meters");
            System.out.println("=======================================================");
        }
    }
}


abstract class Animal{
    protected int speed;
    protected int jumpHight;
    protected boolean canSweam;
    protected String name;
    
    Animal(String name, int speed, int jumpHight, boolean canSweam){
        this.name = name;
        this.jumpHight = jumpHight;
        this.speed = speed;
        this.canSweam = canSweam;
    }
    
    public String getName(){
        return name;
    }
    public int getSpeed(){
        return speed;
    }
    public int getJumpHight(){
        return jumpHight;
    }
    public boolean getCanSweam(){
        return canSweam;
    }
}


class Cat extends Animal{
    Cat(String name, int speed, int jumpHight, boolean canSweam){
        super(name, speed,jumpHight, canSweam);
    }
}

class Dog extends Animal{
    Dog(String name, int speed, int jumpHight, boolean canSweam){
        super(name, speed,jumpHight, canSweam);
    }
}

class Horse extends Animal{
    Horse(String name, int speed, int jumpHight, boolean canSweam){
        super(name, speed,jumpHight, canSweam);
    }
}