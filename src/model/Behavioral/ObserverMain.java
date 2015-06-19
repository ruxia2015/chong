package model.Behavioral;

import java.util.ArrayList;
import java.util.List;

public class ObserverMain {
	public static void main(String[] args) {
		ConcreteSubject s = new ConcreteSubject();
		
		s.attach(new ConcreteObserver(s,"x"));
		s.attach(new ConcreteObserver(s,"Y"));
		s.attach(new ConcreteObserver(s,"z"));
		
		s.subjectState = "ABC";
		s.Notify();
		
	}

}

/**
 * 观察者模式
 * 		定义对象间的一种一对多的依赖关系，当一个对象的状态发生改变时，所依赖它的对象都得到通知并被自动更新。
 * 
 * 定义了一种一对多的依赖关系，让多个观察者对象同时监听某一个主题对象。这个主题对象在状态发生变化时，会通知所有观察者对象，使他们能够自动更新自己。
 */



abstract class Subject{
	private List<Observer> observers = new ArrayList<Observer>();
	
	public void attach(Observer observer){
			observers.add(observer);
	}
	
	public void detach(Observer observer){
		observers.remove(observer);
	}
	
	public void Notify(){
		for (Observer o : observers) {
			o.update();
		}
	}
	
	
}


class ConcreteSubject extends Subject{
	public String subjectState;
	
}

class ConcreteObserver implements Observer{
	private String name;
	private String observerState;
	private ConcreteSubject concreteSubject;
	
	
	public ConcreteObserver(ConcreteSubject s, String string) {
	this.concreteSubject = s;
	this.name = string;
	}


	@Override
	public void update() {
		observerState = concreteSubject.subjectState;
		System.out.println("观察者【"+name+"】的新状态是"+observerState);
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getObserverState() {
		return observerState;
	}
	public void setObserverState(String observerState) {
		this.observerState = observerState;
	}
	public ConcreteSubject getConcreteSubject() {
		return concreteSubject;
	}
	public void setConcreteSubject(ConcreteSubject concreteSubject) {
		this.concreteSubject = concreteSubject;
	}
	
	
	
	
	
}


interface Observer{
	
	public void update();
	
	
}
