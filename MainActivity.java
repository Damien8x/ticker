package com.example.damiensudol.ticker;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button submit = (Button) findViewById(R.id.submit);



        submit.setOnClickListener(MainActivity.this);

    }

    @Override

        public void onClick(View v) {
        EditText symbol = (EditText) findViewById(R.id.symbol);
        TextView instruct = (TextView) findViewById(R.id.instruct);

        String stock = symbol.getText().toString();


        String feedUrl = format(stock);
        Feed feed = new Feed(feedUrl);
        feed.fetchXML();

while(feed.parsingComplete == true);

/*
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(feedUrl));
        startActivity(i);

*/


            instruct.setText(feed.getChange());

    }
    /*public void setText(String change){
        TextView instruct = (TextView) findViewById(R.id.instruct);
        instruct.setText(change);

    }
*/
    public static String format(String stock){
        String formattedStock;

        formattedStock = "https://query.yahooapis.com/v1/public/yql?q=select%20Change%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(%22"+stock+"%22)&format=xml&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
        return formattedStock;
    }
}
