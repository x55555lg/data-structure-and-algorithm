package com;

import com.alibaba.fastjson.JSON;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Xulg
 * @since 2021-11-03 10:27
 */
public class Demo {

    public static void main(String[] args) {
        YdQyCardDetail card = new YdQyCardDetail();
        card.setId(1);
        card.setOrderId(1);
        card.setTbCardNo("fsafsdfsdfasfas");
        card.setUseStatus("aa");
        card.setSkuCode("4322");
        card.setSkuName("名称");
        card.setExpireTime("2021-10-31");
        card.setAmount(123);
        List<YdQyCardDetail> list = new ArrayList<>();
        list.add(card);
        BaseResponse<List<YdQyCardDetail>> response = new BaseResponse<>();
        response.setCode("00");
        response.setMsg("SUCCESS");
        response.setData(list);
        System.out.println(JSON.toJSONString(response, true));


        {
            String CARD_SECRET_KEY_PREFIX = "!@#$%%^%^df125$%$%$%$#%";
            String cardKey = DesUtil.encrypt("480961965012321738",
                    CARD_SECRET_KEY_PREFIX + "TOm7E2wU#vHkvEWTOq6fo4fdO$1ZjgLf");
            System.out.println(cardKey);
        }
    }

    public static class BaseResponse<T> implements Serializable {
        private static final long serialVersionUID = 1L;
        private String msg = "";
        private String code = "00";
        private T data;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }

    public static class YdQyCardDetail implements Serializable {
        private static final long serialVersionUID = 1L;
        private Integer id;
        private Integer orderId;
        private String tbCardNo;
        private String useStatus;
        private String skuCode;
        private String skuName;
        private String expireTime;
        private Integer amount;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getOrderId() {
            return orderId;
        }

        public void setOrderId(Integer orderId) {
            this.orderId = orderId;
        }

        public String getTbCardNo() {
            return tbCardNo;
        }

        public void setTbCardNo(String tbCardNo) {
            this.tbCardNo = tbCardNo;
        }

        public String getUseStatus() {
            return useStatus;
        }

        public void setUseStatus(String useStatus) {
            this.useStatus = useStatus;
        }

        public String getSkuCode() {
            return skuCode;
        }

        public void setSkuCode(String skuCode) {
            this.skuCode = skuCode;
        }

        public String getSkuName() {
            return skuName;
        }

        public void setSkuName(String skuName) {
            this.skuName = skuName;
        }

        public String getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(String expireTime) {
            this.expireTime = expireTime;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }


    }

    @SuppressWarnings("all")
    public static class DesUtil {
        private final static String DES = "DES";

        public static void main(String[] args) throws Exception {
            //		String data = "123 456";
            //		String key = "wang!@#$%";
            //		System.err.println(encrypt(data, key));
            //		System.err.println(decrypt(encrypt(data, key), key));
            //		String card = decrypt("IDwGW/Hm+mhu8zjZl4ttq9HcUWgXXwpl", "!@#$%%^%^df125$%$%$%$#%"+"TOm7E2wU#vHkvEWTOq6fo4fdO$1ZjgLf");
            //		System.out.println(card);

            String cc = encrypt("689440872728064781", "!@#$%%^%^df125$%$%$%$#%" + "TOm7E2wU#vHkvEWTOq6fo4fdO$1ZjgLf");
            System.out.println(cc);
        }

        /**
         * Description 根据键值进行加密
         *
         * @param data
         * @param key  加密键byte数组
         * @return
         * @throws Exception
         */
        public static String encrypt(String data, String key) {
            byte[] bt;
            try {
                bt = encrypt(data.getBytes(), key.getBytes());
                String strs = new BASE64Encoder().encode(bt);
                return strs;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * Description 根据键值进行解密
         *
         * @param data
         * @param key  加密键byte数组
         * @return
         * @throws IOException
         * @throws Exception
         */
        public static String decrypt(String data, String key) throws IOException, Exception {
            if (data == null)
                return null;
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] buf = decoder.decodeBuffer(data);
            byte[] bt = decrypt(buf, key.getBytes());
            return new String(bt);
        }

        /**
         * Description 根据键值进行加密
         *
         * @param data
         * @param key  加密键byte数组
         * @return
         * @throws Exception
         */
        private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
            // 生成一个可信任的随机数源
            SecureRandom sr = new SecureRandom();

            // 从原始密钥数据创建DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(key);

            // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            SecretKey securekey = keyFactory.generateSecret(dks);

            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance(DES);

            // 用密钥初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

            return cipher.doFinal(data);
        }

        /**
         * Description 根据键值进行解密
         *
         * @param data
         * @param key  加密键byte数组
         * @return
         * @throws Exception
         */
        private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
            // 生成一个可信任的随机数源
            SecureRandom sr = new SecureRandom();

            // 从原始密钥数据创建DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(key);

            // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            SecretKey securekey = keyFactory.generateSecret(dks);

            // Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance(DES);

            // 用密钥初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

            return cipher.doFinal(data);
        }
    }

}
