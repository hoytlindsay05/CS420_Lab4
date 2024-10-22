import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Process extends Remote {
    void requestCriticalSection() throws RemoteException;
    void releaseCriticalSection() throws RemoteException;
    void receiveRequest(int requestingProcessId, VectorClock requestingClock) throws RemoteException;
}