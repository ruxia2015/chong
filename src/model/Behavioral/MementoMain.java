package model.Behavioral;

public class MementoMain {
	
		public static void main(String[] args) {
			
			Originator originator = new Originator();
			
			Caretaker caretaker = new Caretaker();
			
			originator.setState("On");
			
			caretaker.saveMemento(originator.createMemento());
			
			originator.setState("close");
			
			originator.restoreMemento(caretaker.retrieveMemento());
			
			System.out.println(originator.getState());
			
			
		}

}


/**
 * 发起人角色类，发起人角色利用一个新创建的备忘录对象将自己的内部状态存储起来。
 * @author xuruxia
 *
 */

class Originator {
	private String state;
	
	public Memento createMemento(){
		 return new Memento(state);
	}
	
	public void restoreMemento(Memento memento){
		this.state = memento.getState();
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	
	
}


/**
 * 备忘录角色类，备忘录对象将发起人对象传入的状态存储起来。
 * @author xuruxia
 *
 */

class Memento{
	private String state;
	public Memento(String state) {
		
		this.state = state;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}

/**
 * 负责人角色类，负责人角色负责保存备忘录对象，但是从不修改（甚至不查看）备忘录对象的内容。
 * @author xuruxia
 *
 */
class Caretaker{
	private Memento memento;
	public Memento retrieveMemento(){
		return this.memento;
	}
	
	public void saveMemento(Memento memento){
		this.memento = memento;
	}
}





