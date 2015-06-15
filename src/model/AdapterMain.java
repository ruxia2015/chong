package model;


public class AdapterMain {

	public static void main(String[] args) {
		
		
		Target target = new Adaptor();
		target.request();
		
		Adaptee ad = new Adaptee();
		Target target2 = new Adaptor2(ad);
		target2.request();
		
	}
}



/**
 * 适配器
 * 将一个类的接口转换成客户所希望的另外一个接口，适配器模式使得原本由于接口不兼容而不能在一起工作的那些类可以一起工作。
 * 
 */


class Adaptor2 implements Target{
	private Adaptee adaptee;
	public Adaptor2(Adaptee adaptee) {
		this.adaptee = adaptee;
	}
	
	
	@Override
	public void request() {
		if(adaptee!=null){
			adaptee.request_new();
		}
		
	}
}

class Adaptor extends Adaptee implements Target{
	@Override
	public void request() {
		// TODO Auto-generated method stub
		super.request_new();
	}
}


class Adaptee{
	public void request_new(){
		System.out.println("request Adaptee");
	}
}


interface Target{
	
	void request();
}