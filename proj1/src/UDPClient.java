import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Sikang on 2019-09-23.
 */
public class UDPClient {


  protected DatagramSocket socket;
  protected ArrayList<Long> interval = new ArrayList<>(TIMES);
  protected static final int TIMES = 10;
  protected static final int TIMEOUT = 1000;


  private void setSocket(DatagramSocket socket) {
    this.socket = socket;
  }

  /**
   * Send out 10 ping message to server at aHost: port, and for each of them, record the receiving
   * time and calculate the RTT
   */
  private void ping(InetAddress aHost, int port) throws IOException {
    this.socket.setSoTimeout(TIMEOUT);
    for (int i = 0; i < TIMES; i++) {
      String t = Long.toString(System.currentTimeMillis());
      byte[] m = t.getBytes();
      DatagramPacket req = new DatagramPacket(m, m.length, aHost, port);
      this.socket.send(req);
      byte[] r = new byte[1000];
      DatagramPacket res = new DatagramPacket(r, r.length);
      try {
        this.socket.receive(res);
      } catch (SocketTimeoutException e) {
        continue;
      }
      long end = System.currentTimeMillis();
      try {
        long start = Long.parseLong(new String(res.getData(), 0, res.getLength()));
        interval.add(end - start);
      } catch (NumberFormatException e) {
        //
      }
    }
  }

  /**
   * For each of the packet print our the time elapsed from sending to receiving, report Lost if not
   * received.
   */
  private void report() {
    Iterator<Long> iter = interval.iterator();
    for (int i = 1; i <= TIMES; i++) {
      if (iter.hasNext()) {
        System.out.printf("Packet No. %d, RTT: %d millisecond.\n", i, iter.next());
      } else {
        System.out.printf("Packet No. %d, Lost.\n", i);
      }
    }
  }


  public static void main(String... args) {
    if (args.length < 2) {
      System.out.println("Please specified the host and the port by <hostname> <port>");
      System.exit(1);
    }
    String host = args[0];
    // parse the port
    int port = 0;
    try {
      port = Integer.parseInt(args[1]);
    } catch (NumberFormatException e) {
      System.out.printf("In valid port number: %s", args[1]);
      System.exit(1);
    }
    try (DatagramSocket aSocket = new DatagramSocket()) {
      // get the hostname
      InetAddress aHost = InetAddress.getByName(args[0]);
      UDPClient c = new UDPClient();
      c.setSocket(aSocket);
      c.ping(aHost, port);
      c.report();
    } catch (SocketException e) {
      System.out.printf("Socket error: %s\n", e.getMessage());
    } catch (IOException e) {
      System.out.printf("Encountered I/O problem: %s\n", e.getMessage());
    }
  }

}
