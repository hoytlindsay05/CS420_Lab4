import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ProcessImpl extends UnicastRemoteObject implements Process {
    private static final long serialVersionUID = 1L;
    private final int processId;
    private final VectorClock vectorClock;
    private final List<Process> processes;
    private boolean inCriticalSection = false; // Flag to track if in critical section

    public ProcessImpl(int id, List<Process> processes, int numProcesses) throws RemoteException {
        this.processId = id;
        this.vectorClock = new VectorClockImpl(numProcesses);
        this.processes = processes;
    }

    @Override
    public synchronized void requestCriticalSection() throws RemoteException {
        vectorClock.increment(processId);
        System.out.println("Process " + processId + " is requesting the critical section.");

        // Send requests to all other processes in the quorum (in this case, all processes)
        for (Process remoteProcess : processes) {
            remoteProcess.receiveRequest(processId, vectorClock);
        }
    }

    @Override
    public synchronized void receiveRequest(int requestingProcessId, VectorClock requestingClock) throws RemoteException {
        vectorClock.update(requestingClock);
        System.out.println("Process " + processId + " received request from " + requestingProcessId);

        // Grant access if this process can
        grantAccess(requestingProcessId);
    }

    private void grantAccess(int requestingProcessId) throws RemoteException {
        // Granting logic here; you may modify this based on your requirements
        System.out.println("Process " + processId + " is granting access to Process " + requestingProcessId);
        // Simulate entering the critical section
        inCriticalSection = true;
        System.out.println("Process " + requestingProcessId + " has entered the critical section.");
        // Simulate critical section work
        try {
            Thread.sleep(1000); // Simulate time spent in critical section
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        inCriticalSection = false;
        releaseCriticalSection(); // Notify that the critical section is released
    }

    @Override
    public synchronized void releaseCriticalSection() throws RemoteException {
        System.out.println("Process " + processId + " is releasing the critical section.");
        // Logic to notify other processes that the critical section is free
    }
}
