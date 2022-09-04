package com.bskoskusk.imagetotex;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bskoskusk.imagetotex.R;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class simpleFileActivity extends AppCompatActivity {
    EditText mResultEt;
    ImageView mPreviewIv;

    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 400;
    private static final int IMAGE_PICK_GALLERY_CODE = 1000;
    private static final int IMAGE_PICK_CAMERA_CODE = 1001;

    String cameraPermission[];
    String storagePermission[];

    String mText;
    private static final int WRITE_EXTERNAL_STORAGE_CODE = 1;


    Uri image_uri;
    //declaring actionbar
    ActionBar actionBar;



    //**************************************************generate button*********************************************************************
    public void generateKLiye(View kuchbhi){

        //input text form edit text
        mText  = mResultEt.getText().toString().trim(); //.trim() removes spaces before and after text
        //validate
        if(mText.isEmpty()){ // user has not entered anything
            Toast.makeText(getApplicationContext(), "Please make sure, Section is not left Blank!", Toast.LENGTH_SHORT).show();

        }
        else {
            // user has entered string data
            //if phone OS is greater then Marsmellow then needs permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED){
                    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    //show popup for runtime permissions
                    requestPermissions(permissions,WRITE_EXTERNAL_STORAGE_CODE);
                }
                else {
                    //permission already granted, save data
                    saveToTexFile(mText);
                }
            }
            else {
                //else system OS is < marshmellow, no need for runtime permissionsm, jist save data
                saveToTexFile(mText);

            }

        }
    }
    //************************************Method to save tex file**********************************************
    private void saveToTexFile(String mText) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHMMSS", Locale.getDefault()).format(System.currentTimeMillis());
        try {
            //path to store file
            File path = Environment.getExternalStorageDirectory();
            //create foldernamed LaTeX files
            File dir = new File(path + "/LaTeX Files/");
            dir.mkdirs();
            //file name
            String fileName = "LaTeX_" + timeStamp + ".tex"; //e.g, LaTeX_20200726_124600.tex

            File file = new File(dir, fileName);

            //FileWriter class is used to store characters in file
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("\\documentclass[12pt]{article}");
            bw.write("\n");
            bw.write("\\begin{document}");
            bw.write("\n");
            bw.write(mText);
            bw.write("\n");
            bw.write("\\end{document}");
            bw.close();

            //show file name and path where file is saved
            Toast.makeText(this, fileName+" is saved to \n"+dir, Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            //if anything goes wrong
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
//*********************************************************************************************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_file);

        //initialize actionbar first
        actionBar = getSupportActionBar();
        //set title now
        actionBar.setTitle("Simple LaTeX File");

        mResultEt = findViewById(R.id.resultEt);
        mPreviewIv = findViewById(R.id.imageIv);
        //camera permission
        cameraPermission = new String[]{Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    }
    //actionbar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate menu
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }
    //handle actionbar item clicks
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.addImage){
            showImageImportDialog();
        }
        return super.onOptionsItemSelected(item);
    }

//***************************************************************************************************************************************
    private void showImageImportDialog() {
        //items to display in dialog
        String [] items = {" Camera"," Gallery"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        //set title
        dialog.setTitle("Select Image");
        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){
                    //camera option clicked
                    /*for OS marshmellow and above we need to ask runtime permission for camera and storage */
                    if(!checkCameraPermission()){
                        //camera permission not allowed request it
                        requestCameraPermission();
                    }
                    else{
                        //permission allowed take picture
                        pickCamera();
                    }
                }
                if(which == 1){
                    //gallery option clicked
                    if(!checkStoragePermission()){
                        //Storage permission is not allowed, request it
                        requestStoragePermission();
                    }
                    else{
                        //permission allowed take picture
                        pickGallery();
                    }
                }
            }
        });
        dialog.create().show(); //show dialog
    }
//***************************************************************************************************************************************

//***************************************************************************************************************************************
    private void pickGallery() {
        //intent to pick image from gallery
        Intent intent = new Intent(Intent.ACTION_PICK);
        //set intent type to image
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_GALLERY_CODE);
    }

    private void pickCamera() {
        //intent to take image from camera,it will also gonna save into storage to get high quality image
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"New Pic"); //title of the picture
        values.put(MediaStore.Images.Media.DESCRIPTION,"Image to Text"); //description
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        startActivityForResult(cameraIntent,IMAGE_PICK_CAMERA_CODE);
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this,storagePermission,STORAGE_REQUEST_CODE);
    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this,cameraPermission,CAMERA_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        /*check camera permission and return the result
         *in order to get high quality image we have to save image in external storage first
         *before inserting to image view thats why storage permission will also be required
         */
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }
//***************************************************************************************************************************************

//***************************************************************************************************************************************
    //handle permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case CAMERA_REQUEST_CODE:
                if(grantResults.length >0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if(cameraAccepted && writeStorageAccepted){
                        pickCamera();
                    }
                    else{
                        Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case STORAGE_REQUEST_CODE:
                if(grantResults.length >0){

                    boolean writeStorageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if(writeStorageAccepted){
                        pickGallery();
                    }
                    else{
                        Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            //for storing file in internal storage
            case WRITE_EXTERNAL_STORAGE_CODE: {
                //if result request is cancelled, the result arrays are empty
                if (grantResults.length > 0 && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED) {
                    //permission was granted, save data
                    saveToTexFile(mText);
                } else {
                    //permission was denied, show toast
                    Toast.makeText(this, "Storage Permission is required to store data", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    //**************************************************************************************************************************************************


//******************************************************************************************************************************************************
    //handle image result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(resultCode == RESULT_OK){
            if(requestCode == IMAGE_PICK_GALLERY_CODE){
                //got image from gallery and now crop it
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON) //enable image guidlines
                        .start(this);
            }
            if (requestCode == IMAGE_PICK_CAMERA_CODE){
                //got image from camera and now crop it
                CropImage.activity(image_uri)
                        .setGuidelines(CropImageView.Guidelines.ON) //enable image guidlines
                        .start(this);
            }
        }
        //get cropped image
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                Uri resultUri = result.getUri(); //get image uri
                //set image to image view
                mPreviewIv.setImageURI(resultUri);

                //get drawable bitmap for text recognition
                BitmapDrawable bitmapDrawable = (BitmapDrawable)mPreviewIv.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                TextRecognizer recognizer = new TextRecognizer.Builder(getApplicationContext()).build();

                if(!recognizer.isOperational()){
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                }
                else {
                    Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                    SparseArray<TextBlock> items = recognizer.detect(frame);
                    StringBuilder sb = new StringBuilder();
                    //get text untill there is no text
                    for (int i = 0; i<items.size(); i++){
                        TextBlock myItem = items.valueAt(i);
                        sb.append(myItem.getValue());
                        sb.append("\n");
                    }
                    //set text to edit text
                    mResultEt.setText(sb.toString());
                }
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                //if there is any error, show it
                Exception error = result.getError();
                Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();

            }
        }
    }
    //******************************************************************************************************************************
}