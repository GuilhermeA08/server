package app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ProcessoManager {

  static Scanner scanner = new Scanner(System.in);

  public static void main(String[] args)
    throws UnknownHostException, IOException {
    // String host;
    // int port;
    int opção;

    // System.out.println("Host: ");
    // host = scanner.next();
    // System.out.println("Port: ");
    // port = scanner.nextInt();

    // Socket socket = new Socket(host, port);
    // Processo processo = new Processo(socket);

    do {
      System.out.println("============MENU=============");
      System.out.println("Processo");
      System.out.println("1- Escutar");
      System.out.println("2- Conectar");

      opção = scanner.nextInt();

      switch (opção) {
        case 1:
          System.out.println("Porta: ");
          ServerSocket serverSocket = new ServerSocket(scanner.nextInt());

          // scanner.close();

          Socket cliente = serverSocket.accept();
          Processo processo = new Processo(cliente);
          processo.setTypeConnection(TypeConnection.LISTEM);
          Thread thread = new Thread(processo);
          Processo.setId(Processo.getId() + 1);
          thread.start();
          serverSocket.close();
          break;
        case 2:
          System.out.println("Host e Porta: ");
          Socket socket = new Socket(scanner.next(), scanner.nextInt());

          // scanner.close();
          // System.out.println("scanner 1 closed");

          Processo processoConnect = new Processo(socket);
          processoConnect.setTypeConnection(TypeConnection.CONNECT);
          Thread thread2 = new Thread(processoConnect);
          thread2.start();
          break;
        default:
          break;
      }
    } while (false);
    // scanner.close();
  }

  static String lerMensagem() {
    Scanner scanner = new Scanner(System.in);
    String mensagem = scanner.nextLine();
    scanner.close();
    return mensagem;
  }
}
