import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ProcessoManager {

  public static void main(String[] args)
    throws UnknownHostException, IOException {
    Socket socket = new Socket("localhost", 12345);
    Socket socket2 = new Socket("localhost", 12344);

    Processo p1 = new Processo(socket);
    Processo p2 = new Processo();
    Processo p3 = new Processo();
    Processo p4 = new Processo();
  }
}
