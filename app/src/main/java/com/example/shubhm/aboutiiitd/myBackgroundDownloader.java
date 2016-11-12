package com.example.shubhm.aboutiiitd;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Shubhm on 12-11-2016.
 */
public class myBackgroundDownloader extends AsyncTask<String,Void,String> {
    static String str="not getting";
    @Override
    protected String doInBackground(String... strings) {


        try{
            try {

             //   Document document = Jsoup.connect(url).get();
                str=mydownloader(strings[0]);//downloadUrl(strings[0]);
                return str;

            } catch (Exception e) {
                str="Unable to retrieve web page. URL may be invalid.";
                return str;
            }


        }catch(Exception e){}
       return "haha";
    }


    @Override
    protected void onPostExecute(String s) {

        MainActivity.textView.setText(strparser(str));
        super.onPostExecute(s);

    }


   String  mydownloader(String url)
   { String html = "Nothing Fetched";
       try {
           HttpClient client = new DefaultHttpClient();
           HttpGet request = new HttpGet(url);
           HttpResponse response = client.execute(request);


           InputStream in = response.getEntity().getContent();
           BufferedReader reader = new BufferedReader(new InputStreamReader(in));
           StringBuilder str = new StringBuilder();
           String line = null;
           while ((line = reader.readLine()) != null) {
               str.append(line);
           }
           in.close();
           html = str.toString();
           Document doc = Jsoup.parseBodyFragment(html);
          html= doc.getElementsByTag("p").toString();
        // html=doc.body().toString();


       }
       catch(Exception e){
           Toast.makeText(MainActivity.context,"There is a problem",Toast.LENGTH_SHORT).show();}
return html;
   }


    String strparser(String str)
    {
        String mynewstr="";
        char[] arr=str.toCharArray();
        boolean skip=false;
        for(int i=0;i<str.length();i++)
        {
            if(arr[i]=='<'){skip=true;}
            if(arr[i]=='>'){skip=false;}


            if(!skip)
            {
                if(arr[i]!='>')
                mynewstr+=arr[i]+"";
            }
        }
        String mystr=mynewstr.replace("&nbsp;","");
        return mystr.substring(mystr.lastIndexOf("Report")+6);//mynewstr.replace("&nbsp;","");
    }
}
