package model.Behavioral;

public class InterpreterMain {
	public static void main(String[] args) {
		
		PlayContext context = new PlayContext();
		context.playText = " O 2 E 0.5 G 0.5 A 3 E 0.5 A 3 E 0.5 G 0.5 D 3 E 0.5 G 0.5 A 0.5 O 3 C 1 O 2 A 0.5 G 0.5 G 1 C 0.5 E 0.5 D 3 ";
		
		Expression expression =  new Scale();;
		try{
			while(context.playText.length()>0){
				String str = context.playText.substring(0,1);
				
				switch (str) {
				case "O":
					expression = new Scale();					
					break;
					
				case "C":
				case "D":
				case "E":
				case "F":
				case "G":
				case "A":
				case "B":
				case "P":
					expression = new Note();
					break;

				}
				expression.interpret(context);
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}

/**
 * 解释器
 * 
 * 给定一个语言，定义它的文法的一种表示，并定义一个解释器，这个解释器使用该表示该解释语言中的句子。
 * 
 */

//乐谱
class PlayContext {
	public String playText;
}

abstract class Expression {
	public void interpret(PlayContext context) {
		if (context.playText.length() == 0) {
			return;
		} else {
			String playKey = context.playText.substring(0, 1);
			context.playText = context.playText.substring(2);
			String playValue = context.playText.substring(0,
					context.playText.indexOf(" ",1)).trim();
			context.playText = context.playText.substring(
					context.playText.indexOf(" ",1) + 1);
			
			//System.out.println(" playValue " +playValue + "  "+context.playText)  ;
			execute(playKey, Double.parseDouble(playValue));
		}
	}

	public abstract void execute(String key, double value);
}

//音符类
class Note extends Expression {

	@Override
	public void execute(String key, double value) {
		String note = "";

		switch (key) {
		case "c":
			note = "1";
			break;
		case "D":
			note = "2";
			break;	
		case "E":
			note = "3";
			break;
		case "F":
			note = "4";
			break;
		case "G":
			note = "5";
			break;
		case "A":
			note = "6";
			break;
		case "B":
			note = "7";
			break;
	    default:
			note = "1";
			break;

		}
		System.out.print(note + " ");

	}

}

//音节
class Scale extends Expression{

	@Override
	public void execute(String key, double value) {
		String scale = "";
		switch ((int)value) {
		case  0:
		case  1:
			scale="低音";
			break;
		case 2:
			scale="中音";
			break;
		case 3:
		default:
			scale="高音";
			break;
				
		}
		
		System.out.print(scale + " "  );
		
	}
	
}



