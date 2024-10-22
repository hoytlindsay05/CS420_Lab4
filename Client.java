import java.rmi.Naming;

public class Client {
    public static void main(String[] args) {
        try {
            for (int i = 0; i < 3; i++) { // Assuming 3 processes
                Process remoteProcess = (Process) Naming.lookup("rmi://localhost/Process" + i);
                System.out.println("Found Process" + i + ". Requesting critical section...");
                remoteProcess.requestCriticalSection();
            }

            Thread.sleep(2000);

            // Notify that all requests are processed
            System.out.println("All requests processed. Client terminating.");
        } catch (Exception e) {
            System.out.println("Client exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
