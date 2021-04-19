package com.pallav.feedbacknative;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Handler;

import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.pallav.feedbacknative.Util.SecureUrl;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;


import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import static android.content.ContentValues.TAG;
import static java.security.KeyStore.getInstance;

public class splashActivity extends AppCompatActivity {
    private ProgressBar progressbar;
    static String cerurl;
    AssetManager assetManager;
  public static   InputStream is;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressbar = findViewById(R.id.progressbar);


         assetManager = getAssets();
        try {
            is = getAssets().open("dercertificate.cer");
        } catch (IOException e) {
            e.printStackTrace();
        }
        getSupportActionBar().hide();

        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(splashActivity.this,CheckLogin.class));
                finish();
            }
        }, secondsDelayed * 2000);


        setUpHttpsConnection();
       /* try {
            addCertificate();

        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }


    public void addCertificate() throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {


        KeyStore trustStore  = getInstance(KeyStore.getDefaultType());
        trustStore.load(null);//Make an empty store
        InputStream instream = getAssets().open("dercertificate.cer");



        BufferedInputStream bis = new BufferedInputStream(instream);

        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        while (bis.available() > 0) {
            Certificate cert = cf.generateCertificate(bis);
            trustStore.setCertificateEntry("fiddler"+bis.available(), cert);
        }
    }

    public static String setUpHttpsConnection() {
        try {
            // Load CAs from an InputStream
            // (could be from a resource or ByteArrayInputStream or ...)
            CertificateFactory cf = CertificateFactory.getInstance("X.509");

            // My CRT file that I put in the assets folder
            // I got this file by following these steps:
            // * Go to https://littlesvr.ca using Firefox
            // * Click the padlock/More/Security/View Certificate/Details/Export
            // * Saved the file as littlesvr.crt (type X.509 Certificate (PEM))
            // The MainActivity.context is declared as:
            // public static Context context;
            // And initialized in MainActivity.onCreate() as:
            // MainActivity.context = getApplicationContext();
            //InputStream is = getAssets().open("httpscertificate.cer");
            InputStream caInput = new BufferedInputStream(is);
            Certificate ca = cf.generateCertificate(caInput);
            System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());

            // Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            // Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            // Create an SSLContext that uses our TrustManager
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, tmf.getTrustManagers(), null);

            // Tell the URLConnection to use a SocketFactory from our SSLContext
            URL url = new URL("https://feedback-app.digital.schaeffler/");
           HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
           urlConnection.setSSLSocketFactory(context.getSocketFactory());
           cerurl = urlConnection.getURL().toString();
           return cerurl;

        } catch (Exception ex) {
            Log.e(TAG, "Failed to establish SSL connection to server: " + ex.toString());
            return null;
        }


    }




}
