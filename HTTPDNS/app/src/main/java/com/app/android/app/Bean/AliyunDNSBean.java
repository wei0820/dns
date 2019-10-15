package com.app.android.app.Bean;

import java.util.List;


public class AliyunDNSBean {
    private String host;
    private int ttl;
    private int origin_ttl;
    private List<String> ips;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public int getOrigin_ttl() {
        return origin_ttl;
    }

    public void setOrigin_ttl(int origin_ttl) {
        this.origin_ttl = origin_ttl;
    }

    public List<String> getIps() {
        return ips;
    }

    public void setIps(List<String> ips) {
        this.ips = ips;
    }
}
