package access.token;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class MDBuilder {

    public static String getMD5(String msg, String salt) {
        return MDBuilder.buildMD(msg, "MD5", salt);
    }

    public static String getHmacMD5(String msg, String key, String salt) {
        return MDBuilder.buildHmacMD(msg, "HmacMD5", key, salt);
    }

    private static String buildMD(String msg, String algs, String salt) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance(algs);
            byte[] buffer = md.digest(addSalt(msg, salt).getBytes());
            result = toHex(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String buildHmacMD(String msg, String algs, String key, String salt) {
        String result = "";
        try {
            SecretKey sk = new SecretKeySpec(key.getBytes(), algs);
            Mac mac = Mac.getInstance(algs);
            mac.init(sk);
            byte[] buffer = mac.doFinal(addSalt(msg, salt).getBytes());
            result = toHex(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String addSalt(String msg, String salt) {
        return msg + salt;
    }

    //private static final int PBKDF2_ITERATIONS = 54;
    private static final int HASH_BIT_SIZE = 32 * 4;
    private static final String PBKDF2_ALGORITHM = "PBKDF2withHmacSHA1";

    public static String getPBKD2(String msg, String salt, int iterations) {
        String result = "";
        try {
            KeySpec spec = new PBEKeySpec(msg.toCharArray(), salt.getBytes(), iterations, HASH_BIT_SIZE);
            SecretKeyFactory f = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
            result = toHex(f.generateSecret(spec).getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String toHex(byte[] bytes) {
        StringBuilder md5str = new StringBuilder();
        for (byte aByte : bytes) {
            int temp = aByte;
            if (temp < 0) temp += 256;
            if (temp < 16) md5str.append("0");
            md5str.append(Integer.toHexString(temp));
        }
        return md5str.toString();
    }

    public static String getRandomSalt(int length, Long seed) {
        String salt = "default";
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            if (seed != null) random.setSeed(seed);
            byte[] bytes = new byte[length / 2];
            random.nextBytes(bytes);
            //salt = DatatypeConverter.printHexBinary(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salt;
    }

    public static String getMD5(String s) {
        return MDBuilder.getMD5(s, "randomstringforsalt");
    }
    
}
