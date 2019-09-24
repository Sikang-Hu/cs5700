import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Random;

/**
 * Created by Sikang on 2019-09-23.
 */
public class UDPServer {

  /**
   * This is to assign a possibility for simulating package losing, from 0 to 10. The probability
   * can be calculated as (10 - THRESHOLD)  / 10.
   */
  private static final int THRESHOLD = 2;

  public static void main(String... args) {
    DatagramSocket aSocket = null;
    if (args.length < 1) {
      System.out.println("Please specified the port by <port>");
      System.exit(1);
    }
    try {
      // parse the port number
      int port = Integer.parseInt(args[0]);
      // Initialize a socket
      aSocket = new DatagramSocket(port);
      byte[] buf = new byte[1000];
      Random r = new Random(System.currentTimeMillis());
      while (true) {
        DatagramPacket req = new DatagramPacket(buf, buf.length);
        // receiving the packet
        aSocket.receive(req);
        // send the packet back as origin if it is not "lost"
        if (r.nextInt(10) >= THRESHOLD) {
          DatagramPacket res = new DatagramPacket(req.getData(), req.getLength()
                  , req.getAddress(), req.getPort());
          aSocket.send(res);
        }
      }
    } catch (NumberFormatException e) {
      System.out.printf("Invalid port number: %s\n", args[0]);
    } catch (IOException e) {
      System.out.printf("Encountered I/O problem: %s\n", e.getMessage());
    } finally {
      if (aSocket != null) {
        aSocket.close();
      }
    }
  }


}
