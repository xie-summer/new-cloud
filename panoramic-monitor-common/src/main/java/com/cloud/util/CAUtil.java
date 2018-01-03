package com.cloud.util;

import org.apache.commons.codec.binary.Base64;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;

/**
 * 1��openssl genrsa -out rsa_private_key.pem 1024 //����RSA˽Կ
 * 2��openssl rsa -in rsa_private_key.pem -pubout -out rsa_public_key.pem //����RSA��Կ����Ҫ���ݸ�taotao
 * 3��openssl pkcs8 -topk8 -inform PEM -in rsa_private_key.pem -outform PEM -nocrypt //��RSA˽Կת����PKCS8��ʽ������javaʹ�ã������ݸ��Ƶ� merPrivateKey
 * 
 * @author gebiao(ge.biao@taotao.com)
 * @since Jun 14, 2013 6:25:19 PM
 */
public class CAUtil {
	private static final transient TLogger DB_LOGGER = LoggerUtils.getLogger(CAUtil.class);
	public static final String MD5withRSA = "MD5withRSA";
	public static final String SHA1WithRSA = "SHA1WithRSA";
	public static PrivateKey getFirstPvkfromPfx(String pfxFile, String password) {
		PrivateKey prikey = null;
		char[] nPassword = password.toCharArray();
		KeyStore ks = getKsfromPfx(pfxFile, password);
		try {
			prikey = (PrivateKey) ks.getKey(ks.aliases().nextElement(), nPassword);
		} catch (UnrecoverableKeyException e) {
			DB_LOGGER.error("", e);
		} catch (KeyStoreException e) {
			DB_LOGGER.error("", e);
		} catch (NoSuchAlgorithmException e) {
			DB_LOGGER.error("", e);
		}
		return prikey;
	}

	public static PrivateKey getPvkfromPfx(String pfxFile, String password, String alias) {
		PrivateKey prikey = null;
		char[] nPassword = password.toCharArray();
		KeyStore ks = getKsfromPfx(pfxFile, password);
		// String keyAlias = getAlsfromPfx(pfxFile, strPassword);
		try {
			prikey = (PrivateKey) ks.getKey(alias, nPassword);
		} catch (UnrecoverableKeyException e) {
			DB_LOGGER.error("", e);
		} catch (KeyStoreException e) {
			DB_LOGGER.error("", e);
		} catch (NoSuchAlgorithmException e) {
			DB_LOGGER.error("", e);
		}
		return prikey;
	}

	private static KeyStore getKsfromPfx(String pfxFile, String password) {
		FileInputStream fis = null;
		try {
			KeyStore ks = KeyStore.getInstance("PKCS12");
			fis = new FileInputStream(pfxFile);
			// If the keystore password is empty(""), then we have to set
			// to null, otherwise it won't work!!!
			ks.load(fis, password.toCharArray());
			return ks;
		} catch (Exception e) {
			DB_LOGGER.error("", e);
		} finally {
			if (null != fis) {
				try {
					fis.close();
				} catch (IOException e) {
					DB_LOGGER.error("", e);
				}
			}
		}
		return null;
	}

	public static String getAlsfromPfx(String pfxFile, String strPassword) {
		String keyAlias = null;
		KeyStore ks = getKsfromPfx(pfxFile, strPassword);
		try {
			Enumeration<String> enumas = ks.aliases();
			if (enumas.hasMoreElements()) {
				keyAlias = enumas.nextElement();
			}
		} catch (KeyStoreException e) {
			DB_LOGGER.error("", e);
		}
		return keyAlias;
	}
	
	public static X509Certificate getX509Certificate(String certFile) {
		X509Certificate x509Certificate = null;
		InputStream ism = null;
		try {
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			ism = new FileInputStream(certFile);
			x509Certificate = (X509Certificate) cf.generateCertificate(ism);
		} catch (CertificateException e) {
			DB_LOGGER.error("", e);
		} catch (FileNotFoundException e) {
			DB_LOGGER.error("", e);
		} finally {
			if (null != ism) {
				try {
					ism.close();
				} catch (IOException e) {
					DB_LOGGER.error("", e);
				}
			}
		}
		return x509Certificate;
	}

	public static Certificate getCfefromPfx(String certFile, String password, String alias) {
		Certificate cert = null;
		try {
			KeyStore ks = getKsfromPfx(certFile, password);
			cert = ks.getCertificate(alias);
		} catch (KeyStoreException e) {
			DB_LOGGER.error("", e);
		}
		return cert;
	}
	
