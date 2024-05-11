package bn.connection;

import java.net.*;
import java.io.*;

/**
 *
 * @author Eduardo Santos
 */
public class Client {

  private Socket clientSocket;
  private PrintWriter out;
  private BufferedReader in;

  public void startConnection(String ip, int port) {
    try {
      
      clientSocket = new Socket(ip, port);
      out = new PrintWriter(clientSocket.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      
    } catch (Exception e) {
      System.out.println("Error in client socket: " + e);
    }
  }

  public String sendMessage(String msg) {
    out.println(msg);
    String resp = "";
    
    try {
      
      resp = in.readLine();
      return resp;
      
    } catch (Exception e) {
      System.out.println("Error in client socket: " + e);
    }
    
    return resp;
  }

  public void stopConnection() {
    try {
      
      in.close();
      out.close();
      clientSocket.close();
      
    } catch (Exception e) {
      System.out.println("Error in client socket: " + e);
    }
  }
}

