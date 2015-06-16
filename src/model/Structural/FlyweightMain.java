package model.Structural;

import java.util.Hashtable;

public class FlyweightMain {
	
	public static void main(String[] args) {
		int ext = 22;
		FlyweightFactory f = new FlyweightFactory();
		
		Flyweight fX = f.getFlyweight("X");
		fX.operation(--ext);
		
		Flyweight fY = f.getFlyweight("Y");
		fY.operation(--ext);
			
		Flyweight fX2 = f.getFlyweight("X");
		
		System.out.println(fX==fX2);
		
		UnSharedFlyweight flyweightB = new UnSharedFlyweight();
		flyweightB.operation(--ext);
			
	}

}

/**
 * 享元模式(线程池)
 * 
 * 为运用共享技术有效的支持大量细粒度的对象
 * 
 *  1、根据对象类型动态的创建对象实例。

            2、根据对象池中的配置，在对象池中找到空闲的实体提供给程序调用，减少创建对象的次数。

            3、我们需要设计每个类型的缓冲池，通过把对象进行缓存，提供性能。如果对象池中的对象长期不会调用，那么我们会提供一个销毁对象的机制。
 * 
 */

class FlyweightFactory{
	private Hashtable<String, Flyweight> hashtable = new Hashtable<String, Flyweight>();
	
	public FlyweightFactory() {
//		hashtable.put("X", new FlyweightA());
//		hashtable.put("Y", new FlyweightA());
//		hashtable.put("Z", new FlyweightA());
	}
	Flyweight getFlyweight(String key){
		if(!hashtable.containsKey(key)){
			hashtable.put(key, new FlyweightA());
		}
		return hashtable.get(key);
	}
}


interface Flyweight{
	
	public void operation(int i);
}


class FlyweightA implements Flyweight{

	@Override
	public void operation(int i) {
		System.out.println("FlyweightA " +i);
		
	}
	
}

class UnSharedFlyweight implements Flyweight{

	@Override
	public void operation(int i) {
		System.out.println("UnSharedFlyweight " +i);
		
	}
	
}


