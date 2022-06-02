package app;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Processo implements Runnable {

  private Socket socket;

  private boolean conexao = true;
  private TypeConnection typeConnection;
  private static int id = 0;

  private String log[];

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

  public static int getId() {
    return id;
  }

  public static void setId(int id) {
    Processo.id = id;
  }

  public String[] getLog() {
    return log;
  }

  public void setLog(String[] log) {
    this.log = log;
  }

  public void menu() {}

  @Override
  public void run() {
    // System.out.println("cheguei no run");
    // System.out.println("my id" + Processo.getId());
    if (this.getTypeConnection() == TypeConnection.LISTEM) {
      System.out.println("Conexão com o cliente estabelecida:");

      try {
        Scanner scanner = new Scanner(this.getSocket().getInputStream());
        scanner.next();

        String mensagemRecebida;

        while (this.isConexao()) {
          mensagemRecebida = scanner.nextLine();
          System.out.println("pegando msg");
          // Integer.parseInt(mensagemRecebida);

          if (mensagemRecebida.equalsIgnoreCase("fim")) {
            this.setConexao(false);
          } else {
            System.out.println(mensagemRecebida);
          }
        }

        scanner.close();
        this.getSocket().close();
      } catch (NumberFormatException | IOException number) {
        System.out.println(number.getCause());
        number.printStackTrace();
      }
    } else if (this.getTypeConnection() == TypeConnection.CONNECT) {
      try {
        System.out.println("O cliente conectou ao servidor");

        PrintStream canalDeEnvio = new PrintStream(
          this.getSocket().getOutputStream()
        );

        String mensagem;
        System.out.println("mensagem criada");

        while (this.isConexao()) {
          System.out.println("Digite uma mensagem");
          mensagem = ProcessoManager.scanner.nextLine();
          // System.out.println("mensagem lida");

          if (mensagem.equalsIgnoreCase("fim")) {
            this.setConexao(false);
          } else {
            System.out.println(mensagem);
          }
          System.out.println("\tEnviado");
          canalDeEnvio.println(mensagem);
        }

        canalDeEnvio.close();
        // scanner.close();
        socket.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public void enviarMensagem() {}

  public void receberMensagem() {}
}
