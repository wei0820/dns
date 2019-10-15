package com.app.android.app.Common;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class GetHttpRequest extends Thread {

    private OnCallbackListener mListener;
    private String urlPath;
    private String message;


    public GetHttpRequest() {
    }

    public GetHttpRequest(String url) {
        this.urlPath = url;
    }

    @Override
    public void run() {
        URL url = null;
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        try {
            url = new URL(urlPath);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(10 * 1000);
            inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));//转成utf-8格式
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            message = response.toString();

            if (mListener != null) {
                mListener.onSuccess(message);
            }

        } catch (Exception e) {
            if (mListener != null) {
                mListener.onError("请求出现错误：" + e.getMessage());
            }
            e.printStackTrace();
        } finally {
            connection.disconnect();

            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


    public void setUrl(String urlPath) {
        this.urlPath = urlPath;
    }

    public void setOnCallbackListener(OnCallbackListener listener) {
        mListener = listener;
    }
}
