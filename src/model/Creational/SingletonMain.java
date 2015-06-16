package model.Creational;

public class SingletonMain {
	public static void main(String[] args) {
		
		Singleton singleton = Singleton.getInstance();
		
		Singleton singleton3 = Singleton.getInstance();
		
		System.out.println(singleton == singleton3);
		
	}
}

/**
 * 单例模式
 * 保证一个类仅有一个实例，并提供一个访问它的全局访问点。
 * 
 */

//懒汉式单例
class Singleton{
	private  static Singleton singleton = null;
	private Singleton(){
		
	}
	
	public static synchronized Singleton getInstance(){
		
		if(singleton ==null){
			singleton = new Singleton();
		}
		return singleton;
		
	}
	
}

//饿汉式单例
class Singleton1 {
    //私有的默认构造子
    private Singleton1() {}
    //已经自行实例化 
    private static final Singleton1 single = new Singleton1();
    //静态工厂方法 
    public static Singleton1 getInstance() {
        return single;
    }
}