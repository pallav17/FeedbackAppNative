package com.pallav.feedbacknative.Util;

import android.icu.util.RangeValueIterator;
import android.net.Uri;
import android.util.Log;

import com.pallav.feedbacknative.LoginWebservice;

import org.json.JSONArray;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Element;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;


public class GetApi {

    private static String NAMESPACE = "http://tempuri.org/";
    //Webservice URL - WSDL File location
    private static String URL = "http://www.as-mexico.com.mx/feedback/Webservice1.asmx";//Make sure you changed IP address
    //SOAP Action URI again Namespace + Web method name
    private static String SOAP_ACTION = "http://tempuri.org/";
    public static String responseJSON;
   //static SoapSerializationEnvelope envelope;




    public static String invokeJSONWS(String methName) {
        // Create request
        SoapObject request = new SoapObject(NAMESPACE,methName);
        // Property which holds input parameters
       /* PropertyInfo paramPI = new PropertyInfo();
        // Set Name
        // Set dataType
        paramPI.setType(String.class);
        // Add the property to request object
        request.addProperty(paramPI);*/
        // Create envelope

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
           SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
       // envelope.headerOut = new Element[1];
       // envelope.headerOut[0] = LoginWebservice.buildAuthHeader();
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // Invole web service
            androidHttpTransport.call(SOAP_ACTION+methName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            Log.e("Soap Res",response.toString());
            // Assign it to static variable
            responseJSON = response.toString();
            JSONArray json = new JSONArray(responseJSON);
            Log.d("Response", json.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
            return responseJSON;

    }

   /* public String[] getResponse(String webMethName)
    {
        String[] strArr = new String[1000];

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
             strArr = new String[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                strArr[i] = jsonArray.getString(i);
            }

            Log.d("Response", Arrays.toString(strArr));

        } catch (Exception e) {
            //Assign Error Status true in static variable 'errored'
            // CheckDNLoginActivity.errored = true;
            e.printStackTrace();
        }
            return strArr;

    }*/




}