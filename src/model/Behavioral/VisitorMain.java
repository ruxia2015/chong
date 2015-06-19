package model.Behavioral;

import java.util.ArrayList;
import java.util.List;



public class VisitorMain {
	public static void main(String[] args) {
		ObjectStructure o = new ObjectStructure();
		o.attach(new Man());
		o.attach(new Woman());
		
		SuccessAction s = new SuccessAction();
		o.Display(s);
		
		FaillingAction a2 = new FaillingAction();
		o.Display(a2);
		
	}

}

/**
 * 访问者
 * 
 * 表示一个作用于某对象结构中的个元素的操作，它使你可以在不改变各元素的类的前提下定义作用于这些元素的新操作。
 */

abstract class VisitorAction{
	
	//得到男人的结论或反应
	public abstract void getManConclusion(Man concreteElementA);
	
	
	//得到女人结论或者反应
	public abstract void geWomanConculsuion(Woman concreteElementB);
	
}

interface person{
	public void accept(VisitorAction visitor);
}

class SuccessAction extends VisitorAction{
	public void getManConclusion(Man concreteElementA) {
		
		System.out.println(concreteElementA	.getClass().getName()+"  "+this.getClass().getName() + "背后多半有一个伟大的女人");
	}
	

	@Override
	public void geWomanConculsuion(Woman concreteElementB) {
		System.out.println(concreteElementB	.getClass().getName()+"  "+this.getClass().getName() + "背后多半有一个不成功的男人");

		
	};
}	

	class FaillingAction extends VisitorAction{
		public void getManConclusion(Man concreteElementA) {
			
			System.out.println(concreteElementA	.getClass().getName()+"  "+this.getClass().getName() + "背后多半有一个伟大的女人-shibai");
		}
		

		@Override
		public void geWomanConculsuion(Woman concreteElementB) {
			System.out.println(concreteElementB	.getClass().getName()+"  "+this.getClass().getName() + "背后多半有一个伟大的女人-shibai");

			
		};
	}

	class Man implements person{
		@Override
		public void accept(VisitorAction visitor) {
			visitor.getManConclusion(this);
			
		}
		
	}
	
	class Woman implements person{
		@Override
		public void accept(VisitorAction visitor) {
			visitor.geWomanConculsuion(this);
			
		}
	}
	

	
class ObjectStructure {
	private List<person> elements = new ArrayList<person>();
	
	public void attach(person person){
		elements.add(person);
	}
	
	public void detach(person person){
		elements.remove(person);
	}
	
	public void Display(VisitorAction visitor){
		for (person p:elements) {
			p.accept(visitor);
		}
	}
	
	
}	

