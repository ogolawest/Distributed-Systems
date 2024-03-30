import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    private final String host;
    private final int port;


    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws UnknownHostException, IOException {
        try (
        Socket socket = new Socket(host, port);
        InputStream input = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true)
    ) {
            Scanner scanner = new Scanner(System.in);
            String message;

            while (true) {
                System.out.print("Enter a message: ");
                message = scanner.nextLine();

                writer.println(message);
                System.out.println("Echo: " + reader.readLine());
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int port = 8080;
        new Client(host, port).start();
    }
}
