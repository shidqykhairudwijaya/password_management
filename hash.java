/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.security.MessageDigest;
import java.util.Base64;

public class hash {
    static byte[] hashedMasterKey;

    //meng-hash masterkey, output masih array byte yang disimpan di variabel hashedMasterKey
    public static byte[] hashMasterKey(String masterKey){
        try{
            //membuat object "h" yang merupakan object hashing beralgoritma SHA-256
            MessageDigest h = MessageDigest.getInstance("SHA-256");
            //digest() adalah methode untuk menjalankan hashing secara penuh, parameter byte[] 
            //getBytes("UTF-8") adalah methode encoding teks ke byte menggunakan UTF-8
            return hashedMasterKey = h.digest(masterKey.getBytes("UTF-8"));
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //Mengubah tipe data hash dari byte ke string, supaya bisa disimpan di database
    //pakai method hashMasterKey dulu sebelum pakai fungsi ini
    public static String hashToBase64(String masterKey){
        //meng-encode hasil hash menjadi String menggunakan Base64
        return Base64.getEncoder().encodeToString(hashedMasterKey);
    }
}
