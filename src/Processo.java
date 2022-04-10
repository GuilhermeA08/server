import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Processo implements Runnable {

  private Socket socket;
  private ServerSocket serverSocket;

  private boolean conexao = true;
  private static int cont = 0;

  private String log[];
  private int id;

  public Processo(Socket socket) {
    this.socket = socket;
  }

  public Processo() {}

  public ServerSocket getServerSocket() {
    return serverSocket;
  }

  public void setServerSocket(ServerSocket serverSocket) {
    this.serverSocket = serverSocket;
  }

  public Socket getSocket() {
    return socket;
  }

  public void setSocket(Socket socket) {
    this.socket = socket;
  }

  public boolean isConexao() {
    return conexao;
  }

  public void setConexao(boolean conexao) {
    this.conexao = conexao;
  }

  public static int getCont() {
    return cont;
  }

  public static void setCont(int cont) {
    Processo.cont = cont;
  }

  public String[] getLog() {
    return log;
  }

  public void setLog(String[] log) {
    this.log = log;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public void run() {
    System.out.println(
      "Conexão " +
      Processo.cont +
      " com o cliente " +
      socket.getInetAddress().getHostAddress() +
      "/" +
      socket.getInetAddress().getHostName()
    );

    try {
      Scanner s = null;
      s = new Scanner(socket.getInputStream());
      String mensagemRecebida;

      // Exibe mensagem no console
      while (conexao) {
        mensagemRecebida = s.nextLine();

        if (mensagemRecebida.equalsIgnoreCase("fim")) conexao =
          false; else System.out.println(mensagemRecebida);
      }

      // Finaliza scanner e socket
      s.close();
      System.out.println(
        "Fim da conexão " + socket.getInetAddress().getHostAddress()
      );
      socket.close();
    } catch (IOException e) {
      e.getMessage();
    }
  }

  public void enviarMensagem() {}

  public void receberMensagem() {}

  public void connect() throws UnknownHostException, IOException {
    try (Socket socket = new Socket("127.0.0.1", 12345)) {
      InetAddress inet = socket.getInetAddress();

      System.out.println("HostAddress = " + inet.getHostAddress());
      System.out.println("HostName = " + inet.getHostName());
    }

    /*
     * Cria um novo objeto Cliente com a conexão socket para que seja executado em um novo processo.
       Permitindo assim a conexão de vário clientes com o servidor.
       */

    Thread t = new Thread(this);
    t.start();
  }

  public void listen() throws IOException {
    serverSocket = new ServerSocket(12345);
    while (true) {
      Socket cliente = serverSocket.accept();

      // Cria uma thread do servidor para tratar a conexão
      Processo processo = new Processo(cliente);

      Thread t = new Thread(processo);
      // Inicia a thread para o cliente conectado

      Processo.setCont(Processo.getCont() + 1);

      t.start();
    }
  }
}
