/**
 * A few simple utilities
 *
 * @author Amon Seagull
 * @version 1.0
 */
public class Utilities
{
    /**
     * Returns a random integer in the range [0,max)
     * @param max The upper bound of the range from which the random
     * integer is drawn.
     */
    public static int randInt(int max){
        return (int)(Math.random() * max);
    }

    /**
     * Pauses the current thread for the specified number of seconds
     * @param nsecs The number of second for which to pause.
     */
    public static void pause (double nsecs){
        try {
            Thread.sleep((long)(1000*nsecs));
        } catch (InterruptedException e){
            // if we weren't allow to finish sleeping, we'll just stop
            // pausing prematurely
            return;
        }
    }

}
