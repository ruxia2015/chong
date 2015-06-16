package model.Structural;


import java.util.ArrayList;
import java.util.List;

import com.chong.common.util.StringTools;

public class CompositeMain {
	
	public static void main(String[] args) {
		Composite root = new Composite("根");
		root.add(new Leaf("叶子A"));
		root.add(new Leaf("叶子B"));
		
		Composite com = new Composite("枝桠X");
		com.add(new Leaf("叶子XA"));
		com.add(new Leaf("鸭子XB"));
		root.add(com);
		
		
		Composite comY = new Composite("枝桠XY");
		comY.add(new Leaf("叶子XYA"));
		comY.add(new Leaf("鸭子XYB"));
		com.add(comY);
		
		root.add(new Leaf("叶子C"));
		root.add(new Leaf("叶子D"));
		
		root.display(0);
		
	}

}



abstract class Component_C{
	protected String name;
	Component_C(String name){
		this.name = name;
	}
	abstract void add(Component_C c);
	abstract void remove(Component_C c);
	abstract void display(int depth);
	
}

class Leaf extends Component_C{

	Leaf(String name) {
		super(name);
	}

	@Override
	public void add(Component_C c) {
		System.out.println("不能添加");
		
	}

	@Override
	public void remove(Component_C c) {
		System.out.println("不能删除");
		
	}

	@Override
	public void display(int depth) {
		System.out.println(StringRepeat.repeat(depth, "-")+name);
		
	}
	
}

/**
 * 组合模式
 * 
 * 将对象组合成树形结构以表示‘部分-整体’的层次结构，组合模式使得用户对单个对象和组合对象的使用具有一致性。
 *
 */

class Composite extends Component_C{
	private List<Component_C> children = new ArrayList<Component_C>();

	Composite(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void add(Component_C c) {
		children.add(c);
		
	}

	@Override
	public void remove(Component_C c) {
		children.remove(c);
		
	}

	@Override
	public void display(int depth) {
		System.out.println( StringRepeat.repeat(depth, "-")+name);
		
		for (Component_C c : children) {
			c.display(depth+3);
			
		}
	}
	
}

class StringRepeat{
	
	static String repeat(int num,String str){
		String res = "";
		for(int i=0;i<num;i++){
			res = res +str;
		}
		
		return res;
	}
}

