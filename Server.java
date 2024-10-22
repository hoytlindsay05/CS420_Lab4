import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) {
        try {
            // Start RMI registry
            Registry registry = LocateRegistry.createRegistry(1099);
            System.out.println("RMI registry started on port 1099.");

            int numProcesses = 3;  // Example number of processes
            List<Process> processes = new ArrayList<>();

            for (int i = 0; i < numProcesses; i++) {
                ProcessImpl process = new ProcessImpl(i, processes, numProcesses);
                processes.add(process);
                registry.bind("Process" + i, process);
            }

            System.out.println("Processes bound to RMI registry and ready.");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
