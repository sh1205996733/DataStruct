package cn.shangyc.printer;

import java.security.SecureRandom;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.SecretKey;
import javax.crypto.Cipher;

public class DES {
	public static void main(String args[]) {
		// 待加密内容
		String str = "测试内容";
		// 密码，长度要是 8 的倍数
		String password = "FAC41088";
		byte[] result = DES.encrypt(str.getBytes(), password);
		System.out.println("加密后：" + byteToHexString(result));
		// 直接将如上内容解密
		try {
			byte[] decryResult = DES.decrypt("A694B3F7043D6ED46D44A7AFEF1D6819".getBytes(""), password);
			System.out.println("解密后：" + new String(decryResult));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 加密
	 * 
	 * @param datasource
	 *            byte[]
	 * @param password
	 *            String
	 * @return byte[]
	 */
	public static byte[] encrypt(byte[] datasource, String password) {
		try {
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(password.getBytes());
			// 创建一个密匙工厂，然后用它把 DESKeySpec 转换成
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			// Cipher 对象实际完成加密操作
			Cipher cipher = Cipher.getInstance("DES");
			// 用密匙初始化 Cipher 对象
			cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
			// 现在，获取数据并加密
			// 正式执行加密操作
			return cipher.doFinal(datasource);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 湖北航天信息技术有限公司 二进制转 16 进制
	 * 
	 * @param bytes
	 * @return
	 */
	private static String byteToHexString(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		String sTemp;
		for (int i = 0; i < bytes.length; i++) {
			sTemp = Integer.toHexString(0xFF & bytes[i]);
			if (sTemp.length() < 2) {
				sb.append(0);
			}
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 解密
	 * 
	 * @param src
	 *            byte[]
	 * @param password
	 *            String
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] src, String password) throws Exception {
		// DES 算法要求有一个可信任的随机数源
		SecureRandom random = new SecureRandom();
		// 创建一个 DESKeySpec 对象
		DESKeySpec desKey = new DESKeySpec(password.getBytes());
		// 创建一个密匙工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		// 将 DESKeySpec 对象转换成 SecretKey 对象
		SecretKey securekey = keyFactory.generateSecret(desKey);
		// Cipher 对象实际完成解密操作
		Cipher cipher = Cipher.getInstance("DES");
		// 用密匙初始化 Cipher 对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, random);
		// 真正开始解密操作
		return cipher.doFinal(src);
	}
}