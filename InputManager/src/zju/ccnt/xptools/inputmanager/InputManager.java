package zju.ccnt.xptools.inputmanager;

public class InputManager {
	public InputReader inputReader;
	public InputThread inputThread;
	
	public InputManager(){
		inputReader=new InputReader();
		inputThread=new InputThread(inputReader);
	}
	
	public void start(){
		inputThread.start();
	}
}
