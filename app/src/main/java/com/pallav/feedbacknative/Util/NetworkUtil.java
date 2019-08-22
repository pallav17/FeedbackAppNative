package com.pallav.feedbacknative.Util;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtil {

    public static URL buildURL(String reponseUrl) {


        Uri uri = Uri.parse(reponseUrl);
        URL url = null;

        try {
            url = new URL(uri.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }


        return url;

    }


public static String getResponse(URL buildUrl) throws IOException {

    HttpURLConnection urlConnection = (HttpURLConnection) buildUrl.openConnection();

    try {
        InputStream inputStream = urlConnection.getInputStream();
        Scanner scanner = new Scanner(inputStream);
        scanner.useDelimiter("\\A");

        if (scanner.hasNext()) {
            return scanner.next();
        } else {
            return null;
        }
    }
        finally{
            urlConnection.disconnect();
        }
    }

}


