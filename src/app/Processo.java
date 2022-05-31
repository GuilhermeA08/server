package app;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Processo implements Runnable {

  private Socket socket;

  private boolean conexao = true;
  private TypeConnection typeConnection;
  private static int cont = 0;

  private String log[];
  private static int id;

  public void name() {}

  public Processo(Socket socket) {
    this.socket = socket;
  }

  public Processo() {}

  public TypeConnection getTypeConnection() {
    return typeConnection;
  }

  public void setTypeConnection(TypeConnection typeConnection) {
    this.typeConnection = typeConnection;
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
    Processo.id = id;
  }

  public void menu() {}

  @Override
  public void run() {
    if (this.getTypeConnection() == TypeConnection.LISTEM) {
      System.out.println("Conexão com o cliente estabelecida:");

      try {
        Scanner scanner = new Scanner(this.getSocket().getInputStream());

        String mensagemRecebida;

        while (this.isConexao()) {
          mensagemRecebida = scanner.nextLine();

          if (mensagemRecebida.equalsIgnoreCase("fim")) {
            this.setConexao(false);
          } else {
            System.out.println(mensagemRecebida);
          }
        }

        scanner.close();
        this.getSocket().close();
      } catch (Exception e) {}
    } else if (this.getTypeConnection() == TypeConnection.CONNECT) {
      try {
        System.out.println("O cliente conectou ao servidor");
        Scanner scanner = new Scanner(System.in);
        PrintStream canalDeEnvio = new PrintStream(
          this.getSocket().getOutputStream()
        );

        String mensagem;

        while (this.isConexao()) {
          System.out.println("Digite uma mensagem");
          mensagem = scanner.nextLine();

          if (mensagem.equalsIgnoreCase("fim")) {
            this.setConexao(false);
          } else {
            System.out.println(mensagem);
          }
          canalDeEnvio.println(mensagem);
        }

        canalDeEnvio.close();
        scanner.close();
        socket.close();
      } catch (Exception e) {
        //TODO: handle exception
      }
    }

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
}
