package com.pallav.feedbacknative.Util;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;

public class GetApi {

    private static String NAMESPACE = "http://tempuri.org/";
    //Webservice URL - WSDL File location
    private static String URL = "http://www.as-mexico.com.mx/feedback/Webservice1.asmx?WSDL";//Make sure you changed IP address
    //SOAP Action URI again Namespace + Web method name
    private static String SOAP_ACTION = "http://tempuri.org/";

    public void getResponse(String webMethName)
    {

        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            JSONArray jsonArray = new JSONArray(response);
            String[] strArr = new String[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                strArr[i] = jsonArray.getString(i);
            }

            Log.d("Response", Arrays.toString(strArr));

        } catch (Exception e) {
            //Assign Error Status true in static variable 'errored'
            // CheckDNLoginActivity.errored = true;
            e.printStackTrace();
        }


    }

}