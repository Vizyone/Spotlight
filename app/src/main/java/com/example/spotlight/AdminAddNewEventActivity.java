package com.example.spotlight;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddNewEventActivity extends AppCompatActivity
{
    private String CategoryName, Description, Price, Ename, saveCurrentDate, saveCurrentTime;
    private Button AddNewEventButton;
    private ImageView InputEventImage;
    private EditText InputEventName, InputEventDescription, InputEventPrice;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private String flyerRandomkey, downloadImageUri;
    private StorageReference FlyerImageRef;
    private DatabaseReference EventsReference;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_event);

        CategoryName = getIntent().getExtras().get("category").toString();
        FlyerImageRef = FirebaseStorage.getInstance().getReference().child("Event Images");
        EventsReference = FirebaseDatabase.getInstance().getReference().child("Events");

        AddNewEventButton =  findViewById(R.id.add_new_event);
        InputEventDescription = findViewById(R.id.event_description);
        InputEventName = findViewById(R.id.event_name);
        InputEventPrice = findViewById(R.id.event_price);
        InputEventImage = findViewById(R.id.select_event_image);
        loadingBar = new ProgressDialog(this);

        InputEventImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });

        AddNewEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ValidateEventData();
            }
        });
    }

    private void OpenGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GalleryPick && resultCode == RESULT_OK && data!=null)
        {
            ImageUri = data.getData();

            InputEventImage.setImageURI(ImageUri);
        }
    }

    private void ValidateEventData()
    {
        Description = InputEventDescription.getText().toString();
        Price = InputEventPrice.getText().toString();
        Ename = InputEventName.getText().toString();

        if(ImageUri == null)
        {
            Toast.makeText(this, "Event flyer is mandatory...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Description))
        {
            Toast.makeText(this, "Event description is mandatory", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Price))
        {
            Toast.makeText(this, "Event price is mandatory", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Ename))
        {
            Toast.makeText(this, "Event name is mandatory", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StoreEventInformation();
        }
    }

    private void StoreEventInformation()
    {
        loadingBar.setTitle("Adding new event");
        loadingBar.setMessage("Please wait while we are adding your event");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        flyerRandomkey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = FlyerImageRef.child(ImageUri.getLastPathSegment() + flyerRandomkey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                Toast.makeText(AdminAddNewEventActivity.this, "Error: "+e.toString(), Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(AdminAddNewEventActivity.this, "Event Image uploaded successfully...", Toast.LENGTH_SHORT).show();

                Task <Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if(!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        downloadImageUri = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        downloadImageUri = task.getResult().toString();

                        Toast.makeText(AdminAddNewEventActivity.this, "Got Event Image Url", Toast.LENGTH_SHORT).show();

                        SaveEventInfoToDatabase();
                    }
                });
            }
        });
    }

    private void SaveEventInfoToDatabase()
    {
        HashMap <String,Object> eventMap = new HashMap<>();
        eventMap.put("pid",flyerRandomkey);
        eventMap.put("date",saveCurrentDate);
        eventMap.put("time",saveCurrentTime);
        eventMap.put("description",Description);
        eventMap.put("image",downloadImageUri);
        eventMap.put("category",CategoryName);
        eventMap.put("price",Price);
        eventMap.put("ename",Ename);

        EventsReference.child(flyerRandomkey).updateChildren(eventMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            startActivity(new Intent(getApplicationContext(),AdminCategoryActivity.class));

                            loadingBar.dismiss();
                            Toast.makeText(AdminAddNewEventActivity.this, "Product is added successfully", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            loadingBar.dismiss();
                            Toast.makeText(AdminAddNewEventActivity.this, "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
