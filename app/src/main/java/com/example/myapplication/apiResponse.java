package com.example.myapplication;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class apiResponse {
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public static String readError(ResponseBody res) {
        String errRes = "";
        try {
            errRes = res.string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return errRes.replaceAll("\"", "");
    }
}
