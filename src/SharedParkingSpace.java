
public class SharedParkingSpace 
{
	private int sharedVariable;
	private boolean accessing = false; // true a thread has a lock, false otherwise

	// Constructor
	public SharedParkingSpace(int spacesAvailable) 
	{
		sharedVariable = spacesAvailable;
	}

	// Attempt to acquire a lock
	public synchronized void acquiredLock() throws InterruptedException 
	{
		Thread meThread = Thread.currentThread();
		
		// while someone else is accessing or threads waiting >0
		while (accessing) 
		{
			System.out.println(meThread.getName() + " waiting to get a lock as someone else is accessing...");
			// wait for the lock to be released
			wait();
		}
		// nobody has got a lock so get one
		accessing = true;
		System.out.println(meThread.getName() + " got a lock");
	}

	// Releases a lock when a thread is finished
	public synchronized void releaseLock() 
	{
		// release the lock and tell everyone
		accessing = false;
		notifyAll();
		Thread meThread = Thread.currentThread();
		System.out.println(meThread.getName() + " released a lock");
	}

	// Process the input and the output to the client
	public synchronized String processInput(String theInput) 
	{
		Thread meThread = Thread.currentThread();
		String theOutput = null;
		
		if (theInput.equalsIgnoreCase("A car wishes to enter")) 
		{
			if (sharedVariable > 0) 
			{
				theOutput = "The car has entered the parking";
				sharedVariable--;
				System.out.println(meThread.getName() + " entered a car, and now there are " + sharedVariable + " spaces");
			} else 
			{
				theOutput = "There is no space";
				System.out.println(meThread.getName() + " wanted to enter a car but there is no space");
			}
		} else if (theInput.equalsIgnoreCase("A car has left")) 
		{
			if (sharedVariable == 5) 
			{
				theOutput = "The parking is already empty";
				System.out.println(meThread.getName() + " said that a car left but there are no cars in the parking");
			} else 
			{
				notifyAll();
				sharedVariable++;
				theOutput = "A car has left the parking and there are now " + sharedVariable + " spaces available";
				System.out.println(meThread.getName() + " said that a car left and there are now " + sharedVariable + " spaces available");
			}
		}
		return theOutput;
	}

}
