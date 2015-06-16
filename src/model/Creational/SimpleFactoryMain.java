package model.Creational;

public class SimpleFactoryMain {

	public static void main(String[] args) {
		SimpleCreator factoryBuilder = new SimpleCreator();
		I2Product productM = factoryBuilder.instanceProduct("M");
		productM.product();
		
		I2Product productN = factoryBuilder.instanceProduct("N");
		productN.product();
		
		I2Product productO = factoryBuilder.instanceProduct("O");
		productO.product();
		

		
		
	}
}

/**
 * 简单工厂模式
 * 
 * 简单工厂模式是属于创建型模式，又叫做静态工厂方法（Static Factory Method）模式，但不属于23种GOF设计模式之一。
 * 
 * 简单工厂模式的实质是由一个工厂类根据传入的参数，动态决定应该创建哪一个产品类（这些产品类继承自一个父类或接口）的实例.
 *
 */



// 简单工厂模式的核心，它负责实现创建所有实例的内部逻辑。工厂类的创建产品类的方法可以被外界直接调用，创建所需的产品对象。
class SimpleCreator{
	
	public  I2Product instanceProduct(String oper){
		I2Product product = null;
		 
		switch(oper) {
		case "M":
			product = new ProductM();
			break;
		case "N":
			product = new ProductN();
			break;
		case "O":
			product = new ProductO();
			break;
		default:
			break;
		}
		
		return product;
	}
	
}


// 简单工厂模式所创建的所有对象的父类，它负责描述所有实例所共有的公共接口。
interface I2Product{
	void product();

}

//是简单工厂模式的创建目标，所有创建的对象都是充当这个角色的某个具体类的实例。
class ProductM implements I2Product{
	@Override
	public void product() {
		System.out.println("我是产品M");
		
	}
	
}


//是简单工厂模式的创建目标，所有创建的对象都是充当这个角色的某个具体类的实例。
class ProductN implements I2Product{
	@Override
	public void product() {
		System.out.println("我是产品N");
		
	}
	
}

//是简单工厂模式的创建目标，所有创建的对象都是充当这个角色的某个具体类的实例。
class ProductO implements I2Product{
	@Override
	public void product() {
		System.out.println("我是产品O");
		
	}
	
}
