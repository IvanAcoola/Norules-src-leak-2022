package net.minecraftforge.utils;

public class TimerUtil
{

    private long lastMS = 0L;
    private long prevMS = 0L;
    private long ms = this.getCurrentMS();

    public boolean isDelay(long delay)
    {
        if (System.currentTimeMillis() - lastMS >= delay)
        {
            return true;
        }
        return false;
    }

    public final long getElapsedTime() {
        return this.getCurrentMS() - this.ms;
    }

    public final boolean elapsed(long milliseconds) {
        return this.getCurrentMS() - this.ms > milliseconds;
    }

    public long getCurrentMS()
    {
        return System.nanoTime() / 1000000L;
    }

    public void setLastMS(long lastMS)
    {
        this.lastMS = lastMS;
    }

    public void setLastMS()
    {
        this.lastMS = System.currentTimeMillis();
    }

    public int convertToMS(int d)
    {
        return 1000 /d;
    }

    public boolean hasReached(float f)
    {
        return (float) (getCurrentMS() - this.lastMS) >= f;
    }

    public void reset()
    {
        this.lastMS = getCurrentMS();
    }

    public boolean delay(float milliSec)
    {
        return (float)(getTime() - this.prevMS) >= milliSec;
    }

    public long getTime()
    {
        return System.nanoTime() / 1000000L;
    }


    private long time = -1L;

    public boolean hasTimeElapsed(long time, boolean reset) {
        if (System.currentTimeMillis() - this.lastMS > time) {
            if (reset) {
                this.reset();
            }
            return true;
        }
        return false;
    }

    public void resetTimers()
    {
        this.time = System.currentTimeMillis();
    }

    public long getTimerTime()
    {
        return this.time;
    }

    public void setTime(long time)
    {
        this.time = time;
    }
}
