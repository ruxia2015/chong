package model.Behavioral;

public class TemplateMethodMain {
	public static void main(String[] args) {
		AbstractClass c ;
		
		c = new ConcreteClassA();
		c.templateMethod();
		
		c= new ConcreteClassB();
		c.templateMethod();
	}

}

/***
 * 模板方法
 * 
 * 定义一个操作的算法的骨架，而降一些步骤延迟到子类中，模板方法使得子类可以不改变一个算法的结构
 * 即可重定义该算法的某些特定的步骤。
 * 
 */


abstract class AbstractClass{
	
	public abstract void primitiveOperation1();
	public abstract void primitiveOperation2();
	
	public void templateMethod(){
		primitiveOperation1();
		primitiveOperation2();
		
		System.out.println("  ");
	}
}

class ConcreteClassA extends AbstractClass{
	@Override
	public void primitiveOperation1() {
		System.out.println("具体类A方式1");
		
	}
	@Override
	public void primitiveOperation2() {
		System.out.println("具体类A方式2");
		
	}
}

class ConcreteClassB extends AbstractClass{
	@Override
	public void primitiveOperation1() {
		System.out.println("具体类B方式1");
		
	}
	@Override
	public void primitiveOperation2() {
		System.out.println("具体类B方式2");
		
	}
}


