package model.Creational;

public class AbstractFactoryMain {
	
	public static void main(String[] args) {
		IFactory1 factory1 = new ConcreateFactory1();
		IProductA iProductA1 = factory1.getProductA1();
		IProductB productB1 = factory1.getProductB1();
		
		IFactory2 factory2 = new ConcreateFactory2();
		IProductA iProductA2 = factory2.getProductA2();
		IProductB iProductB2 = factory2.getProductB2();
		
	}

}

/**
 * 抽象工厂
 * 
 * 提供一个创建一系列或者相关依赖对象的接口，而无需指定他们具体的类。
 * 
 * 
 *	(1) 一个系统不应当依赖于产品类实例如何被创建、组合和表达的细节，这对于所有形态的工厂模式都是重要的。

	(2) 这个系统有多于一个的产品族，而系统只消费其中某一产品族。

	(3) 同属于同一个产品族的产品是在一起使用的，这一约束必须在系统的设计中体现出来。

	(4) 系统提供一个产品类的库，所有的产品以同样的接口出现，从而使客户端不依赖于实现。
	
	工厂和产品 -- 一对多的关系
 * 
 */


interface IProductA{
	public void method(); 
}

class ProductA1 implements IProductA{
	public void method(){}
}

class ProductA2 implements IProductA{
	public void method(){}
}


interface IProductB{
	public void method(); 
}

class ProductB1 implements IProductB{
	@Override
	public void method() {
		// TODO Auto-generated method stub
		
	}
}

class ProductB2 implements IProductB{
	@Override
	public void method() {
		// TODO Auto-generated method stub
		
	}
}
//-------------------------------------------

interface IFactory1{
	IProductA getProductA1();
	IProductB getProductB1();
}

interface IFactory2{
	IProductA getProductA2();
	IProductB getProductB2();
}

class ConcreateFactory1 implements IFactory1{
	@Override
	public IProductA getProductA1() {
		// TODO Auto-generated method stub
		return new ProductA1();
	}
	
	@Override
	public IProductB getProductB1() {
		// TODO Auto-generated method stub
		return new ProductB1();
	}
}

class ConcreateFactory2 implements IFactory2{
	@Override
	public IProductA getProductA2() {
		// TODO Auto-generated method stub
		return new ProductA2();
	}
	
	@Override
	public IProductB getProductB2() {
		// TODO Auto-generated method stub
		return new ProductB2();
	}
}







