package model.Behavioral;

public class StrategyMain {
	
	
	public static void main(String[] args) {
		
		context context = new context(new BackDoor());
		context.operate();
		
		
		context context2 = new  context(new BlackEnemy());
		context2.operate();
		
		context context3 = new context(new GivenGreenLight());
		context3.operate();
		
		
		
		
		
	}

}

/**
 * 
 * 策略模式
 * 
 * 其意图是定义一系列的算法，把他们一个个封装起来，并且使他们相互替换。
 * 本模式使得算法可独立于使用它的客户端的变化而变化
 * 
 */

class context {
	private Strategy strategy;
	
	public context(Strategy strategy){
		this.strategy = strategy;
	}
	
	public void operate(){
		this.strategy.operator();
	}
}

class BlackEnemy implements Strategy{
	
	@Override
	public void operator() {
		System.out.println("孙夫人断后，挡住追兵...");  
	}
}




class GivenGreenLight implements Strategy{
	@Override
	public void operator() {
		 System.out.println("求吴国太开个绿灯，放行！");  		
	}
}


class BackDoor implements Strategy{	
	@Override
	public void operator() {
		System.out.println("找乔国老帮忙，让吴国太给孙权施加压力，使孙权不能杀刘备...");
		
	}
}


interface Strategy{	
	public void operator();
}