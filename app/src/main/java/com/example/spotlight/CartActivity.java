package com.example.spotlight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spotlight.Model.Cart;
import com.example.spotlight.Prevalent.Prevalent;
import com.example.spotlight.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CartActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button nextProcessBtn;
    private TextView txtTotalAmount;

    private int overTotalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setReverseLayout(true);
        ((LinearLayoutManager) layoutManager).setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        nextProcessBtn = findViewById(R.id.next_process_btn);
        txtTotalAmount = findViewById(R.id.total_price);
        //For the pop up
        relativeLayout = findViewById(R.id.popup);
        nextProcessBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Toast.makeText(CartActivity.this, "Add the popup!", Toast.LENGTH_SHORT).show();
                //showpopup();
                Intent i = new Intent(CartActivity.this, PopUpWindow.class);
                startActivity(i);
            }
        });


    }
    //private PopupWindow popupWindow;
    private RelativeLayout relativeLayout;
    /*private void showpopup() {


        LayoutInflater inflater = (LayoutInflater) CartActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View layout = inflater.inflate(R.layout.payment_confirmation_popup, null);
        popupWindow = new PopupWindow(layout, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        /*Button btnSubmit = layout.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        popupWindow.showAtLocation(relativeLayout, Gravity.CENTER,0,0);

        if (Build.VERSION.SDK_INT>=21){
            popupWindow.setElevation(5.0f);
        }

    }*/

    @Override
    protected void onStart()
    {
        super.onStart();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartListRef.child("User View")
                        .child(Prevalent.currentOnlineUser.getPhone())
                        .child("Events"),Cart.class)
                        .build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter =
                new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull final Cart cart) {
                        cartViewHolder.txtEventName.setText(cart.getEname());
                        cartViewHolder.txtEventPrice.setText("Price = " + cart.getPrice() + " $");
                        cartViewHolder.txtNumberOfTickets.setText("Number of tickets = " + cart.getQuantity());

                        int oneTypeOfProductPrice = ((Integer.valueOf(cart.getPrice()))) * Integer.valueOf(cart.getQuantity());
                        overTotalPrice = overTotalPrice + oneTypeOfProductPrice;
                        txtTotalAmount.setText("Total Price = " + String.valueOf(overTotalPrice) + " $");

                        cartViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                CharSequence options[] = new CharSequence[]
                                        {
                                          "Edit",
                                          "Detete"
                                        };
                                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                                builder.setTitle("Cart Options");

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i)
                                    {
                                        if(i == 0)
                                        {
                                            Intent intent = new Intent(CartActivity.this,EventDetailsActivity.class);
                                            intent.putExtra("pid",cart.getPid());
                                            startActivity(intent);
                                        }
                                        if(i == 1)
                                        {
                                            cartListRef.child("User View")
                                                    .child(Prevalent.currentOnlineUser.getPhone())
                                                    .child("Events")
                                                    .child(cart.getPid())
                                                    .removeValue()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) 
                                                        {
                                                            if(task.isSuccessful())
                                                            {
                                                                Toast.makeText(CartActivity.this, "Event removed successfully from Cart List", Toast.LENGTH_SHORT).show();

                                                                Intent intent = new Intent(CartActivity.this,HomeActivity.class);
                                                                startActivity(intent);
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                });
                                builder.show();
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
                        CartViewHolder holder = new CartViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

}
