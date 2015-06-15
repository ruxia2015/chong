package model;

import java.util.Vector;

public class IteratorMain {

	public static void main(String[] args) {
		
		final Aggregat agg = new ConcreteAggregat();
        final Iterator iterator = agg.createIterator();
        System.out.println(iterator.first());
        while (!iterator.isDone())
        {
            System.out.println(iterator.next());
        }

	}
}

/**
 * 提供一种方法顺序访问一个聚合对象中的各个元素，而不需要暴露该对象的内部表示
 * 
 */
class ConcreteAggregat implements Aggregat{
	private Vector vector =null;	
	
	
    public ConcreteAggregat()
    {
        vector = new Vector();
        vector.add("vector 1");
        vector.add("vector 2");
    }
	
	@Override
	public Iterator  createIterator() {		
        
        return new ConcreteIterator(vector);
        
	}
	public Vector getVector() {
		return vector;
	}
	public void setVector(Vector vector) {
		this.vector = vector;
	}
	
	
}

interface Aggregat{
	public Iterator createIterator();
}




class ConcreteIterator implements Iterator {

	private int currentIndex = 0;
	private Vector vector = null;
	
	
	public ConcreteIterator(final Vector vector){
		this.vector = vector;
	}
	
	
	@Override
	public Object first() {
		currentIndex = 0 ;
		return vector.get(currentIndex);
	}
	@Override
	public Object next() {
		currentIndex++;
		return vector.get(currentIndex);
	}
	@Override
	public Object currentItem() {
		// TODO Auto-generated method stub
		return vector.get(currentIndex);
	}
	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return currentIndex <= this.vector.size()-1 ? true : false;
	}

	

}

interface Iterator {

	public Object first();

	public Object next();

	public Object currentItem();

	public boolean isDone();
}
