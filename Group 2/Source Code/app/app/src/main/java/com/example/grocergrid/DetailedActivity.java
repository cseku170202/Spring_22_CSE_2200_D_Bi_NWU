package com.example.grocergrid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.grocergrid.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;


public class DetailedActivity extends AppCompatActivity {


   TextView quantity;
   int totalQuantity=1;
   int totalPrice=0;

    ImageView detailedImg;
    TextView price,rating;
    Button addToCart;
    ImageView addItem,removeItem;




    Toolbar toolbar;

    FirebaseFirestore firestore;
    FirebaseAuth auth;
    ViewAllModel viewAllModel=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);



        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();


        final Object object = getIntent().getSerializableExtra("detail");
        if (object instanceof ViewAllModel){
            viewAllModel=(ViewAllModel) object;

        }



        quantity= findViewById(R.id.quantity);
        detailedImg = findViewById(R.id.detailed_img);
        addItem = findViewById(R.id.add_item);
        removeItem = findViewById(R.id.remove_item);
        price = findViewById(R.id.detailed_price);
        rating = findViewById(R.id.detailed_rating);

        if (viewAllModel !=null){
            Glide.with(getApplicationContext()).load(viewAllModel.getImg_url()).into(detailedImg);
            rating.setText(viewAllModel.getRating());
            price.setText("Price:"+viewAllModel.getPrice()+"/kg");

            totalPrice=viewAllModel.getPrice()*totalQuantity;

            if(viewAllModel.getType().equals("egg")){
                price.setText("Price:"+viewAllModel.getPrice()+"/dozen");
                totalPrice=viewAllModel.getPrice()*totalQuantity;
            }
            if (viewAllModel.getType().equals("drink")){
                price.setText("Price:"+viewAllModel.getPrice()+"/litre");
                totalPrice=viewAllModel.getPrice()*totalQuantity;
            }
            if (viewAllModel.getType().equals("dessert")){
                price.setText("Price:"+viewAllModel.getPrice()+"/per piece");
                totalPrice=viewAllModel.getPrice()*totalQuantity;
            }

        }
        addToCart = findViewById(R.id.add_to_cart);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addedToCart();
            }
        });
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalQuantity < 1000) {
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalPrice = viewAllModel.getPrice() * totalQuantity;
                } else {
                    Toast.makeText(DetailedActivity.this, "Out of stock", Toast.LENGTH_SHORT).show();
                }
            }
        });
        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalQuantity> 1){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalPrice=viewAllModel.getPrice()*totalQuantity;
                }

            }
        });


    }

    private void addedToCart() {
        String saveCurrentDate,saveCurrentTime;
        Calendar calForDate= Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("MM/dd/yyyy");
        saveCurrentDate=currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calForDate.getTime());
        String userEmail = auth.getCurrentUser().getEmail();

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("productName", viewAllModel.getName());
        cartMap.put("productPrice", price.getText().toString());
        cartMap.put("Date", saveCurrentDate);
        cartMap.put("Time", saveCurrentTime);
        cartMap.put("TotalQuantity", quantity.getText().toString());
        cartMap.put("TotalPrice", totalPrice);

        // Use the user's email as the document ID
        firestore.collection("AddToCart").document(userEmail)
                .collection("CurrentUser").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(DetailedActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });






    }
}