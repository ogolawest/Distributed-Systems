import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server implements MessageHandler {
	private final int port;

	public Server(int port) {
 		this.port = port;
 	}

	public void start() throws IOException {
 		try (ServerSocket serverSocket = new ServerSocket(port)) {
 			System.out.println("Server started on port " + port);

 			while (true) {
 				Socket clientSocket = serverSocket.accept();
 				System.out.println("New client connected");

 				new Thread(()->handleConnection(clientSocket)).start();
 			}
 		}
 	}


	
	private void handleConnection(Socket socket) {
 		try (
		InputStream input = socket.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		OutputStream output = socket.getOutputStream();
		PrintWriter writer = new PrintWriter(output, true)
		) {
				String message;
				while ((message = reader.readLine()) != null) {
					System.out.println("Received message: " + message);
					handleMessage(message);
					writer.println(message);
				}
			} catch (IOException e) {
				 System.err.println("Error handling connection" + e.getMessage());
 		}
	 }

	@Override
 	public void handleMessage(String message) {
 		// Implement your business logic here
 		System.out.println("Handling message: " + message);
 	}

	public static void main(String[] args) throws IOException {
 		int port = 8080;
 		new Server(port).start();
 	}
}
