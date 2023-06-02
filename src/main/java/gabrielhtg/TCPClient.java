package gabrielhtg;
import java.io.*;  
import java.net.*;
import java.util.Scanner;

import com.github.tomaslanger.chalk.Chalk;

import gabrielhtg.service.TCPClientService; 
 
public class TCPClient { 
    public static void main(String argv[]) throws Exception { 
        String sentence; 
        String kirimanServer;
        TCPClientService.clearScreen();
        TCPClientService service = new TCPClientService();
        Scanner scan = new Scanner(System.in);
        Socket clientSocket = new Socket(); 
        DataOutputStream outToServer = null; 
        BufferedReader inFromServer = null; 
        int panjangGaris = 80;

        service.buatGaris(panjangGaris);
        System.out.print("Masukkan IP Server  : ");
        String ipServer = scan.nextLine();
        // String ipServer = "192.168.43.200";
        
        SocketAddress socketAddr = new InetSocketAddress(ipServer, 5000);
        
        try{ 
            clientSocket.connect(socketAddr, 2000); 
            outToServer = new DataOutputStream(clientSocket.getOutputStream()); 
            inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 
            System.out.print("Masukkan username   : ");
            String username = scan.nextLine();
            outToServer.writeBytes(service.encode(username));
            
            if (service.decode(inFromServer.readLine()).trim().equals("false")) {
                service.buatGaris(panjangGaris);
                System.out.println("User " + Chalk.on(username).yellow() + Chalk.on(" tidak ditemukan!").red().bold());
                service.buatGaris(panjangGaris);
                System.out.print("Register now? (y/n)    : ");
                System.out.flush();
                String persetujuan = scan.nextLine();

                if (persetujuan.toLowerCase().equals("y")) {
                    String passwordSatu = "";
                    while (true) {
                        passwordSatu = service.mintaPw("Masukkan password baru : ");
                        String passwordDua = service.mintaPw("Masukkan password lagi : ");
                        
                        if (passwordSatu.equals(passwordDua)) {
                            outToServer.writeBytes(service.encode(passwordSatu));
                            break;
                        }

                        else {
                            service.buatGaris(panjangGaris);
                            System.out.println(Chalk.on("Password tidak sama. Coba lagi!").red().bold());
                            service.buatGaris(panjangGaris);
                        }
                    }
                    service.buatGaris(panjangGaris);

                    if (service.decode(inFromServer.readLine()).trim().equals("true")) {
                        System.out.println("Berhasil menambahkan user " + Chalk.on(username).yellow() + " dengan pw " + Chalk.on(passwordSatu).yellow() + ".");
                        System.out.println("Pesan ini akan hilang dalam 1 detik");
                        service.loadingEcekEcek(1);
                        TCPClientService.clearScreen();
                        System.out.flush();
                    }

                    else {
                        System.out.println(Chalk.on("Gagal menambahkan user").red().bold());
                        System.out.flush();
                        System.exit(0);
                    }
                }

                else {
                    service.buatGaris(panjangGaris);
                    System.exit(0);
                }
            }

            else {
                while (true) {
                    outToServer.writeBytes(service.encode(service.mintaPw("Masukkan password   : ")));
    
                    if (service.decode(inFromServer.readLine()).equals("false")) { // kredensial tidak tepat
                        continue;
                    }

                    else {
                        TCPClientService.clearScreen();
                        break;
                    }
                }
            }
            // System.out.print("Masukkan password   : ");
            // String password = scan.nextLine();
            service.buatGaris(panjangGaris);
            System.out.println("Connecting to server on IP: " +  ipServer + " on PORT: " + 5000); 
            
            service.loadingEcekEcek(1);
            service.buatGaris(panjangGaris);

            System.out.println(Chalk.on("Berhasil terhubung ke server!!").green().bold()); 
            
            service.buatGaris(panjangGaris);
        } catch (SocketTimeoutException exception) { 
            System.out.println("SocketTimeoutException " + ipServer + ":" + 5000 + ". " + exception.getMessage()); 
            System.exit(0); 
        } catch (IOException exception) { 
            exception.printStackTrace();
            System.out.println("IOException - Unable to connect to " + ipServer + ":" + 5000 + ". " + exception.getMessage()); 
            System.exit(0); 
        } 
 
        System.out.println("Tuliskan \"/exit\" untuk berhenti !!"); 

        do { 
            service.buatGaris(panjangGaris);
            System.out.print("Input : ");
             
            System.out.flush();
            sentence = scan.nextLine(); 

            if (sentence.equals("/save")) {
                service.buatGaris(panjangGaris);
                StringBuilder isiNote = new StringBuilder();
                System.out.print("Masukkan nama note : ");
                System.out.flush();
                String namaNote = scan.nextLine();
                System.out.flush();
                service.buatGaris(panjangGaris);
                System.out.println("Masukkan isi note  : ");

                String temp = "";
                while (!temp.equals("---")) {
                    temp = scan.nextLine();
                    isiNote.append(temp + "\n");
                }

                outToServer.writeBytes(sentence + '\n'); 
                outToServer.writeBytes(service.encode(namaNote));
                outToServer.writeBytes(service.encode(isiNote.toString()));
                kirimanServer = inFromServer.readLine();
                service.buatGaris(panjangGaris);
                System.out.println(service.decode(kirimanServer));
                continue;
            }

            else if (sentence.equals("/remove")) {
                service.buatGaris(panjangGaris);
                System.out.print("Masukkan notename : ");
                System.out.flush();
                String notenameRemove = scan.nextLine();
                outToServer.writeBytes(sentence + '\n'); 
                outToServer.writeBytes(service.encode(notenameRemove)); // mengirim notename ke server
                kirimanServer = inFromServer.readLine(); // menerima kiriman server
                service.buatGaris(panjangGaris);
                System.out.println(service.decode(kirimanServer));
                continue;
            }

            else if (sentence.equals("/send")) {
                service.buatGaris(panjangGaris);
                System.out.print("Masukkan path     : ");
                System.out.flush();
                String path = scan.nextLine();
                outToServer.writeBytes(sentence + '\n');
                int length = path.split("\\\\").length;
                outToServer.writeBytes(service.encode(path.split("\\\\")[length - 1])); 
                service.kirimFile(ipServer, path);
                // outToServer.writeBytes(service.encode(path)); // mengirim path ke server
                // kirimanServer = inFromServer.readLine(); // menerima kiriman server
                service.buatGaris(panjangGaris);
                // System.out.println(service.decode(kirimanServer));
                continue;
            }

            service.buatGaris(panjangGaris);
            outToServer.writeBytes(sentence + '\n'); 
            kirimanServer = inFromServer.readLine();
            System.out.println(service.decode(kirimanServer));
        } while(!sentence.toLowerCase().equals("/exit")); 
 
        service.buatGaris(panjangGaris);
        clientSocket.close();
        scan.close();
    } 
}