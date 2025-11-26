/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author HP
 */
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class encryption {
    public static String encrypt(String textToEncrypt, byte[] hashedMasterKey){
        try {
            //men-generate Initial Vector (IV), yaitu random value.
            SecureRandom secureRandom = new SecureRandom();
            byte[] iv = new byte[16];
            secureRandom.nextBytes(iv);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            //menginisialisasi secret key dengan hasil hash masterkey
            SecretKeySpec secretKeySpec = new SecretKeySpec(hashedMasterKey, "AES");

            //setup algoritma enkripsi, menggunakan algoritma AES, mode CBC, dan PKCS5 padding.
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);

            //menjalankan algoritma enkripsi, lalu hasilnya disimpan dalam variabel cipherText
            byte[] cipherText = cipher.doFinal(textToEncrypt.getBytes("UTF-8"));

            //menggabungkan cipher text dengan iv
            byte[] encrypted = new byte[iv.length + cipherText.length];
            //array iv ditaruh ke encrypted, posisinya di awal
            System.arraycopy(iv, 0, encrypted, 0, iv.length);
            //array cipherText ditaruh juga di encrypted, posisinya setelah iv
            System.arraycopy(cipherText, 0, encrypted, iv.length, cipherText.length);

            return Base64.getEncoder().encodeToString(encrypted);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decrypt(String textToDecrypt, byte[] hashedMasterKey){
        try {
            //decode cipherText dengan Base64
            byte[] encrypted = Base64.getDecoder().decode(textToDecrypt);

            //ambil 16 byte pertama dari array encrypted, taruh di array iv
            byte[] iv = new byte[16];
            System.arraycopy(encrypted, 0, iv, 0, iv.length);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            //menginisialisasi hasil hash sebagai secret key
            SecretKeySpec secretKeySpec = new SecretKeySpec(hashedMasterKey, "AES");

            //men-set-up algoritma AES, konfigursi sama seperti sebelumnya, berbeda di bagian init saja (mode dekripsi)
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec);

            //ambil ciphertext dari array encripted, posisinya setelah array iv
            byte[] cipherText = new byte[encrypted.length - 16];
            System.arraycopy(encrypted, 16, cipherText, 0, cipherText.length);

            //melakukan dekripsi penuh
            byte[] plainText = cipher.doFinal(cipherText);

            return new String(plainText, "UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
