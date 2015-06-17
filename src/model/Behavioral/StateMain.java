package model.Behavioral;

public class StateMain {
	public static void main(String[] args) {

		Work c = new Work();
		
		c.hour = 9;
		c.writerProgram();
		
		c.hour = 12;
		c.writerProgram();
		
		c.hour = 14;
		c.writerProgram();
		
		c.finish = false;
		c.hour = 18;
		c.writerProgram();
		
		c.hour = 20;
		c.writerProgram();

		c.hour = 22;
		c.writerProgram();
		
		



	}

}

/***
 * 
 * 状态模式 允许一个对象在其内部状态改变它的行为，让对象看起来似乎修改它的类。
 * 
 * 主要解决 当控制一个对象的状态转换的条件表达式过于复杂时的情况，把状态的判断逻辑转移到表示不同状态的一系列类当中。 可以把复杂的判断逻辑简化
 * 
 */

interface State{
	public void work(Work w);
}

class ForenoonState implements State{

	@Override
	public void work(Work w) {
		if(w.hour < 12){
			System.out.println("当前时间为【"+w.hour+"】点，上午工作，精神百倍");
		}else{
			w.setState(new NoonState());
			w.writerProgram();
		}
		
	}

}

class NoonState implements State{
	@Override
	public void work(Work w) {
		if(w.hour < 13){
			System.out.println("当前时间为【"+w.hour+"】点，饿了，午饭：");
		}else{
			w.setState(new AfternoonState());
			w.writerProgram();
		}
		
	}

}

class AfternoonState implements State{
	@Override
	public void work(Work w) {
		if(w.hour < 17){
			System.out.println("当前时间为【"+w.hour+"】点，下午：");
		}else{
			w.setState(new EveningState());
			w.writerProgram();
		}
		
	}

}

class EveningState implements State{
	@Override
	public void work(Work w) {
		
		if(w.finish){
			w.setState(new RestState());
			w.writerProgram();
		}else{
			
			if(w.hour < 21){
				System.out.println("当前时间为【"+w.hour+"】点，加班：");
			}else{
				w.setState(new SleepingState());
				w.writerProgram();
			}
			
		}
		
	}

}

class SleepingState implements State{

	@Override
	public void work(Work w) {
	System.out.println("不行了，睡了");
		
	}
	
}


class RestState implements State{

	@Override
	public void work(Work w) {
		System.out.println("走吧");
		
	}
	
}


class Work{
	int hour;
	State state;
	
	boolean finish = false;
	
	
	
	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public boolean isFinish() {
		return finish;
	}

	public void setFinish(boolean finish) {
		this.finish = finish;
	}

	public Work() {
		state = new ForenoonState();
	}
	
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	
	public void writerProgram(){
		state.work(this);
	}
	
	
	
	
}




















/*
interface State {
	public void handle(StateContext c);
}

class ConcreteStateA implements State {

	@Override
	public void handle(StateContext c) {
		c.state = new ConcreteStateB();
	}
}

class ConcreteStateB implements State {

	@Override
	public void handle(StateContext c) {
		c.state = new ConcreteStateA();

	}
}

class StateContext {
	State state;

	public StateContext(State s) {
		this.state = s;
	}

	public void request() {
		System.out.println("当前的状态是 ： " + state.getClass().getName());
		state.handle(this);
	}

}*/