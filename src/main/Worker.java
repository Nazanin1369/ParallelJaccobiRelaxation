package main;
import static main.Application.SIZE;
import static main.Application.NUM_ITER;
import static main.Application.NUM_PROC;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Worker implements Runnable
{
	private double A[][],B[][];
	private int start_row;
	private int end_row;
	private final CyclicBarrier barrier;
	
	public Worker(double A[][],double B[][], int start_row, int end_row, CyclicBarrier barrier)
	{
		this.A = A;
		this.B = B;
		this.start_row = start_row;
		this.end_row = end_row;
		this.barrier = barrier;
	}

	@Override
	public void run() 
	{
		double temp;
		for(int k = 0; k < NUM_ITER; k++ )
		{
			for(int i = start_row; i <= end_row; i++)
			{
				for(int j = 1; j < SIZE - 2; j++)
				{
					
					temp = (A[i - 1][j] + A[i][j - 1] + A[i + 1][j] + A[i][j + 1]) / 4;
					B[i][j] = temp;
				}
			}
			//Barrier
			try
			{
			barrier.await();
			}
			catch(InterruptedException e1)
			{
				e1.printStackTrace();
			}
			catch(BrokenBarrierException e2)
			{
				e2.printStackTrace();
			}
			
			for(int i = 0; i < SIZE; i++)
			{
				for(int j = 0; j < SIZE; j++)
				{
					A[i][j] = B[i][j];
				}
			}
			
			
			//Barrier
			try
			{
			barrier.await();
			}
			catch(InterruptedException e1)
			{
				e1.printStackTrace();
			}
			catch(BrokenBarrierException e2)
			{
				e2.printStackTrace();
			}
			
		}
		
		
	}

	
}
