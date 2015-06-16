package model.Behavioral;

public class MediatorMain {

	public static void main(String[] args) {
		ConcreteNediator concreteNediator = new ConcreteNediator();

		ConcreteCollegue1 collegue1 = new ConcreteCollegue1(concreteNediator);
		ConcreteCollegue2 collegue2 = new ConcreteCollegue2(concreteNediator);

		concreteNediator.setCollegue1(collegue1);
		concreteNediator.setCollegue2(collegue2);

		collegue1.send("chi le mei?");
		collegue2.send("hai mei you !");

	}

}

/**
 * 
 * 中介者模式 用一个中介者对象来封装一系列的对象交互。 中介者使各对象不需要显示的相互引用，从而使其耦合松散，而且可以独立的改变他们之间的交互
 * 
 */


/**
 * 中介者抽象
 * @author xuruxia
 *
 */
interface Mediator {
	void send(String message, Colleague colleague);
}

class ConcreteNediator implements Mediator {
	private ConcreteCollegue2 collegue2;
	private ConcreteCollegue1 collegue1;



	public void setCollegue2(ConcreteCollegue2 collegue2) {
		this.collegue2 = collegue2;
	}


	public void setCollegue1(ConcreteCollegue1 collegue1) {
		this.collegue1 = collegue1;
	}

	//根据实际需求改造
	@Override
	public void send(String message, Colleague colleague) {
		if (colleague == collegue1) {
			collegue2.notify(message);
		} else {
			collegue1.notify(message);
		}

	}

}

class Colleague {
	Mediator mediator;

	public Mediator getMediator() {
		return mediator;
	}

	public void setMediator(Mediator mediator) {
		this.mediator = mediator;
	}


}

class ConcreteCollegue1 extends Colleague {
	public ConcreteCollegue1(Mediator mediator) {
		super.setMediator(mediator);
	}

	public void send(String message) {
		mediator.send(message, this);
	}

	public void notify(String message) {
		System.out.println("同事1收到了消息：" + message);
	}

}

class ConcreteCollegue2 extends Colleague {
	public ConcreteCollegue2(Mediator mediator) {
		super.setMediator(mediator);
	}

	public void send(String message) {
		mediator.send(message, this);
	}

	public void notify(String message) {
		System.out.println("同事2收到了消息：" + message);
	}

}
