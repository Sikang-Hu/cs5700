
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by Sikang on 2019-09-23. This is a server that can be used to process the message sent
 * by the client who connected to it.
 */
public class TCPServer {

  protected Socket socket;
  protected StringService service;

  public void setSocket(Socket socket) {
    this.socket = socket;
  }

  public void setService(StringService service) {
    this.service = service;
  }

  /**
   * Execute the business logic, processing the string received and sent it back to the client.
   */
  public void execute() {
    try (DataInputStream dis = new DataInputStream(socket.getInputStream());
         DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {
      String msg = dis.readUTF();
      dos.writeUTF(this.service.convert(msg));
    } catch (IOException e) {
      System.out.printf("Error happened while reading or writing: %s\n", e.getMessage());
    }
  }

  public static void main(String... args) {
    // Register service on port 32000
    if (args.length < 1) {
      System.out.println("Please specified the port by <port>");
      System.exit(1);
    }

    // parse the argument
    int port = 0;
    try {
      port = Integer.parseInt(args[0]);
    } catch (NumberFormatException e) {
      System.out.printf("In valid port number: %s", args[0]);
      System.exit(1);
    }

    try (ServerSocket s = new ServerSocket(port);
         Socket s1 = s.accept()) {
      // Initialize a new server socket
      TCPServer server = new TCPServer();
      server.setSocket(s1);
      server.setService(new ToggleService());
      server.execute();
      s1.close();
      s.close();
      System.out.println("Server closed...");
    }  catch (SocketException e) {
      System.out.printf("Socket error: %s\n", e.getMessage());
    } catch (IOException e) {
      System.out.printf("Encountered I/O problem: %s\n", e.getMessage());
    }


  }


}
