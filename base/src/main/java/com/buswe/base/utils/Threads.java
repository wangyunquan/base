package com.buswe.base.utils;

import java.io.PrintStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Threads
{
  public static void sleep(long durationMillis)
  {
    try
    {
      Thread.sleep(durationMillis);
    }
    catch (InterruptedException e)
    {
      Thread.currentThread().interrupt();
    }
  }
  
  public static void sleep(long duration, TimeUnit unit)
  {
    try
    {
      Thread.sleep(unit.toMillis(duration));
    }
    catch (InterruptedException e)
    {
      Thread.currentThread().interrupt();
    }
  }
  
  public static void gracefulShutdown(ExecutorService pool, int shutdownTimeout, int shutdownNowTimeout, TimeUnit timeUnit)
  {
    pool.shutdown();
    try
    {
      if (!pool.awaitTermination(shutdownTimeout, timeUnit))
      {
        pool.shutdownNow();
        if (!pool.awaitTermination(shutdownNowTimeout, timeUnit)) {
          System.err.println("Pool did not terminated");
        }
      }
    }
    catch (InterruptedException ie)
    {
      pool.shutdownNow();
      
      Thread.currentThread().interrupt();
    }
  }
  
  public static void normalShutdown(ExecutorService pool, int timeout, TimeUnit timeUnit)
  {
    try
    {
      pool.shutdownNow();
      if (!pool.awaitTermination(timeout, timeUnit)) {
        System.err.println("Pool did not terminated");
      }
    }
    catch (InterruptedException ie)
    {
      Thread.currentThread().interrupt();
    }
  }
  
  public static ExecutorService newFixedThreadPool(int threadSize)
  {
    return new ThreadPoolExecutor(threadSize, threadSize, 0L, TimeUnit.MILLISECONDS, 
      new LinkedBlockingQueue(1)
      {
        private static final long serialVersionUID = -9028058603126367678L;
        
        public boolean offer(Runnable e)
        {
          try
          {
            put(e);
            return true;
          }
          catch (InterruptedException ie)
          {
            Thread.currentThread().interrupt();
          }
          return false;
        }
      });
  }
  
  public static class WrapExceptionRunnable
    implements Runnable
  {
    private static Logger logger = LoggerFactory.getLogger(WrapExceptionRunnable.class);
    private Runnable runnable;
    
    public WrapExceptionRunnable(Runnable runnable)
    {
      this.runnable = runnable;
    }
    
    public void run()
    {
      try
      {
        this.runnable.run();
      }
      catch (Exception e)
      {
        logger.error("Unexpected error occurred in task", e);
      }
    }
  }
}
