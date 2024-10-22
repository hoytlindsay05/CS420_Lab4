import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class VectorClockImpl extends UnicastRemoteObject implements VectorClock, Serializable {
    private static final long serialVersionUID = 1L;
    private final int[] clock;

    public VectorClockImpl(int numProcesses) throws RemoteException {
        this.clock = new int[numProcesses];
    }

    @Override
    public synchronized void increment(int processId) throws RemoteException {
        clock[processId]++;
    }

    @Override
    public synchronized void update(VectorClock receivedClock) throws RemoteException {
        for (int i = 0; i < clock.length; i++) {
            clock[i] = Math.max(clock[i], receivedClock.getTime(i));
        }
    }

    @Override
    public int getTime(int processId) throws RemoteException {
        return clock[processId];
    }

    @Override
    public int[] getClock() throws RemoteException {
        return clock.clone();  // Return a copy of the clock array
    }
}
