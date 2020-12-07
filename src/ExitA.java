import java.io.*;
import java.net.*;

public class ExitA 
{
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException 
	{

		// set up the socket and the in and out variables
		Socket parkingServerSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		int portNumber = 4444;

		// connect to the server and set the input and output variables
		try {
			parkingServerSocket = new Socket("localhost", portNumber);
			out = new PrintWriter(parkingServerSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(parkingServerSocket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: localhost");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: " + portNumber);
			System.exit(1);
		}

		BufferedReader stdIN = new BufferedReader(new InputStreamReader(System.in));
		String parkingResponse;

		System.out.println(
				"The Exit A has succesfully initialised client and IO connections to the parking on the socket "
						+ portNumber);
		System.out.println("REPRESENT A CAR LEAVING EXIT-A BY TYPING IN THE CONSOLE THE LETTER 'l'");

		while (true) 
		{
			if (stdIN.readLine().equalsIgnoreCase("l")) 
			{
				out.println("A car has left");
				parkingResponse = in.readLine();
				System.out.println("Exit-A : " + parkingResponse);
			} else 
			{
				System.out.println("A CAR EXIT IS ONLY REPRESENTED BY LETTER 'l'");
			}
			
		}
	}

}