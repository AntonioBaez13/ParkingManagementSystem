import java.io.*;
import java.net.*;

public class ParkingServerThread extends Thread
{

	private Socket serverSocket = null;
	private SharedParkingSpace sharedParkingObject;
	private String parkingThreadName;
	
	//Set up thread
	 public ParkingServerThread(Socket serverSocket,String threadName, SharedParkingSpace sharedObject)
	 {
		 this.serverSocket = serverSocket;
		 parkingThreadName = threadName;
		 sharedParkingObject = sharedObject;
	 }
	 
	 //This is called when start is used in the calling method
	 public void run()
	 {
		 try{
			 System.out.println(parkingThreadName + "initialising");
			 currentThread().setName(parkingThreadName);
			 PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);
			 BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
			 String inputLine, outputLine;
			 
			 while((inputLine = in.readLine()) != null)
			 {
				 // Get a lock
				 try{
					 sharedParkingObject.acquiredLock();
					 outputLine = sharedParkingObject.processInput(inputLine);
					 out.println(outputLine);
					 sharedParkingObject.releaseLock();
				 }catch (InterruptedException e) {
					System.err.print("Failed to gt a lock when reading: " + e);
				}
			 }
			 
			 out.close();
			 in.close();
			 serverSocket.close();
		 }catch (IOException e) {

			 e.printStackTrace();
			
		 }
		 
	 }
	 
}
