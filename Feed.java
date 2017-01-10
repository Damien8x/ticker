package com.example.damiensudol.ticker;

/**
 * Created by damiensudol on 1/8/17.
 */

import java.net.URL;

import java.io.InputStream;
import java.net.HttpURLConnection;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import android.util.Log;


public class Feed {


    private String change= "";
    private String urlString = null;
    private XmlPullParserFactory xmlFactoryObject;
    public volatile boolean parsingComplete = true;


    public Feed (String url){
        this.urlString = url;
    }


    public String getChange(){
        return change;
    }

    public void setChange(String change){
        this.change = change;
    }

    public void parseXMLAndStoreIt(XmlPullParser myParser) {
        int event;
        String text="";

        try{

                event = myParser.getEventType();
                while (event != XmlPullParser.END_DOCUMENT){
                    String name=myParser.getName();

                        switch(event) {

                            case XmlPullParser.START_TAG:
                                break;

                            case XmlPullParser.TEXT:
                                text = myParser.getText();
                                break;

                            case XmlPullParser.END_TAG:
                                if (name.equals("Change")) {
                                    setChange(text);
                                }else{
                                }
                                break;
                        }
                    event = myParser.next();
                        }
            parsingComplete = false;

        }catch (Exception e){
            setChange("didn't work");
            e.printStackTrace();
        }
    }
    public void fetchXML(){
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {

                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);

                    // Starts the query
                    conn.connect();
                    InputStream stream = conn.getInputStream();

                    xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser myParser = xmlFactoryObject.newPullParser();

                    myParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    myParser.setInput(stream, null);

                    parseXMLAndStoreIt(myParser);
                    stream.close();
                }

                catch (Exception e) {
                }
            }
        });
        thread.start();
    }

}
