package model.Behavioral;

public class CommandMain {
	public static void main(String[] args) {
		Receiver r = new Receiver();
		Command c = new ConcreteCommand(r);
		Invoker i = new Invoker();		
		i.setCommand(c);		
		i.ExecuteCommand();
		
		}

}

/**
 * 将一个请求封装为一个对象，从而使你可以用不同的请求为客户进行参数化；
 * 可以对请求排队或记录请求日志，以及支持可撤销的操作。
 */




abstract class Command{
	protected Receiver receiver;
	
	public Command(Receiver receiver) {
		this.receiver = receiver;
	}
	
	abstract public void execute();
}

//将一个接收者对象绑定于一个动作，调用接收者相应的操作，以实现Execute
class ConcreteCommand extends Command{

	public ConcreteCommand(Receiver receiver) {
		super(receiver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		receiver.action();
		
	}
	
}

class Invoker{
	private Command command;

	public void setCommand(Command command) {
		this.command = command;
	}
	
	public void ExecuteCommand(){
		command.execute();
	}
}




class Receiver{

	public void action() {
		System.out.println("执行请求");
		
	}
	
}
