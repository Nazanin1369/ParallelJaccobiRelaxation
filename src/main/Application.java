package main;

import java.util.concurrent.CyclicBarrier;

public class Application 
{
	public static final int SIZE = 34;
	public static double A[][] = new double[SIZE][SIZE];
	public static double B[][] = new double[SIZE][SIZE];
	public static final int NUM_ITER = 1000;
	public static final int NUM_PROC = Runtime.getRuntime().availableProcessors();
	
	public static void main(String[] args)
	{
		/* Initialize */
		Application app = new Application();
		Thread[] threads = new Thread[NUM_PROC];
		CyclicBarrier barrier = new CyclicBarrier(NUM_PROC);
		int portion =  SIZE / NUM_PROC;
	
		for( int i = 0; i < SIZE; i++)
		{
			for(int j =0; j < SIZE; j++)
			{
				A[i][j] = 0;
			}
		}
		
		for(int i =0; i < SIZE; i++)
		{
			A[i][0] = 10; 
			A[0][i] = 10; 
			A[i][SIZE-1] = 10; 
			A[SIZE-1][i] = 10;
		}
		
		for(int i = 0; i < SIZE; i++)
		{
			for(int j = 0; j < SIZE; j++)
			{
				B[i][j] = A[i][j];
			}
		}
		
		/*Parallel*/
		for(int i = 0; i < NUM_PROC; i++)
		{
			int start = i * portion + 1;
			int end = start + portion - 1;
			System.out.println("start : " + start +" end: " + end);
			threads[i] = new Thread(new Worker(A, B, start, end, barrier));
		}
		
		
		
		long par_start = System.nanoTime();
		for(int i = 0; i < NUM_PROC; i++)
		{
			threads[i].start();
		}
		
		for(int i = 0; i < NUM_PROC; i++)
		{
			try
			{
				threads[i].join();
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		
		long par_end = System.nanoTime();
		long par_time = (par_end - par_start);
		System.out.print("Parallel Elapsed Time: ");
		System.out.println((par_time / Math.pow(10,6)));
		
		app.printArray(A);
		System.out.println("\n-----------------");
		app.printArray(B);
	
	}// End of main
	
	public void printArray(double A[][])
	{
		for(int i = 0; i < SIZE; i++)
		{
			System.out.println();
			for(int j = 0; j < SIZE; j++)
			{
				System.out.print(A[i][j] + " "); 
			}
		}
	}

}
