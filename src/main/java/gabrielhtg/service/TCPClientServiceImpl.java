package gabrielhtg.service;

import java.io.IOException;
import java.util.Base64;

public class TCPClientServiceImpl implements TCPClientService{
    @Override
    public String encode(String sentence) {
        byte[] stringInBytes = sentence.getBytes();
        return Base64.getEncoder().encodeToString(stringInBytes) + "\n";
    }

    @Override
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

    @Override
    public void buatGaris (int n) {
        for (int i = 0; i < n; i++) {
            System.out.print("-");
        }
        System.out.println();
    }
        
    @Override
    public void buatGaris () {
        System.out.println("-------------------------------------------");
    }

    @Override
    public void loadingEcekEcek() {
        String[] animationFrames = { "|", "/", "-", "\\" };
        int frameIndex = 0;
        int durationInSeconds = 2;
        
        long startTime = System.currentTimeMillis();
        
        while (true) {
            System.out.print("\rLoading " + animationFrames[frameIndex]);
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

    @Override
    public void letakkanTeks(int x, int y, String text) {
        System.out.print("\u001B[" + x + ";" + y + "H");
        
        // Menampilkan teks setelah kursor berada pada posisi yang diinginkan
        System.out.println(text);
    }
}
