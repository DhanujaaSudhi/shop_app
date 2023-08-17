package com.android.shoppingzoo.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.shoppingzoo.Model.Order;
import com.android.shoppingzoo.Model.Product;
import com.android.shoppingzoo.R;

import java.util.ArrayList;

public class payment_process extends AppCompatActivity {
    static final String GPAY_PACKAGE_NAME ="com.google.android.apps.nbu.paisa.user";
    private EditText  upiEdt, nameEdt, descEdt;
    private TextView transactionDetailsTV,amountEdt;
    Order order;
    Uri uri;
    String status;
    private ArrayList<Product> productArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_process);
        Intent i=getIntent();
        String price1=i.getStringExtra("price");

        System.out.println(price1);
        amountEdt=findViewById(R.id.idEdtAmount);
        amountEdt.setText(price1);
        upiEdt = findViewById(R.id.idEdtUpi);
        nameEdt = findViewById(R.id.idEdtName);
        descEdt = findViewById(R.id.idEdtDescription);
        Button makePaymentBtn = findViewById(R.id.idBtnMakePayment);
        transactionDetailsTV = findViewById(R.id.idTVTransactionDetails);



        // on below line we are getting date and then we are setting this date as transaction id.
                makePaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // on below line we are getting data from our edit text.
                String  amount = amountEdt.getText().toString();
                Toast.makeText(getApplicationContext(),amount.toString(), Toast.LENGTH_SHORT).show();
                String Upi_id = upiEdt.getText().toString();
                String name =nameEdt.getText() .toString();
                String note = descEdt.getText().toString();
                // on below line we are validating our text field.
                if(!name.equals("")&&!Upi_id.equals("")&&!note.equals("") && !amount.equals("")){
                    uri=makePayment(name,Upi_id,note,amount);
                    payWithGpay(GPAY_PACKAGE_NAME);
                }
            }
        });
    }
   private static Uri makePayment(String name, String Upi_id, String note, String amount){
        return new Uri.Builder()
                .scheme("upi")
                .authority("pay")
                .appendQueryParameter("am", amount)
                //.appendQueryParameter("cu","INR")
                .appendQueryParameter("pa","sruthirajsruthika@okaxis")
                .appendQueryParameter("pn",name)
                .appendQueryParameter("tn",note)


                .build();
   }
   private void payWithGpay(String packagename){
        if(isAppInstalled(this,packagename)){
            Intent i=new Intent(Intent.ACTION_VIEW);
            i.setData(uri);
            i.setPackage(packagename);
            startActivityForResult(i,0);
        }
        else{
            Toast.makeText(payment_process.this, "Google pay is not installed. Please install nad try again.", Toast.LENGTH_SHORT).show();
        }

   }

   public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);



       if((RESULT_OK == resultCode)){
          // Toast.makeText(payment_process.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
           Intent intent = new Intent(payment_process.this, payment_method.class);
           startActivity(intent);


       }
       else{
           Toast.makeText(payment_process.this, "Transaction cancelled or failed please try again.", Toast.LENGTH_SHORT).show();


       }
   }

   public static boolean isAppInstalled(Context context,String packageName){
        try{
            context.getPackageManager().getApplicationInfo(packageName,0);
            return true;
        }catch (PackageManager.NameNotFoundException e){
            return false;
        }
   }
}