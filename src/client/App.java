package client;
import java.io.IOException;

import tools.ConfigureManager;
import tools.LoggerClient;


public class App {
	public static void main(String []args){
		try {
			LoggerClient loggerClient = new LoggerClient();
			for(int i = 0; i< 10; i++){
				loggerClient.write("hello world.");
				Thread.sleep(500);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
