/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author HP
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Regist {

    //variabel buat nampung input masterkey
    public String masterKey;

    //fungsi buat nge-cek apakah masterkey ada atau ngga
    public boolean searchMasterKey(){
        try {
            Connection conn = Database.getConnection();
            String sql = "SELECT user_id FROM users LIMIT 1";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }catch(Exception e){
            System.out.println("Error search master key: "+e.getMessage());
            return false;
        }
    }

    //fungsi untuk registrasi masterkey
    public int createMasterKey(String masterKey){
        try {
            if (searchMasterKey()){
                System.out.println("Master Key sudah ada. Tidak dapat membuat yang baru.");
                return -1;
            }

            hash.hashMasterKey(masterKey); // jalankan hashing dulu
            String hashedMasterKey = hash.hashToBase64(masterKey); // baru encode ke base64String hashedMasterKey = hash.hashToBase64(masterKey);

            Connection conn = Database.getConnection();

            String sql = "INSERT INTO users (master_key) VALUES (?)";
            PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, hashedMasterKey);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // return user_id
            }

        } catch (Exception e) {
            System.out.println("Error saat membuat master key: " + e.getMessage());
        }

        return -1; // gagal
    }

    // fungsi untuk menyimpan pertanyaan pengingat
    public boolean saveClue(int userId, String q1, String q2, String q3){
        try{
        // Encrypt all questions before saving
        String encQ1 = encryption.encrypt(q1, hash.hashedMasterKey);
        String encQ2 = encryption.encrypt(q2, hash.hashedMasterKey);
        String encQ3 = encryption.encrypt(q3, hash.hashedMasterKey);

        Connection conn = Database.getConnection();
        String sql = "INSERT INTO forget(user_id, question1, question2, question3) VALUES (?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, userId);
        stmt.setString(2, encQ1);
        stmt.setString(3, encQ2);
        stmt.setString(4, encQ3);
        stmt.executeUpdate();
        return true;

        } catch(Exception e){
            System.out.println("Error save clue: "+e.getMessage());
            return false;
        }
    }


    //fungsi untuk validasi login user
    public boolean verifyMasterKey(String masterKey){
        boolean isValid = false;

        try {
            hash.hashMasterKey(masterKey); // jalankan hashing dulu
            String hashedMasterKey = hash.hashToBase64(masterKey); // baru encode ke base64

            Connection conn = Database.getConnection();

            String sql = "SELECT master_key FROM users LIMIT 1";
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                //variabel yang menyimpan nilai hash master_key di DB
                String hash = rs.getString("master_key");

                if (hash.equals(hashedMasterKey)) {
                    isValid = true;
                }
            }

        } catch (Exception e) {
            System.out.println("Error saat validasi masterkey: " + e.getMessage());
        }

        return isValid;
    }
}
