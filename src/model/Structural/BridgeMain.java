package model.Structural;

public class BridgeMain {
	
	public static void main(String[] args) {
		AbstractRoad speedWay = new SpeedWay();  
		speedWay.aCar = new Car();
		speedWay.run();
		
		AbstractRoad street = new Street();  
	    street.aCar = new Bus();  
	    street.run(); 

		
	}

}

/**
 * 桥接模式
 * 将抽象部分与它的实现部分分离，使他们独立的变化。
 * 
 * 什么叫抽象与它的实现部分分离，这并不是说，让抽象类和其派生类分离。因为这没有任何的意义，
 * 
 * 实现指的是抽象类和它的派生类用来实现自己的对象。
 * 
 */

//以车（不同的车）行驶在路（不同的路）上为例



class AbstractRoad{
	AbstractCar aCar;
	void run(){
		System.out.println("路");
	}	
}

abstract class AbstractCar{
	 void run(){};  
}

class Street extends AbstractRoad{ 
	void run(){
		super.run();
		aCar.run();
		System.out.println("在市区街上走");
	}
}

class SpeedWay  extends AbstractRoad{ 
	void run(){
		super.run();
		aCar.run();
		System.out.println("在高速公路上走");
	}
}

class Car extends AbstractCar{
	   @Override  
	    void run() {  
	        // TODO Auto-generated method stub  
	        super.run();  
	        System.out.print("小汽车");  
	    }  
}

class Bus extends AbstractCar{  
    @Override  
    void run() {  
        // TODO Auto-generated method stub  
        super.run();  
        System.out.print("公交车");  
    }  
}  

