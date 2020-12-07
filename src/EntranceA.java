import java.io.*;
import java.net.*;

public class EntranceA 
{

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException 
	{

		// set up the socket and the in and out variables
		Socket parkingServerSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		int portNumber = 4444;
		int carsWaiting = 0;

		//connect to the server and set the input and output variables
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
				"The Entrance-A has succesfully initialised client and IO connections to the parking on the socket "
						+ portNumber);
		System.out.println("REPRESENT A CAR ARRIVING ON ENTRANCE-A BY TYPING IN THE CONSOLE THE LETTER 'a'");

		while (true) 
		{
			if (stdIN.readLine().equalsIgnoreCase("a")) 
			{
				carsWaiting++;
				System.out.println("Entrance-A : A car has arrived");
			} else
			{
				System.out.println("CAR ARRIVAL IS ONLY REPRESENTED BY LETTER 'a'");
			}
			
			// if there is at least one car waiting
			while (carsWaiting > 0) 
			{
				out.println("A car wishes to enter");
				parkingResponse = in.readLine();
				
				if (parkingResponse.equalsIgnoreCase("The car has entered the parking")) 
				{
					carsWaiting--;
					System.out.println("Entrance-A : A car has entered the parking and there is/are " + carsWaiting
							+ " cars waiting to enter");
				} else if (parkingResponse.equalsIgnoreCase("There is no space")) 
				{
					System.out.println("Entrance-A : There is no space in the parking, and there is/are " + carsWaiting
							+ " cars waiting to enter");
				}
				
				//Even if there are cars waiting, a new car can enter the queue. 
				if (stdIN.readLine().equalsIgnoreCase("a"))
				{
					carsWaiting++;
					System.out.println("Entrance-A : A car has arrived");
				}else 
				{
					System.out.println("CAR ARRIVAL IS REPRESENTED BY LETTER 'a'");// remove later?
				}
			}
		}
	}

}
