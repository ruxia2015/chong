package model.Creational;

public class BuilderMain {
	public static void main(String[] args) {
		ConcreteBuilder builder = new ConcreteBuilder();
		Director director = new Director( builder ); 
		
		director.construct(); 
		Product product = builder.getResult();
	}

}

/**
 * 
 * 建造者模式
 * 
 * 将一个复杂的构建对象与它的表示分离，使得同样的构建过程可以创建不同的表示。
 * 
 */


//指挥者，是构建一个使用Builder接口的对象
class Director{
	private Builder builder;
	
	public Director(Builder builder) {
		this.builder = builder;
	}
	
	//对产品的组装
	public Product construct(){
		builder.buildPartA();
		builder.buildPartC();
		builder.buildPartB();
		
		 return builder.getResult();
	}
	
	
}

interface Builder{
	void buildPartA();	
	void buildPartB();
	void buildPartC();	
	
	public Product getResult();
}

//具体的构建者，实现builder接口，构建和装配各个部件。
class ConcreteBuilder implements Builder{
	Part partA,partB,partC;
	Product product = new Product();;

	
	public Product  getResult(){
		return product;
	}

	@Override
	public void buildPartA() {
		partA = new Part("A");
		product.addPart(partA);
		
	}

	@Override
	public void buildPartB() {
		partB = new Part("B");
		product.addPart(partB);
		
	}

	@Override
	public void buildPartC() {
		partC = new Part("C");
		product.addPart(partC);
		
	}
}


 class Product { void addPart(Part part){System.out.println("加入"+part.pa);} }
 class Part { 
	 String pa;
	 public Part(String pa){System.out.println("生产了"+pa) ; this.pa = pa;} 
	 }
