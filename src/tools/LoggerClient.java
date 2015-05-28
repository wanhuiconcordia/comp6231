package tools;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class LoggerClient {
	private Socket clientSocket;
	private DataOutputStream dataOutputStream;
	public LoggerClient() throws UnknownHostException, IOException {
		ConfigureManager.getInstance().loadFile("./settings/logger_client_settings.conf");
		clientSocket = new Socket(ConfigureManager.getInstance().getString("loggerServerName", "localhost"),
				ConfigureManager.getInstance().getInt("loggerServerPort", 2020));
		OutputStream outputStream = clientSocket.getOutputStream();
		dataOutputStream = new DataOutputStream(outputStream);
	}
	
	public boolean write(String msg){
		try {
			dataOutputStream.writeUTF(msg);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
