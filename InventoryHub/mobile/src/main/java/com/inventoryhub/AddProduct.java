package com.inventoryhub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddProduct extends AppCompatActivity {

    EditText editProductName, editProductPrice, editProductDescription, editProductQuantity;
    Button addProductButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        addProductButton = (Button) findViewById(R.id.addProductButton);
        editProductName = (EditText) findViewById(R.id.editProductName);
        editProductPrice = (EditText) findViewById(R.id.editProductPrice);
        editProductDescription = (EditText) findViewById(R.id.editProductDescription);
        editProductQuantity = (EditText) findViewById(R.id.editProductQuantity);

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkData();
            }
        });

    }
    void checkData(){
        String pName = editProductName.getText().toString();
        String pPrice = editProductPrice.getText().toString();
        String pDescription = editProductDescription.getText().toString();
        String pQuantity = editProductQuantity.getText().toString();

        if(pName.isEmpty()){
            Toast.makeText(AddProduct.this, "Please enter name", Toast.LENGTH_SHORT).show();
        }else if(pPrice.isEmpty()){
            Toast.makeText(AddProduct.this, "Please enter price", Toast.LENGTH_SHORT).show();
        }else if(pDescription.isEmpty()){
            Toast.makeText(AddProduct.this, "Please enter description", Toast.LENGTH_SHORT).show();
        }else if(pQuantity.isEmpty()){
            Toast.makeText(AddProduct.this, "Please enter quantity", Toast.LENGTH_SHORT).show();
        }else{

            DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
            ProductObject object = new ProductObject(pName, pPrice, pDescription, pQuantity);
            ref.child(MainActivity.pin).push().setValue(object).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(AddProduct.this, "Added Successfully.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

        }
    }
}