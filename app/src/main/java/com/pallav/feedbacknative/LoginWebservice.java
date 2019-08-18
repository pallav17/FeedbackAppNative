package com.pallav.feedbacknative;

import android.util.Log;

import com.pallav.feedbacknative.Util.HeaderProperty;
import com.pallav.feedbacknative.Util.Services;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Node;
import org.kxml2.kdom.Element;




public class LoginWebservice  {

        //Namespace of the Webservice - can be found in WSDL
        private static String NAMESPACE = "http://tempuri.org/";


        //Webservice URL - WSDL File location
        private static String URL = "http://www.as-mexico.com.mx/feedback/Webservice1.asmx?WSDL";//Make sure you changed IP address
        //SOAP Action URI again Namespace + Web method name
        private static String SOAP_ACTION = "http://tempuri.org/";

        public static boolean invokeLoginWS(String userName,String passWord, String webMethName) {
            boolean loginStatus = false;
            boolean result = false;

            // Create request
            SoapObject loginRequest = new SoapObject(NAMESPACE, webMethName);
            // Property which holds input parameters
            PropertyInfo unamePI = new PropertyInfo();
            PropertyInfo passPI = new PropertyInfo();
            // Set Username
            unamePI.setName("Email");
            // Set Value
            unamePI.setValue(userName);
            // Set dataType
            unamePI.setType(String.class);
            // Add the property to request object
            loginRequest.addProperty(unamePI);
            //Set Password
            passPI.setName("Password");
            //Set dataType
            passPI.setValue(passWord);
            //Set dataType
            passPI.setType(String.class);
            //Add the property to request object
            loginRequest.addProperty(passPI);

          result = invokeApi(loginRequest,webMethName);


            return result;


        }


        public static boolean invokeApi(SoapObject request, String webMethName)

        {
            boolean responseStatus = false;
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
                // Assign it to  boolean variable variable
                responseStatus = Boolean.parseBoolean(response.toString());
                Log.w("ResponseStatuslog",Boolean.toString(responseStatus));

            } catch (Exception e) {
                //Assign Error Status true in static variable 'errored'
                // CheckDNLoginActivity.errored = true;
                e.printStackTrace();
            }
            //Return booleam to calling object
            return responseStatus;
        }


        }



