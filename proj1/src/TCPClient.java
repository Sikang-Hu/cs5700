import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Sikang on 2019-09-23. This is a class represent a TCPClient, it can send a message to
 * Specified destination and print out the response.
 */
public class TCPClient {

  public static void main(String... args) {
    // Parse the arguments
    if (args.length < 2) {
      System.out.println("Please specified the host and the port by <host> <port>");
      System.exit(1);
    }
    String host = args[0];
    int port = 0;
    try {
      port = Integer.parseInt(args[1]);
    } catch (NumberFormatException e) {
      System.out.printf("In valid port number: %s", args[1]);
      System.exit(1);
    }
    // Initialize a socket
    System.out.println("Connecting...");

    try (Socket s1 = new Socket(host, port)) {
      System.out.println("Succeeded!");

      // Accept input from the user
      System.out.println("Enter text:");
      Scanner s = new Scanner(System.in);

      // Send the input text through socket
      OutputStream s1Out = s1.getOutputStream();
      DataOutputStream dos = new DataOutputStream(s1Out);
      dos.writeUTF(s.nextLine());

      // Get the response from the socket
      InputStream s1In = s1.getInputStream();
      DataInputStream dis = new DataInputStream(s1In);
      System.out.println("Response from server:");

      // print out the response
      System.out.println(dis.readUTF());
      dos.close();
      dis.close();
    } catch (IOException e) {
      System.out.printf("Failed to get the response from the server: %s\n", e.getMessage());
    }
  }


}
