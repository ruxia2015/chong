package model.Structural;

public class FacadeMain {
	
	public static void main(String[] args) {
		
		Fade fade = new Fade();
		fade.methodA();
		fade.methodB();
		
	}

}


/**
 * 
 * 为子系统中的一组接口提供一个一致的界面。外观模式定义了一个高层接口，这个接口使得这一子系统更加容易使用。
 * 
 * 
 */


class Fade{
	private SubSystemOne one;
	private SubSystemTwo systemTwo;
	private SubSystem3 subSystem3;
	private SubSystem4 subSystem4;
	
	public Fade() {
		one = new SubSystemOne();
		systemTwo = new SubSystemTwo();
		subSystem3 = new SubSystem3();
		subSystem4 = new SubSystem4();
	}
	
	
	public void methodA(){
		System.out.println("方法组A");
		one.methodOne();
		systemTwo.methodTwo();
		subSystem3.method3();
	}
	
	public void methodB(){
		System.out.println("方法组B");
		one.methodOne();
		subSystem3.method3();
		subSystem4.method4();
	}
}


class SubSystemOne{
	void methodOne(){
		System.out.println("方法一");
	}
}

class SubSystemTwo{
	void methodTwo(){
		System.out.println("方法2");
	}
}

class SubSystem3{
	void method3(){
		System.out.println("方法3");
	}
}

class SubSystem4{
	void method4(){
		System.out.println("方法4");
	}
}
