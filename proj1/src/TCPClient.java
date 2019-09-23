import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Created by Sikang on 2019-09-23.
 */
public class TCPClient {


  public static void main(String... args) throws IOException {
    Socket s1 = new Socket("localhost", 32000);
    InputStream s1In = s1.getInputStream();
    DataInputStream dis = new DataInputStream(s1In);
    String st = dis.readUTF();
    System.out.println(st);

    dis.close();
    s1In.close();
    s1.close();
  }


}
