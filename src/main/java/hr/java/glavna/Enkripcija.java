package hr.java.glavna;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Enkripcija  {
    public String encryptString(String unos)throws NoSuchAlgorithmException {
        MessageDigest md=MessageDigest.getInstance("MD5");
        byte[] messageDigest=md.digest(unos.getBytes());
        BigInteger bigInteger=new BigInteger(1,messageDigest);
        return bigInteger.toString();
    }
}
