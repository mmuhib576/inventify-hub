package com.inventoryhub;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateProduct extends AppCompatActivity {

    EditText editProductName, editProductPrice, editProductDescription, editProductQuantity;
    Button addProductButton, deleteProductButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);


        addProductButton = (Button) findViewById(R.id.addProductButton);
        deleteProductButton = (Button) findViewById(R.id.deleteProductButton);
        editProductName = (EditText) findViewById(R.id.editProductName);
        editProductPrice = (EditText) findViewById(R.id.editProductPrice);
        editProductDescription = (EditText) findViewById(R.id.editProductDescription);
        editProductQuantity = (EditText) findViewById(R.id.editProductQuantity);

        editProductName.setText(MobileLanding.selectedProduct.productName);
        editProductPrice.setText(MobileLanding.selectedProduct.productPrice);
        editProductDescription.setText(MobileLanding.selectedProduct.productDescription);
        editProductQuantity.setText(MobileLanding.selectedProduct.productQuanitty);


        deleteProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProduct.this);
                builder.setTitle("Delete?");
                builder.setMessage("Are you sure you want to delete?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child(MainActivity.pin).child(MobileLanding.selectedProduct.key).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(UpdateProduct.this, "Product deleted successfully.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

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
            Toast.makeText(UpdateProduct.this, "Please enter name", Toast.LENGTH_SHORT).show();
        }else if(pPrice.isEmpty()){
            Toast.makeText(UpdateProduct.this, "Please enter price", Toast.LENGTH_SHORT).show();
        }else if(pDescription.isEmpty()){
            Toast.makeText(UpdateProduct.this, "Please enter description", Toast.LENGTH_SHORT).show();
        }else if(pQuantity.isEmpty()){
            Toast.makeText(UpdateProduct.this, "Please enter quantity", Toast.LENGTH_SHORT).show();
        }else{

            DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
            ProductObject object = new ProductObject(pName, pPrice, pDescription, pQuantity);
            ref.child(MainActivity.pin).child(MobileLanding.selectedProduct.key).setValue(object).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(UpdateProduct.this, "Updated Successfully.", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}