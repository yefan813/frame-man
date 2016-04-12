package com.frame.service.utils;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RSA安全编码组件
 * 
 * @version 1.0
 * @since 1.0
 */
public class RSAUtils {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RSAUtils.class);
    
    public static final String KEY_ALGORITHM = "RSA";
    
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    
    private static final String PUBLIC_KEY = "RSAPublicKey";
    
    private static final String PRIVATE_KEY = "RSAPrivateKey";
    
    /**
     * 用私钥对信息生成数字签名
     * 
     * @param data
     *            加密数据
     * @param privateKey
     *            私钥
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        // 解密由base64编码的私钥
        byte[] keyBytes = Base64Utils.decode(privateKey);
        
        // 构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        
        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        
        // 取私钥匙对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        
        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);
        
        return Base64Utils.encode(signature.sign());
    }
    
    /**
     * 校验数字签名
     * 
     * @param data
     *            加密数据
     * @param publicKey
     *            公钥
     * @param sign
     *            数字签名
     * @return 校验成功返回true 失败返回false
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
        
        // 解密由base64编码的公钥
        byte[] keyBytes = Base64Utils.decode(publicKey);
        
        // 构造X509EncodedKeySpec对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        
        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        
        // 取公钥匙对象
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);
        
        // 验证签名是否正常
        return signature.verify(Base64Utils.decode(sign));
    }
    
    /**
     * 解密<br>
     * 用私钥解密
     * 
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data, String key) throws Exception {
        // 对密钥解密
        byte[] keyBytes = Base64Utils.decode(key);
        
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        
        return cipher.doFinal(data);
    }
    
    /**
     * 解密<br>
     * 用公钥解密
     * 
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data, String key) throws Exception {
        // 对密钥解密
        byte[] keyBytes = Base64Utils.decode(key);
        
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        
        return cipher.doFinal(data);
    }
    
    /**
     * 解密<br>
     * 用公钥解密
     * 
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String decryptByPublicKey(String data, String key) {
        try {
            return new String(decryptByPublicKey(hex2byte(data.getBytes("utf-8")), key), "utf-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("解密失败,data:" + data, e);
        } catch (Exception e) {
            LOGGER.error("解密失败,data:" + data, e);
        }
        return "";
    }
    
    /**
     * 加密<br>
     * 用公钥加密
     * 
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String key) throws Exception {
        // 对公钥解密
        byte[] keyBytes = Base64Utils.decode(key);
        
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        
        return cipher.doFinal(data);
    }
    
    /**
     * 加密<br>
     * 用私钥加密
     * 
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String key) throws Exception {
        // 对密钥解密
        byte[] keyBytes = Base64Utils.decode(key);
        
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        
        return cipher.doFinal(data);
    }
    
    /**
     * 加密<br>
     * 用私钥加密
     * 
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptByPrivateKey(String data, String key) {
        try {
            return byte2hex(encryptByPrivateKey(data.getBytes("utf-8"), key));
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("加密失败,data:" + data, e);
        } catch (Exception e) {
            LOGGER.error("加密失败,data:" + data, e);
        }
        return "";
    }
    
    /**
     * 取得私钥
     * 
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        
        return Base64Utils.encode(key.getEncoded());
    }
    
    /**
     * 取得公钥
     * 
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        
        return Base64Utils.encode(key.getEncoded());
    }
    
    /**
     * 初始化密钥
     * 
     * @return
     * @throws Exception
     */
    public static Map<String, Object> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        
        KeyPair keyPair = keyPairGen.generateKeyPair();
        
        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        
        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }
    
    public static Map<String, String> genKey() throws NoSuchAlgorithmException {
        Map<String, String> keyMap = new HashMap<String, String>();
        KeyPairGenerator keygen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        SecureRandom random = new SecureRandom();
        // random.setSeed(keyInfo.getBytes());
        // 初始加密，512位已被破解，用1024位,最好用2048位
        keygen.initialize(1024, random);
        // 取得密钥对
        KeyPair kp = keygen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) kp.getPrivate();
        String privateKeyString = Base64Utils.encode(privateKey.getEncoded());
        RSAPublicKey publicKey = (RSAPublicKey) kp.getPublic();
        String publicKeyString = Base64Utils.encode(publicKey.getEncoded());
        keyMap.put(PUBLIC_KEY, publicKeyString);
        keyMap.put(PRIVATE_KEY, privateKeyString);
        return keyMap;
    }
    
    public static RSAPublicKey getPublicKey(String publicKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return (RSAPublicKey) keyFactory.generatePublic(spec);
    }
    
    public static RSAPrivateKey getPrivateKey(String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return (RSAPrivateKey) keyFactory.generatePrivate(spec);
    }
    
    /**
     * 二行制转十六进制字符串
     * 
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }
    
    /**
     * 十六进制转二进制
     * 
     * @param b
     * @return
     */
    public static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException("长度不是偶数");
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }
    
}
