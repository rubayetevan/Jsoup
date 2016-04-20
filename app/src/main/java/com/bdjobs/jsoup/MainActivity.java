package com.bdjobs.jsoup;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.provider.Settings;
import android.renderscript.Element;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    String url = "http://www.prothom-alo.com/";
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Title().execute();


    }

    private class Title extends AsyncTask<Void, Void, Void> {
        String title;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Android Basic JSoup Tutorial");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document document = Jsoup.connect(url).get();
                Elements element = document.select("div[id=widget_50158]");
                Elements elements = element.select(".title");
                Elements links = elements.select("a[href]");
                //title = Html.fromHtml( elements.toString()).toString();
                title=links.toString();
                title = url+link(title);
                System.out.println(title);

                Document document1 = Jsoup.connect(title).get();
                Elements elements1 = document1.select("div[itemprop=articleBody]");
                title=Html.fromHtml( elements1.toString()).toString();


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set title into TextView
            TextView txttitle = (TextView) findViewById(R.id.titletxt);
            txttitle.setText(title);
            mProgressDialog.dismiss();
        }
    }


    public static String link(String data) {
        //String test = "qazwsx<a href=\"http://stackoverflow.com\">Take me to StackOverflow</a>fdgfdhgfd"
                //+ "<a href=\"http://stackoverflow2.com\">Take me to StackOverflow2</a>dcgdf";

        String regex = "<a href=(\"[^\"]*\")[^<]*</a>";

        Pattern p = Pattern.compile(regex);

        Matcher m = p.matcher(data);
        System.out.println(m.replaceAll("$1"));
        String d = m.replaceAll("$1").toString();
        return d.replaceAll("\"","");
    }
}
