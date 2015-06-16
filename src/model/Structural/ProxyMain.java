package model.Structural;

public class ProxyMain {
	
	public static void main(String[] args) {
		Proxy proxy = new Proxy();
		proxy.request();
		
	}

}


/**
 * 代理模式
 * 
 * 为其他对象提供一种代理控制对这个对象的访问
 * 
 * 
 */

interface Subject{
	void request();
}


class RealSubject implements Subject{
	@Override
	public void request() {
		System.out.println("RealSubject 我是");
		
	}
}

class Proxy implements Subject{
	private RealSubject subject;
	

	@Override
	public void request() {
		if(subject==null){
			subject = new RealSubject();
		}
		subject.request();
		
	}
}


