package com.foodie.activities;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.foodie.R;
import com.foodie.models.Admin_FoodModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class Activity_SetFood extends AppCompatActivity {
    EditText quantity, food, price;
    Button done;
    ProgressDialog dialog;
    FirestoreRecyclerAdapter<Admin_FoodModel, FoodViewHolder> adapter;
    ImageButton delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_food);
        quantity = findViewById(R.id.quantity);
        food = findViewById(R.id.food);
        price = findViewById(R.id.price);
        done = findViewById(R.id.done);
        delete = findViewById(R.id.delete);
        dialog = new ProgressDialog(Activity_SetFood.this);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                if (food.getText().toString().isEmpty()) {
                    food.setError("Enter Food Type");
                } else if (price.getText().toString().isEmpty()) {
                    price.setError("Enter Price");
                } else if (quantity.getText().toString().isEmpty()) {
                    quantity.setError("Enter Quantity");
                } else {


                    Map<String, String> map = new HashMap<>();
                    map.put("food", food.getText().toString());
                    map.put("price", price.getText().toString());
                    map.put("quantity", quantity.getText().toString());

                    db.collection("foodie").document().set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                dialog.dismiss();
                                food.setText("");
                                quantity.setText("");
                                price.setText("");
                                Toast.makeText(Activity_SetFood.this, "Data added successfully", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(Activity_SetFood.this, "Data could not be added", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

        /*DISPLAY LIST OF FOODS*/
        Query query = db.collection("foodie").orderBy("food", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Admin_FoodModel> foods = new FirestoreRecyclerOptions.Builder<Admin_FoodModel>()
                .setQuery(query, Admin_FoodModel.class).build();


        adapter = new FirestoreRecyclerAdapter<Admin_FoodModel, FoodViewHolder>(foods) {
            @Override
            protected void onBindViewHolder(@NonNull FoodViewHolder holder, int position, @NonNull Admin_FoodModel food_model) {
                holder.setFoodType(food_model.getFood());
                holder.setPrice(food_model.getPrice());
                holder.setQuantity(food_model.getQuantity());
            }

            @NonNull
            @Override
            public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_food_layout, parent, false);
                return new FoodViewHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);
    }

    /*VIEW HOLDER*/

    class FoodViewHolder extends RecyclerView.ViewHolder {
        View view;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }

        void setFoodType(String foodType) {
            TextView food_type = view.findViewById(R.id.food);
            food_type.setText(foodType);
        }

        void setPrice(String price) {
            TextView food_type = view.findViewById(R.id.price);
            food_type.setText(price);
        }

        void setQuantity(String quantity) {
            TextView food_type = view.findViewById(R.id.quantity);
            food_type.setText(quantity);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (adapter != null) {
            adapter.stopListening();
        }
    }

}
