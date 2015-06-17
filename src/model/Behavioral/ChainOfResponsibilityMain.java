package model.Behavioral;

public class ChainOfResponsibilityMain {
	public static void main(String[] args) {
		
		CommonManager jinli = new CommonManager("经理");
		Majordomo zongjian = new Majordomo("总监");
		GeneralManager zhongjinli = new GeneralManager("总经理");
		
		jinli.setSuperior(zongjian);
		zongjian.setSuperior(zhongjinli);
		
		
		Request request = new Request();
		request.requestType = "请假";
		request.requestContent = "小才请假";
		request.number = 1;
		jinli.requestApplication(request);
		
		Request request2 = new Request();
		request2.requestType = "请假";
		request2.requestContent = "小才请假";
		request2.number = 4;
		jinli.requestApplication(request2);
		
		Request request3 = new Request();
		request3.requestType = "加薪";
		request3.requestContent = "小才请求请假";
		request3.number = 500;
		jinli.requestApplication(request3);
		
		
		Request request4 = new Request();
		request4.requestType = "加薪";
		request4.requestContent = "小才请求请假";
		request4.number = 1000;
		jinli.requestApplication(request4);
		
		Request request5 = new Request();
		request5.requestType = "病假";
		request5.requestContent = "要请病假";
		request5.number = 30;
		jinli.requestApplication(request5);
		
		
		
	}

}

/**
 * 职责链模式
 * 
 * 使多个对象都有机会处理请求，从而避免请求的发送者和接收者之间的耦合关系。
 * 将这些对象连接成一条链，并沿着这条链传递该请求，直到有一个对象处理他为止。
 * 
 */

//请假
abstract class Manager{
	protected String name;
	protected Manager superior;
	
	public Manager(String name) {
		this.name = name;
	}

	public void setSuperior(Manager superior) {
		this.superior = superior;
	}
	
	
	abstract void requestApplication(Request request);
	
}

class CommonManager extends Manager{
	
	

	public CommonManager(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	void requestApplication(Request request) {
		if("请假".equals(request.requestType) && request.number <=2){
			System.out.println(name + ":" + request.requestContent + "数量" +request.number + "被批准");
		}else{
			if(superior!=null){
				superior.requestApplication(request);
			}
		}
		
	}

}


class Majordomo extends Manager{

	public Majordomo(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	void requestApplication(Request request) {

		if("请假".equals(request.requestType) && request.number <=5){
			System.out.println(name + ":" + request.requestContent + "数量" +request.number + "被批准");
		}else{
			if(superior!=null){
				superior.requestApplication(request);
			}
		}
		
	
		
	}
	
}

class GeneralManager extends Manager{

	public GeneralManager(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	void requestApplication(Request request) {
		if("请假".equals(request.requestType)){
			System.out.println(name + ":" + request.requestContent + "数量" +request.number + "被批准");
		}else if("加薪".equals(request.requestType) && request.number <= 500){
			System.out.println(name + ":" + request.requestContent + "数量" +request.number + "被批准");
		}else if("加薪".equals(request.requestType) && request.number >500){
			System.out.println(name + ":" + request.requestContent + "数量" +request.number + "再说吧");	
			
		}else{
			System.out.println(name + ":" + request.requestContent + "数量" +request.number + "不被批准");
		}
		
	}
	
}


class Request{
	String requestType ;
	String requestContent;
	int number;
	
}