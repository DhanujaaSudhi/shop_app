package com.android.shoppingzoo.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.shoppingzoo.R;

public class contact extends AppCompatActivity {
    TextView call,feedback;
    ImageView facebook, instagram,share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        call = findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+call));//change the number
                startActivity(callIntent);

            }
        });


        feedback = (TextView) findViewById(R.id.feedback);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                String uriText = "mailto:" + Uri.encode("laxhamenswear@gmail.com") + "?subject=" +
                        Uri.encode("your email id ") + "&body=" + Uri.encode("");

                Uri uri = Uri.parse(uriText);
                intent.setData(uri);
                startActivity(Intent.createChooser(intent, "Send Email"));

            }
        });



        facebook = findViewById(R.id.facebook);
        facebook.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                gotoUrl("https://wa.me/9342935812");


            }

            private void gotoUrl(String s) {
                Uri uri = Uri.parse(s);
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });


        instagram = findViewById(R.id.instagram);
        instagram.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                gotoUrl("https://instagram.com/laxha_pollachi?igshid=YmMyMTA2M2Y=");


            }


            private void gotoUrl(String s) {
                Uri uri = Uri.parse(s);
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });



        share = (ImageView) findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                String sharebody = "look all Programmings";
                String subject = "https://play.google.com/store/apps/details?id="+ getApplicationContext().getPackageName();
                i.putExtra(Intent.EXTRA_SUBJECT,sharebody);
                i.putExtra(Intent.EXTRA_TEXT,subject);
                startActivity(Intent.createChooser(i,"Seek my vision"));

            }
        });
    }
}