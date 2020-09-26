package com.lg.fu_za_du;

import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;

/**
 * 时间复杂度
 *
 * @author Xulg
 * Created in 2019-10-23 15:08
 */
@SuppressWarnings("ManualArrayCopy")
public class SpaceFuZaDu {

    public static void main(String[] args) {
        Sign sign = new Sign(SignAlgorithm.SHA256withRSA);
        String privateKey = sign.getPrivateKeyBase64();
        String publicKey = sign.getPublicKeyBase64();
        System.out.println("私钥\r\n" + privateKey);
        System.out.println("公钥\r\n" + publicKey);
        String data = "<Result>\n" +
                "\t<Status>0</Status>\n" +
                "\t<ResultMsg>success</ResultMsg>\n" +
                "\t<OrderID>test000002</OrderID>\n" +
                "\t<QList>\n" +
                "\t\t<QDetail>\n" +
                "\t\t\t<EndTime>2020-07-06</EndTime>\n" +
                "\t\t\t<StartTime>2020-05-07</StartTime>\n" +
                "\t\t\t<Qcode>0007509,0009370</Qcode>\n" +
                "\t\t\t<QPrice>100</QPrice>\n" +
                "\t\t</QDetail>\n" +
                "\t</QList>\n" +
                "\t<Sms>水果券密码：0007509,0009370，每个面值1元。凭密码可在果麦合作门店使用。登录 www.guomailife.com 查询适用门店 。该券不找零，不兑现，不与其它优惠同享（包括会员卡），有效期至2020-07-06。客服：0571-85808891</Sms>\n" +
                "</Result>";

        byte[] signDataBytes = sign.sign(data.getBytes());
        boolean verify = sign.verify(data.getBytes(), signDataBytes);
        System.out.println(verify);
    }

    /*
     * 例如对一个数组array进行操作，返回一个int值
     *      int calc(int[] array) {
     *          return 0;
     *      }
     * 如果额外需要的空间是有限的，和array的大小没有关系，则空间复杂度为O(1)，
     * 否则就是别的复杂度了，例如O(n)。
     */

    /**
     * 空间复杂度o(1)
     */
    public int[] copy(int[] array) {
        int[] newArr = new int[array.length];
        for (int i = 0; i < newArr.length; i++) {
            newArr[i] = array[i];
        }
        return newArr;
    }

}
