package model.Creational;

public class PrototypeMain {
	
	public static void main(String[] args) {

		ConcretePrototype cp = new ConcretePrototype();
		for(int i=0; i< 10; i++){
			ConcretePrototype clonecp = (ConcretePrototype)cp.clone();
			clonecp.show();
		}
	
	}

	
}



/**
 * 原型模式
 * 用原型实例指定创建对象的种类，并且通过拷贝这些原型创建新的对象
 * 
 */


class Prototype implements Cloneable {
	public Prototype clone(){
		Prototype prototype = null;
		try{
			prototype = (Prototype)super.clone();
		}catch(CloneNotSupportedException e){
			e.printStackTrace();
		}
		return prototype; 
	}
}

class ConcretePrototype extends Prototype{
	public void show(){
		System.out.println("原型模式实现类");
	}
}

