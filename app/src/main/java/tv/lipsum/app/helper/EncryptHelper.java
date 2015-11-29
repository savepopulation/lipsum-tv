package tv.lipsum.app.helper;

/**
 * Created by tyln on 14.07.15.
 */

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by TaylanS on 13.1.2015.
 */
public class EncryptHelper {
    private static final String CHIPHER_TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private static final String GENERATE_KEY__ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final String GENERATE_KEY_ALGORITHM = "AES";
    public static final int CRYPTO_TYPE_ENCRYPT = 0;
    public static final int CRYPTO_TYPE_DECRYPT = 1;
    private String hashKey;
    private String salt;
    private SecretKey key;
    private String charset;

    public EncryptHelper(String hashKey, String salt, String charset) {
        this.hashKey = hashKey;
        this.salt = salt;
        this.charset = charset;
        initKey();
    }

    public String crypto(String inString, int type) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(CHIPHER_TRANSFORMATION);
            byte[] inputByte = inString.getBytes(charset);
            switch (type) {
                case CRYPTO_TYPE_DECRYPT:
                    cipher.init(Cipher.DECRYPT_MODE, key);
                    return new String(cipher.doFinal(Base64.decode(inputByte, Base64.DEFAULT)));
                case CRYPTO_TYPE_ENCRYPT:
                    cipher.init(Cipher.ENCRYPT_MODE, key);
                    return new String(Base64.encode(cipher.doFinal(inputByte), Base64.DEFAULT));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return null;
    }

    private SecretKey getSecretKey(char[] password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance(GENERATE_KEY__ALGORITHM);
        KeySpec spec = new PBEKeySpec(password, salt, 1024, 128);
        SecretKey tmp = factory.generateSecret(spec);
        return (new SecretKeySpec(tmp.getEncoded(), GENERATE_KEY_ALGORITHM));
    }

    private void initKey() {
        try {
            key = getSecretKey(hashKey.toCharArray(), salt.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }
}