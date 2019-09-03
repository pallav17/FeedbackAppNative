package com.pallav.feedbacknative;

import android.util.Log;

import com.pallav.feedbacknative.Util.HeaderProperty;
import com.pallav.feedbacknative.Util.Services;

import org.json.JSONArray;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Node;
import org.kxml2.kdom.Element;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;


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

    public static boolean invokeInsertFeedbackWS(String  Subject,String Recepient_Email,String Description, String Suggestion, String Email, String Rating, String webMethName)  {

        boolean result = false;

        // Create request
        SoapObject insertfeedbackRequest = new SoapObject(NAMESPACE, webMethName);
        // Property which holds input parameters
        PropertyInfo SubjectPI = new PropertyInfo();
        PropertyInfo Recepient_EmailPI = new PropertyInfo();
        PropertyInfo DescriptionPI = new PropertyInfo();
        PropertyInfo SuggestionPI = new PropertyInfo();
        PropertyInfo EmailPI = new PropertyInfo();
        PropertyInfo RatingPI = new PropertyInfo();


        SubjectPI.setName("Subject");
        SubjectPI.setValue(Subject);
        SubjectPI.setType(String.class);
        insertfeedbackRequest.addProperty(SubjectPI);

        Recepient_EmailPI.setName("Recepient_Email");
        Recepient_EmailPI.setValue(Recepient_Email);
        Recepient_EmailPI.setType(String.class);
        insertfeedbackRequest.addProperty(Recepient_EmailPI);

        DescriptionPI.setName("Description");
        DescriptionPI.setValue(Description);
        DescriptionPI.setType(String.class);
        insertfeedbackRequest.addProperty(DescriptionPI);

        SuggestionPI.setName("Suggestion");
        SuggestionPI.setValue(Suggestion);
        SuggestionPI.setType(String.class);
        insertfeedbackRequest.addProperty(SuggestionPI);

        EmailPI.setName("Email");
        EmailPI.setValue(Email);
        EmailPI.setType(String.class);
        insertfeedbackRequest .addProperty(EmailPI);

        RatingPI.setName("Rating");
        RatingPI.setValue(Rating);
        RatingPI.setType(String.class);
        insertfeedbackRequest .addProperty(RatingPI);

        result = invokeApi(insertfeedbackRequest,webMethName);


        return result;


    }




    public static boolean invokeSignupInsertUser(String  FirstName,String  LastName,String  Office,  String Email , String Password, String IsActive, String IsDelete, String webMethName)  {

        boolean result = false;

        // Create request
        SoapObject InsertUserRequest = new SoapObject(NAMESPACE, webMethName);
        // Property which holds input parameters
        PropertyInfo FirstNamePI = new PropertyInfo();
        PropertyInfo LastNamePI = new PropertyInfo();
        PropertyInfo OfficePI = new PropertyInfo();
        PropertyInfo EmailPI = new PropertyInfo();
        PropertyInfo PasswordPI = new PropertyInfo();
        PropertyInfo IsActivePI = new PropertyInfo();
        PropertyInfo IsDeletePI = new PropertyInfo();


        FirstNamePI.setName("FirstName");
        FirstNamePI.setValue(FirstName);
        FirstNamePI.setType(String.class);
        InsertUserRequest.addProperty(FirstNamePI);

        LastNamePI.setName("LastName");
        LastNamePI.setValue(LastName);
        LastNamePI.setType(String.class);
        InsertUserRequest.addProperty(LastNamePI);

        OfficePI.setName("Office");
        OfficePI.setValue(Office);
        OfficePI.setType(String.class);
        InsertUserRequest.addProperty(OfficePI);

        EmailPI.setName("Email");
        EmailPI.setValue(Email);
        EmailPI.setType(String.class);
        InsertUserRequest.addProperty(EmailPI);


        PasswordPI.setName("Password");
        PasswordPI.setValue(Password);
        PasswordPI.setType(String.class);
        InsertUserRequest.addProperty(PasswordPI);

        IsActivePI.setName("IsActive");
        IsActivePI.setValue(IsActive);
        IsActivePI.setType(String.class);
        InsertUserRequest.addProperty(IsActivePI);

        IsDeletePI.setName("IsDelete");
        IsDeletePI.setValue(IsDelete);
        IsDeletePI.setType(String.class);
        InsertUserRequest.addProperty(IsDeletePI);

        result = invokeApi(InsertUserRequest,webMethName);

        return result;


    }

    public  static String invokeDisplayFeedbackWS( String Email, String Token , String webMethName)    {

        boolean result = false;
        String Feedbacks;

        // Create request
        SoapObject DisplayFeedbackRequest = new SoapObject(NAMESPACE, webMethName);
        // Property which holds input parameters

        PropertyInfo EmailPI = new PropertyInfo();
        PropertyInfo TokenPI = new PropertyInfo();


        EmailPI.setName("Email");
        EmailPI.setValue(Email);
        EmailPI.setType(String.class);
        DisplayFeedbackRequest.addProperty(EmailPI);

        TokenPI.setName("Token");
        TokenPI.setValue(Token);
        TokenPI.setType(String.class);
        DisplayFeedbackRequest.addProperty(TokenPI);

        Feedbacks = invokeApiResponse(DisplayFeedbackRequest,webMethName);


        return Feedbacks;



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



    public static String invokeApiResponse(SoapObject request, String webMethName)

    {


        boolean responseStatus = false;
        String responseArray = null;
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);


        // AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(URL);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);


            Log.d("SOAP RESPONSE...............","This is response.............."+androidHttpTransport.responseDump);


            // Get the response
            // SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            SoapObject response = (SoapObject) envelope.bodyIn;

            // SoapObject obj =(SoapObject) response.getProperty(0);

           /* for(int i=0; i<obj.getPropertyCount(); i++)
            {
                int id= Integer.parseInt(obj.getProperty(i).toString());
                //Do what you want
            }*/





            Log.d("SOAP RESPONSE...............","This is response.............."+response.toString());


            if(response != null)
            {
                responseStatus = true;
                responseArray = response.toString();

                Log.d("Response,........................", responseArray);
                JSONArray jarray =new JSONArray(responseArray);

                Log.d("Response,........................", jarray.toString());

                // System.out.println("*****JARRAY*****"+jArray);


                Log.w("ResponseStatuslog",Boolean.toString(responseStatus));
                Log.d("This is the response", jarray.toString() );
            }





        } catch (Exception e) {
            //Assign Error Status true in static variable 'errored'
            // CheckDNLoginActivity.errored = true;
            e.printStackTrace();
        }
        //Return booleam to calling object
        return responseArray;
    }


        }



