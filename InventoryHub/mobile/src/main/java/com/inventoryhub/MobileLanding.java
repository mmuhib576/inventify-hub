package com.inventoryhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MobileLanding extends AppCompatActivity {

    Button add_button;
    ListView product_list;
    ArrayList<ProductObject> productArray = new ArrayList<>();

    public static ProductObject selectedProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_landing);

        ((TextView) findViewById(R.id.unique_id)).setText("Connected for: "+MainActivity.pin);

        ((ImageView) findViewById(R.id.logout_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(MobileLanding.this, MainActivity.class));
            }
        });


        add_button = (Button) findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MobileLanding.this, AddProduct.class));
            }
        });


        product_list = (ListView) findViewById(R.id.product_list);


        FirebaseDatabase.getInstance().getReference().child(MainActivity.pin).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productArray = new ArrayList<>();
                for (DataSnapshot snap: snapshot.getChildren()){
                    ProductObject pObject = new ProductObject(
                            snap.child("productName").getValue().toString(),
                            snap.child("productPrice").getValue().toString(),
                            snap.child("productDescription").getValue().toString(),
                            snap.child("productQuanitty").getValue().toString()
                    );
                    pObject.key = snap.getKey();
                    productArray.add(pObject);
                }
                product_list.setAdapter(new ListAdapter());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MobileLanding.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        product_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedProduct = productArray.get(i);
                startActivity(new Intent(MobileLanding.this, UpdateProduct.class));
            }
        });

        product_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MobileLanding.this);
                builder.setTitle("Delete?");
                builder.setMessage("Are you sure you want to delete?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child(MainActivity.pin).child(productArray.get(i).key).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(MobileLanding.this, "Product deleted successfully.", Toast.LENGTH_SHORT).show();
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
                return true;
            }
        });
    }

    class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return productArray.size();
        }

        @Override
        public Object getItem(int i) {
            return productArray.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View vi = getLayoutInflater().inflate(R.layout.product_item, null);
            TextView title, quantity, price;
            title = vi.findViewById(R.id.title);
            price = vi.findViewById(R.id.price);
            quantity = vi.findViewById(R.id.quantity);
            title.setText(productArray.get(i).productName);
            price.setText("Price: $"+productArray.get(i).productPrice);
            quantity.setText("Quantity: "+productArray.get(i).productQuanitty);
            return vi;
        }
    }
}