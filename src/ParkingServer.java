import java.io.*;
import java.net.*;

public class ParkingServer 
{

	public static void main(String[] args) throws IOException 
	{
		
		// set up the server socket on port 4444
		ServerSocket serverSocket = null;
		boolean listening = true;
		int spacesAvailable = 5;
		
		try {
			serverSocket = new ServerSocket(4444);
		} catch (IOException e) {
			System.err.println("Could not listen on port: 4444");
			System.exit(-1);
		}
		
		System.out.println("The parking management server is up and waiting");
		
		//Create the shared object that allows the controlled reading and writting
		SharedParkingSpace sharedParkingSpace = new SharedParkingSpace(spacesAvailable);
		
		// when client connects, make the link
		while(listening)
		{
			new ParkingServerThread(serverSocket.accept(),"Entrance A ", sharedParkingSpace).start();
			new ParkingServerThread(serverSocket.accept(),"Entrance B " ,sharedParkingSpace).start();
			new ParkingServerThread(serverSocket.accept(),"Exit A " ,sharedParkingSpace).start();
			new ParkingServerThread(serverSocket.accept(),"Exit B " ,sharedParkingSpace).start();
		}
		
		serverSocket.close();
	}
}
