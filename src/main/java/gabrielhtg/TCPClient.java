package gabrielhtg;
import java.io.*;  
import java.net.*;
import java.util.Scanner;

import com.github.tomaslanger.chalk.Chalk;

import gabrielhtg.service.TCPClientService;
import gabrielhtg.service.TCPClientServiceImpl; 
 
public class TCPClient { 
    public static void main(String argv[]) throws Exception { 
        String sentence; 
        String kirimanServer;
        TCPClientServiceImpl.clearScreen();
        TCPClientService service = new TCPClientServiceImpl();
        Scanner scan = new Scanner(System.in);
        Socket clientSocket = new Socket(); 
        DataOutputStream outToServer = null; 
        BufferedReader inFromServer = null; 
        int panjangGaris = 60;

        service.buatGaris(panjangGaris);
        // System.out.print("Masukkan IP Server  : ");
        // String ipServer = scan.nextLine();
        String ipServer = "192.168.43.200";
        
        
        SocketAddress socketAddr = new InetSocketAddress(ipServer, 5000);
        
        try{ 
            clientSocket.connect(socketAddr, 2000); 
            outToServer = new DataOutputStream(clientSocket.getOutputStream()); 
            inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 
            System.out.print("Masukkan username   : ");
            String username = scan.nextLine();
            outToServer.writeBytes(service.encode(username));
            // System.out.print("Masukkan password   : ");
            // String password = scan.nextLine();
            service.buatGaris(panjangGaris);
            System.out.println("Connecting to server on IP: " +  ipServer + " on PORT: " + 5000); 
            
            service.loadingEcekEcek();
            service.buatGaris(panjangGaris);

            System.out.println( Chalk.on("Berhasil terhubung ke server!!").green().bold()); 
            
            service.buatGaris(60);
            // kirimanServer = inFromServer.readLine(); 
            // System.out.printf("FROM SERVER: %c %s\n", '\u2713', modifiedSentence ); 
        } catch (SocketTimeoutException exception) { 
            System.out.println("SocketTimeoutException " + ipServer + ":" + 5000 + ". " + exception.getMessage()); 
            System.exit(0); 
        } catch (IOException exception) { 
            exception.printStackTrace();
            System.out.println("IOException - Unable to connect to " + ipServer + ":" + 5000 + ". " + exception.getMessage()); 
            System.exit(0); 
        } 
 
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in)); 
        System.out.println("Tuliskan \"/exit\" untuk berhenti !!"); 

        do { 
            System.out.print("Input : "); 
            System.out.flush();
            sentence = inFromUser.readLine(); 
            outToServer.writeBytes(sentence + '\n'); 
            kirimanServer = inFromServer.readLine();
            System.out.println(service.decode(kirimanServer));
        } while(!sentence.toLowerCase().equals("/exit")); 
 
        clientSocket.close();
        scan.close();
    } 
}