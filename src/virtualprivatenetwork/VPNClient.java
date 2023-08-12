/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package virtualprivatenetwork;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.io.*;
import java.net.Socket;
import java.security.Key;
import java.security.SecureRandom;

/**
 *
 * @author ikasa
 */
public class VPNClient {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 12345;
    private static Key secretKey;

    public static void main(String[] args) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = new SecureRandom();
            keyGenerator.init(256, secureRandom);
            secretKey = keyGenerator.generateKey();
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            System.out.println("Connected to VPN server");

            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = System.in.read(buffer)) != -1) {
                byte[] encryptedBytes = cipher.update(buffer, 0, bytesRead);
                out.write(encryptedBytes);
            }
            out.close();
            in.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
