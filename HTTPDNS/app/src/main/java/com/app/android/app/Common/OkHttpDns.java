package com.app.android.app.Common;

/**
 * Created by CJH on 2019/3/20.
 */
public class OkHttpDns /*implements Dns*/ {
    /*private static final String ACCOUNT_ID = "113179";
    HttpDnsService httpdns;//httpdns 解析服务
    private static OkHttpDns instance = null;

    private OkHttpDns(Context context) {
        this.httpdns = HttpDns.getService(context, ACCOUNT_ID);
        //this.httpdns.setCachedIPEnabled(true);
    }

    public static OkHttpDns getInstance(Context context) {
        if (instance == null) {
            instance = new OkHttpDns(context);
        }
        return instance;
    }

    @Override
    public List<InetAddress> lookup(String hostname) throws UnknownHostException {
        //通过异步解析接口获取ip
        String ip = httpdns.getIpByHost(hostname);
        if (ip != null) {
            //如果ip不为null，直接使用该ip进行网络请求
            List<InetAddress> inetAddresses = Arrays.asList(InetAddress.getAllByName(ip));
            LogUtils.out("OkHttpDns inetAddresses:" + inetAddresses);
            return inetAddresses;
        }
        //如果返回null，走系统DNS服务解析域名
        return Dns.SYSTEM.lookup(hostname);
    }*/

}
