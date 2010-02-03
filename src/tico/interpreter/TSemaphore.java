package tico.interpreter;

public class TSemaphore {
	
	private int value;
	
	public TSemaphore (int initial){
		value = initial;
	}
	
	synchronized public void release()
		throws InterruptedException{
			while (value==1) wait();
			++value;
			notify();
	}

	synchronized public void acquire()
		throws InterruptedException{
			while (value==0) wait();
			--value;
			notify();
	}
	
	synchronized public void releaseWhenStop()
	throws InterruptedException{
		if (value==0)
			value=1;
}
}
