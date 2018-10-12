package ch.herzog.fractalfx.concurrent;

import javafx.concurrent.Task;

public abstract class ProfiledTask<T> extends Task<T> {

    private long start;
    private long end;

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getExecutionTime() {
        return end - start;
    }

}
