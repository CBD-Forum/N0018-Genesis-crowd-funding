package com.fbd.core.app.thirdtrusteeship.common;

/**
 * 资金托管 constants.
 * 
 */
public class TrusteeshipConstants {

    public static final String Package = "com.esoft.jdp2p.trusteeship";

    public static final class Status {
        /**
         * 等待发送
         */
        public static final String UN_SEND = "un_send";
        /**
         * 已发送
         */
        public static final String SENDED = "sended";
        /**
         * 通过
         */
        public static final String PASSED = "passed";
        /**
         * 未通过
         */
        public static final String REFUSED = "refused";
        /**
         * 无响应
         */
        public static final String NO_RESPONSE = "no_response";
        // 查询状态为部分成功
        public static final String TRANSSTAT_P = "P";
    }

    public static final class OperationType {
        /**
         * 开户
         */
        public static final String CREATE_ACCOUNT = "create_account";

        /**
         * 充值
         */
        public static final String RECHARGE = "recharge";

        /**
         * 投标
         */
        public static final String INVEST = "invest";

        /**
         * 放款
         */
        public static final String GIVE_MOENY_TO_BORROWER = "give_moeny_to_borrower";

        /**
         * 还款
         */
        public static final String REPAY = "repay";

        /**
         * 提现
         */
        public static final String WITHDRAW_CASH = "withdraw_cash";
    }

    public static final class Config {
        /**
         * 商户号(test)
         */
        public static final String MER_CODE = "808801";
        /**
         * 商户号
         */
        // public static final String MER_CODE = "026463";

        /**
         * MD5证书(test)
         */
        public static final String CERT = "GPhKt7sh4dxQQZZkINGFtefRKNPyAj8S00cgAwtRyy0ufD7alNC28xCBKpa6IU7u54zzWSAv4PqUDKMgpOnM7fucO1wuwMi4RgPAnietmqYIhHXZ3TqTGKNzkxA55qYH";
        /**
         * MD5证书
         */
        // public static final String CERT =
        // "73297299406009425447973854877798120053727617787832991250033241161797846171036507793589687092958544609009581912452655868100084322";

        /**
         * 3des base64 key(test)
         */
        public static final String THREE_DES_BASE64_KEY = "ICHuQplJ0YR9l7XeVNKi6FMn";
        /**
         * 3des base64 key
         */
        // public static final String THREE_DES_BASE64_KEY =
        // "1jpxjcrUokjKH0AownKB0fQL";

        /**
         * 3des iv(向量)(test)
         */
        public static final String THREE_DES_IV = "2EDxsEfp";
        /**
         * 3des iv(向量)
         */
        // public static final String THREE_DES_IV = "1jpxjcrU";

        /**
         * 3des 加密方法／运算模式／填充模式
         */
        public static final String THREE_DES_ALGORITHM = "DESede/CBC/PKCS5Padding";

    }

    /**
     * 请求地址
     * 
     * @author Administrator
     * 
     */
    public static final class RequestUrl {
        /**
         * 开户(test)
         */
        public static final String CREATE_IPS_ACCT = "http://p2p.ips.net.cn/CreditWeb/createIpsAcct.aspx";
        /**
         * 开户
         */
        // public static final String CREATE_IPS_ACCT =
        // "https://p2p.ips.com.cn/CreditWeb/createIpsAcct.aspx ";

        /**
         * 充值(test)
         */
        public static final String RECHARGE = "http://p2p.ips.net.cn/CreditWeb/dpTrade.aspx";

        /**
         * 充值
         */
        // public static final String RECHARGE =
        // "https://p2p.ips.com.cn/CreditWeb/dpTrade.aspx";

        /**
         * 投标(test)
         */
        public static final String INVEST = "http://p2p.ips.net.cn/CreditWeb/tenderTrade.aspx";

        /**
         * 投标
         */
        // public static final String INVEST =
        // "https://p2p.ips.com.cn/CreditWeb/tenderTrade.aspx";

        /**
         * 还款(test)
         */
        public static final String REPAY = "http://p2p.ips.net.cn/CreditWeb/repaymentTrade.aspx";

        /**
         * 还款
         */
        // public static final String REPAY =
        // "https://p2p.ips.com.cn/CreditWeb/repaymentTrade.aspx";

        /**
         * 提现(test)
         */
        public static final String WITHDRAW_CASH = "http://p2p.ips.net.cn/CreditWeb/dwTrade.aspx";

        /**
         * 提现
         */
        // public static final String WITHDRAW_CASH =
        // "https://p2p.ips.com.cn/CreditWeb/dwTrade.aspx";
    }

    /**
     * 返回地址（web）
     */
    public static final class ResponseWebUrl {
        /**
         * 地址前缀（正式）
         */
        // public static final String PRE_RESPONSE_URL =
        // "http://www.minzizhijia.com/trusteeship_return_web/";
        /**
         * 地址前缀(测试)
         */
        public static final String PRE_RESPONSE_URL = "http://localhost:7070/zhongdahengtong/trusteeship_return_web/";
        // public static final String PRE_RESPONSE_URL =
        // "http://115.28.136.88/trusteeship_return_web/";

    }

    /**
     * 返回地址（server to server）
     */
    public static final class ResponseS2SUrl {
        /**
         * 地址前缀(正式)
         */
        // public static final String PRE_RESPONSE_URL =
        // "http://www.minzizhijia.com/trusteeship_return_s2s/";
        /**
         * 地址前缀(测试)
         */
        public static final String PRE_RESPONSE_URL = "http://localhost:7070/zhongdahengtong/trusteeship_return_s2s/";
        // public static final String PRE_RESPONSE_URL =
        // "http://115.28.136.88/trusteeship_return_s2s/";
    }

}
