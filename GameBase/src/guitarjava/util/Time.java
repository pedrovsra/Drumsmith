package guitarjava.util;

/**
 * Util class used for time operations.
 * @author Lucas
 */
public class Time
{
    /**
     * Stop the execution for a certain period of time.
     * @param time Time to wait in milliseconds.
     */
    public static void waitSomeTime(int time)
    {
        try
        {
            Thread.sleep(time);
        }
        catch (InterruptedException e)
        {
            // Do nothing.
        }
    }
}
