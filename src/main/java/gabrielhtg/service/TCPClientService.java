package gabrielhtg.service;

import java.io.Console;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Base64;

public class TCPClientService{
    public String encode(String sentence) {
        byte[] stringInBytes = sentence.getBytes();
        return Base64.getEncoder().encodeToString(stringInBytes) + "\n";
    }

    public String decode(String sentence) {
        byte[] stringInBytes = Base64.getDecoder().decode(sentence);
        return new String(stringInBytes);
    }

    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else {
                System.out.print("\033\143");
            }
        } catch (IOException | InterruptedException ex) {}
    }

    public void buatGaris (int n) {
        for (int i = 0; i < n; i++) {
            System.out.print("-");
        }
        System.out.println();
    }
        
    public void buatGaris () {
        System.out.println("-------------------------------------------");
    }

    public void loadingEcekEcek(int n) {
        String[] animationFrames = { "|", "/", "-", "\\" };
        int frameIndex = 0;
        int durationInSeconds = n;
        
        long startTime = System.currentTimeMillis();
        
        while (true) {
            System.out.print("\rLoading " + animationFrames[frameIndex]);
            System.out.flush();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            frameIndex = (frameIndex + 1) % animationFrames.length;
            
            long currentTime = System.currentTimeMillis();
            long elapsedTimeInSeconds = (currentTime - startTime) / 1000;
            
            if (elapsedTimeInSeconds >= durationInSeconds) {
                System.out.print("\rLoading " + '\u2713' + '\n');
                break;
            }
        }
    }

    public void letakkanTeks(int x, int y, String text) {
        System.out.print("\u001B[" + x + ";" + y + "H");
        
        // Menampilkan teks setelah kursor berada pada posisi yang diinginkan
        System.out.println(text);
    }

    public String mintaPw (String pesan) {
        Console console = System.console();
        if (console == null) {
            System.out.println("Tidak dapat mengambil konsol. Jalankan program di luar IDE.");
            System.exit(1);
        }
        char[] passwordArray = console.readPassword(pesan);
        String password = new String(passwordArray);
        java.util.Arrays.fill(passwordArray, ' ');

        return password;
    }

    public void kirimFile (String ip, String path) {
        String serverHost = ip;
        int serverPort = 5000;
        String filePath = path;

        try {
            Socket socket = new Socket(serverHost, serverPort);
            FileInputStream fileInputStream = new FileInputStream(filePath);
            OutputStream outputStream = socket.getOutputStream();
            // Baca file menjadi byte array
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                // Kirim byte array ke sisi penerima
                outputStream.write(buffer, 0, bytesRead);
            }
            fileInputStream.close();
            outputStream.flush();
            System.out.println("File berhasil dikirim");
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