	/**
	 * ��ǩ
	 * <br>{@link #doCheck(String, String, String)}
	 * @param content Ԫ����
	 * @param sign	ǩ��
	 * @param publicKey base64encode��Ĺ�Կ
	 * @param algorithm ǩ���㷨
	 * @return true�ɹ�
	 */
	public static boolean doCheck(String content, String sign, String publicKey, String algorithm) {
		try {
			PublicKey pubKey = getPublicKeyFromX509("RSA", publicKey);
			Signature signature = Signature.getInstance(algorithm);
			signature.initVerify(pubKey);
			signature.update(content.getBytes("UTF-8"));
			return signature.verify(Base64.decodeBase64(sign.getBytes()));
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * ��ǩ���㷨ʹ��MD5withRSA
	 * @param content Ԫ����
	 * @param sign ǩ��
	 * @param publicKey base64encode��Ĺ�Կ
	 * @return
	 */
	public static boolean doCheck(String content, String sign, String publicKey) {
		return doCheck(content, sign, publicKey ,MD5withRSA);
	}
	
	private static PublicKey getPublicKeyFromX509(String algorithm, String publicKey) throws NoSuchAlgorithmException {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
			byte[] encodedKey = publicKey.getBytes();
			encodedKey = Base64.decodeBase64(encodedKey);
			return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
		} catch (InvalidKeySpecException ex) {
		}
		return null;
	}

	public static String doSign(String content, String privateKey, String encoding) {
		return doSign(content, privateKey, encoding,MD5withRSA);
	}
	
	public static String doSign(String content, String privateKey, String encoding, String algorithm) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] encodedKey = privateKey.getBytes(encoding);
			// ��base64����
			encodedKey = Base64.decodeBase64(encodedKey);
			PrivateKey priKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
			Signature signature = Signature.getInstance(algorithm);
			signature.initSign(priKey);
			signature.update(content.getBytes(encoding));
			byte[] signed = signature.sign();
			return new String(Base64.encodeBase64(signed));
		} catch (Exception e) {
			DB_LOGGER.warn(e, 20);
			return "";
		}
	}
	public static String doSign(String content, PrivateKey priKey, String encoding, String algorithm) {
		try {
			Signature signature = Signature.getInstance(algorithm);
			signature.initSign(priKey);
			signature.update(content.getBytes(encoding));
			byte[] signed = signature.sign();
			return new String(Base64.encodeBase64(signed));
		} catch (Exception e) {
			DB_LOGGER.warn(e, 20);
			return "";
		}
	}
	public static byte[] doSign(byte[] content, PrivateKey priKey, String algorithm) {
		try {
			Signature signature = Signature.getInstance(algorithm);
			signature.initSign(priKey);
			signature.update(content);
			byte[] signed = signature.sign();
			return signed;
		} catch (Exception e) {
			DB_LOGGER.warn(e, 20);
			return null;
		}
	}
	
	/**
	 * У��ǩ�����㷨ʹ��MD5withRSA
	 * <br>�μ�{@link #doCheck(String, String, String)}
	 * @param content Ԫ����
	 * @param sign ǩ��
	 * @param publicKey ��Կ
	 * @return true�ɹ�
	 */
	public static boolean doCheck(String content, String sign, byte[] publicKey) {
		return doCheck(content, sign, publicKey ,MD5withRSA);
	}
	
	/**
	 * ��ǩ
	 * <br>{@link #doCheck(String, String, byte[])}
	 * @param content Ԫ����
	 * @param sign	ǩ��
	 * @param publicKey ��Կ
	 * @param algorithm ǩ���㷨
	 * @return true�ɹ�
	 */
	public static boolean doCheck(String content, String sign, byte[] publicKey, String algorithm) {
		try {
			PublicKey pubKey = getPublicKeyFromX509("RSA", publicKey);
			Signature signature = Signature.getInstance(algorithm);
			signature.initVerify(pubKey);
			signature.update(content.getBytes("UTF-8"));
			return signature.verify(Base64.decodeBase64(sign.getBytes()));
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean doCheck(String content, byte[] sign, byte[] publicKey) {
		return doCheck(content, sign, publicKey ,MD5withRSA);
	}
	
	public static boolean doCheck(String content, byte[] sign, byte[] publicKey, String algorithm) {
		try {
			PublicKey pubKey = getPublicKeyFromX509("RSA", publicKey);
			Signature signature = Signature.getInstance(algorithm);
			signature.initVerify(pubKey);
			signature.update(content.getBytes("UTF-8"));
			return signature.verify(sign);
		} catch (Exception e) {
			return false;
		}
	}
	
	private static PublicKey getPublicKeyFromX509(String algorithm, byte[] publicKey) throws NoSuchAlgorithmException {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
			return keyFactory.generatePublic(new X509EncodedKeySpec(publicKey));
		} catch (InvalidKeySpecException ex) {
		}
		return null;
	}
}
