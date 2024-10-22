import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VectorClock extends Remote {
    void increment(int processId) throws RemoteException;
    void update(VectorClock receivedClock) throws RemoteException;
    int getTime(int processId) throws RemoteException;
    int[] getClock() throws RemoteException; 
}
