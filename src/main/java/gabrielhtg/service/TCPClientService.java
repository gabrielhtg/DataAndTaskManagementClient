package gabrielhtg.service;

public interface TCPClientService {
    String encode(String sentence);
    String decode(String sentence);
    void buatGaris(int n);
    void buatGaris();
    void loadingEcekEcek();
    void letakkanTeks(int x, int y, String text);
}
