/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author HP
 */
public class Password_general {
    private String title, username, password, link, notes;
    private int credentialsId;

    public Password_general (String title, String username, String password, String link, String notes){
        this.title = title;
        this.username = username;
        this.password = password;
        this.link = link;
        this.notes = notes;
    }
    public Password_general (String title, String username, String password, String link, String notes, int credentialsId){
        this.title = title;
        this.username = username;
        this.password = password;
        this.link = link;
        this.notes = notes;
        this.credentialsId = credentialsId;
    }


    //getter untuk masing-masing variabel
    public String getTitle(){return title;}
    public String getUsername(){return username;}
    public String getPassword(){return password;}
    public String getLink(){return link;}
    public String getNotes(){return notes;}
    public int getId(){return credentialsId;}
    

    //setter untuk masing-masing variabel
    public void setTitle(String title){ this.title = title;}
    public void setUsername(String username){ this.username = username;}
    public void setPassword(String password){ this.password = password;}
    public void setLink(String link){ this.link = link;}
    public void setNotes(String notes){ this.notes = notes;}
    public void setNotes(int credentialsId){ this.credentialsId = credentialsId;}
    
    
    public Password_general() {
    
    }
     //inisialiasi kategori yang akan digunakan untuk password generator
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-_=+[]{};:,.<>?/\\|";

    //panjang password selalu 16
    private static final int PASSWORD_LENGTH = 16;

    private SecureRandom random = new SecureRandom();

    public String generate() {
        //mendeklarasikan array untuk menampung hasil generate
        List<Character> chars = new ArrayList<>();

        //men-generate 1 karakter untuk amsing-masing kategori
        chars.add(randomChar(UPPER));
        chars.add(randomChar(LOWER));
        chars.add(randomChar(DIGITS));
        chars.add(randomChar(SYMBOLS));

        //menggabungkan semua kategori
        String all = UPPER + LOWER + DIGITS + SYMBOLS;

        //men-generate karakter dari gabungan kategori untuk mengisi sisanya
        while (chars.size() < PASSWORD_LENGTH) {
            chars.add(randomChar(all));
        }

        //mengacak urutan array chars
        Collections.shuffle(chars);

        //mengonversi array chars menjadi string
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            sb.append(c);
        }

        return sb.toString();
    }

    //fungsi untuk men-generate karakter acak dalam daftar kategori
    private char randomChar(String src) {
        return src.charAt(random.nextInt(src.length()));
    }
    
    public enum Strength {
        WEAK,
        MEDIUM,
        STRONG
    }

    public Strength strengthTest(String password) {
        int score = 0;

        //jika panjang password > 8, poin+1, >12 poin+2
        if (password.length() >= 8) score++;
        if (password.length() >= 12) score++;

        if (password.matches(".*[A-Z].*")) score++;
        if (password.matches(".*[a-z].*")) score++;
        if (password.matches(".*[0-9].*")) score++;
        if (password.matches(".*[!@#$%^&*()\\-_=+\\[\\]{};:,.<>?/\\\\|].*")) score++;

        // bagian perhitungan score
        if (score <= 2) {
            System.out.println("Pastikan password mengandung huruf kecil, Kapital, angka, simbol, serta panjang > 12 karakter");
            return Strength.WEAK;
        } else if (score <= 4) {
            System.out.println("Pastikan password mengandung huruf kecil, kapital, angka, simbol, serta panjang > 12 karakter");
            return Strength.MEDIUM;
        } else {
            return Strength.STRONG;
        }
    }
}
