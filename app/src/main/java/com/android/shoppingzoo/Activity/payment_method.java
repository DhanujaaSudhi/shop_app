package com.android.shoppingzoo.Activity;

import static com.android.shoppingzoo.Model.Utils.TAG_medicine_list;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.shoppingzoo.Model.Product;
import com.android.shoppingzoo.R;

import java.util.ArrayList;

public class payment_method extends AppCompatActivity{
ImageButton paytm,cod;

String amount ="0.0";
private ArrayList<Product> productArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        cod=findViewById(R.id.cod_btn);
        paytm=findViewById(R.id.paytm);



        Intent intent1=getIntent();


        productArrayList =new ArrayList<>();
        Intent i=getIntent();
        productArrayList = (ArrayList<Product>) i.getSerializableExtra(TAG_medicine_list);

        amount=intent1.getStringExtra("price");


        cod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(payment_method.this, MainActivity.class);
                startActivity(intent);

               Toast.makeText(payment_method.this,"Order Submitted",Toast.LENGTH_LONG).show();

            }
        });
        paytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(payment_method.this, payment_process.class);

                intent.putExtra("price",amount);

                startActivity(intent);
              //  Toast.makeText(payment_method.this,"Order Submitted",Toast.LENGTH_LONG).show();


            }
        });


    }
}