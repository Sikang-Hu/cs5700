import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Sikang on 2019-09-23.
 */
public class TCPServer {


  public static void main(String... args) throws IOException {
    // Register service on port 32000
    ServerSocket s = new ServerSocket(32000);
    Socket s1 = s.accept();
    OutputStream s1out = s1.getOutputStream();
    DataOutputStream dos = new DataOutputStream(s1out);
    dos.writeUTF("Hi there!");
    dos.close();
    s1out.close();
    s1.close();
  }



}
