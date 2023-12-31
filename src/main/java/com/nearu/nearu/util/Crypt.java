package com.nearu.nearu.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Base64;

public class Crypt {

    Crypt(){}


    public static Crypt newCrypt(){
        return new Crypt();
    }

    public String getPassword(String msg, String salt){
        PBEKeySpec spec = new PBEKeySpec(msg.toCharArray(), salt.getBytes(), 10, 512);
        SecretKeyFactory skf;
        byte[] hash = null;
        try {
            skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = skf.generateSecret(spec).getEncoded();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new String(Base64.getEncoder().encode(hash));
    }

    /**
     * if length is 1, default lenght of return value is 10
     */
    public String getSalt(int length){
        try {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            byte[] salt = new byte[length];
            secureRandom.nextBytes(salt);
            byte[] encoded = Base64.getEncoder().encode(salt);
            return new String(encoded);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * make SHA-256
     * @param msg
     * @return String
     */
    public String getAuthKey(String msg){
        String value = "";

        SecretKeyFactory skf;
        byte[] hash = null;
        try {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            // Salt generation 128 bits long
            byte[] salt = new byte[256];
            secureRandom.nextBytes(salt);
            value = salt.toString();
            PBEKeySpec spec = new PBEKeySpec(msg.toCharArray(), value.getBytes(), 10, 512);
            skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = skf.generateSecret(spec).getEncoded();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return new String(Base64.getEncoder().encode(hash));
    }

    private boolean chkData(String msg){
        if(msg == null || "".equals(msg)){
            return false;
        }
        return true;
    }

    public String SHA256(String msg){
        StringBuffer sb = new StringBuffer();
        String data = "";
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(msg.getBytes());
            byte[] mdBytes = md.digest();
            data = byteAsString(mdBytes);
            return data;
        }catch (Exception localNoSuchAlgorithmException) {

        }
        return data;
    }

    private String byteAsString(byte[] dataBytes) throws Exception {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < dataBytes.length; i++) {
            String hex = Integer.toHexString(0xFF & dataBytes[i]);
            if (hex.length() == 1) sb.append('0');
            sb.append(hex);
        }
        return sb.toString();
    }
}