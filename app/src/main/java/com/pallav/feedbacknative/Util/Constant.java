package com.pallav.feedbacknative.Util;

import android.content.Context;
import android.util.Log;
import android.widget.SearchView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import static android.content.ContentValues.TAG;
import static com.pallav.feedbacknative.Util.SecureUrl.*;





public class Constant {

    // String certificate ="/res/xml/httpscertificate.cer";
    //  File file = new File(certificate);


  public static String CLOUDURL = "https://feedback-app.digital.schaeffler/";

  public static String TESTURL = "https://feedback-app.digital.schaeffler/qasfeedback/";

  // public static HttpsURLConnection BASEURL = SecureUrl.setUpHttpsConnection(url);






}





