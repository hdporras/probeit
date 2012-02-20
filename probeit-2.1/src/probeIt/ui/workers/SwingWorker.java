package probeIt.ui.workers;

import javax.swing.SwingUtilities;

/**
 * This is the 3rd version of SwingWorker (also known as SwingWorker 3), an
 * abstract class that you subclass to perform GUI-related work in a dedicated
 * thread. For instructions on and examples of using this class, see:
 * 
 * http://java.sun.com/docs/books/tutorial/uiswing/misc/threads.html
 * 
 * Note that the API changed slightly in the 3rd version: You must now invoke
 * start() on the SwingWorker after creating it.
 */
public abstract class SwingWorker
{
	private Object value; // see getValue(), setValue()

	/**
	 * Class to maintain reference to current worker thread under separate
	 * synchronization control.
	 */
	private static class ThreadVar
	{
		private Thread thread;
		ThreadVar(Thread t)
		{
			thread = t;
		}
		synchronized Thread get()
		{
			return thread;
		}
		synchronized void clear()
		{
			thread = null;
		}
	}

	private ThreadVar threadVar;

	/**
	 * Get the value produced by the worker thread, or null if it hasn't been
	 * constructed yet.
	 */
	protected synchronized Object getValue()
	{
		return value;
	}

	/**
	 * Set the value produced by worker thread
	 */
	private synchronized void setValue(Object x)
	{
		value = x;
	}

	/**
	 * Compute the value to be returned by the <code>get</code> method.
	 */
	public abstract Object construct();

	/**
	 * Called on the event dispatching thread (not on the worker thread) after
	 * the <code>construct</code> method has returned.
	 */
	public void finished()
	{
		System.out.println("FINISHED!! METHOD");
	}

	/**
	 * A new method that interrupts the worker thread. Call this method to force
	 * the worker to stop what it's doing.
	 */
	public void interrupt()
	{
		Thread t = threadVar.get();
		if (t != null)
		{
			t.interrupt();
		}
		threadVar.clear();
	}

	/**
	 * Return the value created by the <code>construct</code> method. Returns
	 * null if either the constructing thread or the current thread was
	 * interrupted before a value was produced.
	 * 
	 * @return the value created by the <code>construct</code> method
	 */
	public Object get()
	{
		while (true)
		{
			Thread t = threadVar.get();
			if (t == null)
			{
				return getValue();
			}
			try
			{
				t.join();
			} catch (InterruptedException e)
			{
				Thread.currentThread().interrupt(); // propagate
				return null;
			}
		}
	}

	/**
	 * Start a thread that will call the <code>construct</code> method and
	 * then exit.
	 */
	public SwingWorker()
	{
		final Runnable doFinished = new Runnable()
		{
			public void run()
			{
				System.out.println("about to call finish... (SwingWorker[runnable])");
				finished();
			}
		};

		Runnable doConstruct = new Runnable()
		{
			public void run()
			{
				try
				{
					System.out.println("Begin Construct (SwingWorker)");
					setValue(construct());
					System.out.println("End Construct (SwingWorker)");
				} finally
				{
					System.out.println("ThreadVar.clear (SwingWorker)");
					threadVar.clear();
					System.out.println("ThreadVar.clear (SwingWorker)");
				}

				System.out.println("Finished, call doFinished (SwingWorker[InvokeLater])");
				//SwingUtilities.invokeLater(doFinished);
				doFinished.run();
			/*	
				try{
					SwingUtilities.invokeAndWait(doFinished);
				}catch(Exception e)
				{
					e.printStackTrace();
					doFinished.run();
				}*/
				
				System.out.println("Done (SwingWorker[InvokeLater])");
			}
		};

		Thread t = new Thread(doConstruct);
		threadVar = new ThreadVar(t);
	}

	/**
	 * Start the worker thread.
	 */
	public void start()
	{
		Thread t = threadVar.get();
		if (t != null)
		{
			System.out.println("----------------------Thread starting-------------------------------------------------------");
			t.start();
			//System.out.println("----------------------Thread ended-------------------------------------------------------");
		}
	}
}
