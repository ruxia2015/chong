package model;


public class DecoratorMain
{
    
	public static void  main(String[]  argds) {
		ComponentA compentA = new ComponentA();
		
		DecoratorA directorA = new DecoratorA();
		
		DecoratorB directorB = new DecoratorB();
		
		
		directorA.setCompent(compentA);
		directorB.setCompent(directorA);
		
		//directorA.aa();
		directorB.aa();
	}

}


/**
 * 装饰模式
 * 动态的给对象添加一些额外的职责，就添加功能来说，装饰模式相比生成子类更加的灵活
 * @author xuruxia
 *
 */

class DecoratorA extends Decorator{

	@Override
	public void aa() {
		super.aa();
		System.out.println("带花");
		
	}
	
}

class DecoratorB extends Decorator{

	@Override
	public void aa() {
		super.aa();
		System.out.println("带彩儿 ");
		
	}
	
}

class ComponentA implements Component{
	
	public void aa(){
		System.out.println("woshi yuanshi de ");
	}
}

abstract class  Decorator implements Component{
	private Component com;
	
    void setCompent(Component com){
    	this.com = com;
    }
	
    @Override
	public  void aa(){
    	if(com!=null){
    		com.aa();
    	}
    }
	
}



interface Component{
	void aa();
}