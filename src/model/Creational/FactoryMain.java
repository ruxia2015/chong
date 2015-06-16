package model.Creational;

public class FactoryMain {
	
	public static void main(String[] args) {
		Creator creator = new ConcreteCreatorA();
		IProduct productA = creator.factoryMethod();
		productA.run();
		
		creator = new ConcreteCreatorB();
		IProduct productB = creator.factoryMethod();
		productB.run();
		
	}

}

/**
 * 工厂方法模式
 * 
 * 定义一个用于创建对象的接口，让子类决定实例化哪一个类，工厂模式使一个类的实例化延迟到子类。
 * 
 * 工厂和产品 -- 一对一的关系
 * 
 */


interface IProduct{ public void run();}
class ProductA implements IProduct{
	@Override
	public void run() {
		System.out.println(" productA ......... ");
	}
}

class ProductB implements IProduct{
	@Override
	public void run() {
		System.out.println(" productA ......... ");
	}
}

interface Creator{IProduct factoryMethod();};
class ConcreteCreatorA implements Creator{ @Override
public IProduct factoryMethod() {
	return new ProductA();	
} };

class ConcreteCreatorB implements Creator{ @Override
public IProduct factoryMethod() {
	return new ProductB();	
} };