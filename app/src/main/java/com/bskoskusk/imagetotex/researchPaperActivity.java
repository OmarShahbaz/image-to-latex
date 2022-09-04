package com.bskoskusk.imagetotex;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

public class researchPaperActivity extends AppCompatActivity {
    EditText eT;
    EditText eT1;
    EditText eT2;
    EditText eT3;
    EditText eT4;
    EditText eT5;
    EditText eT6;
    EditText eT7,eT8,eT9,eT10,eT11,eT12;
    EditText eT13,eT14,eT15,eT16,eT17,eT18,eT19,eT20,eT21,eT22,eT23,eT24,eT25,eT26,eT27,eT28,eT29;
    ImageView mPreviewIv;
    ActionBar actionBar;
    int check =13;

    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 400;
    private static final int IMAGE_PICK_GALLERY_CODE = 1000;
    private static final int IMAGE_PICK_CAMERA_CODE = 1001;

    String cameraPermission[];
    String storagePermission[];

    String rTitle;
    String authorName;
    String deptName;
    String orgName;
    String cityA;
    String countryA;
    String mailA;
    String authorNameB,deptNameB,orgNameB,cityB,countryB,mailB;
    String rAbstract,rKeywords,intro,relatedWork,impDetails,toolArch,testCase,randTest,genAlgo1,genAlgo2,genAlgo3,genAlgo4,limitations,availability,expDetails,conclusion,futureWork;

    private static final int WRITE_EXTERNAL_STORAGE_CODE = 1;

    Uri image_uri;

    //**************************************************generate button*********************************************************************
    public void generateKLiye(View kuchbhi){

        //input text form edit text
        rTitle = eT.getText().toString().trim();
        authorName = eT1.getText().toString().trim();
        deptName  = eT2.getText().toString().trim();
        orgName = eT3.getText().toString().trim();
        cityA = eT4.getText().toString().trim();
        countryA = eT5.getText().toString().trim();
        mailA = eT6.getText().toString().trim();
        //authorB
        authorNameB = eT7.getText().toString().trim();
        deptNameB  = eT8.getText().toString().trim();
        orgNameB = eT9.getText().toString().trim();
        cityB = eT10.getText().toString().trim();
        countryB = eT11.getText().toString().trim();
        mailB = eT12.getText().toString().trim();
        //researchpaper section
        rAbstract = eT13.getText().toString().trim();
        rKeywords  = eT14.getText().toString().trim();
        intro = eT15.getText().toString().trim();
        relatedWork = eT16.getText().toString().trim();
        impDetails = eT17.getText().toString().trim();
        toolArch = eT18.getText().toString().trim();
        testCase = eT19.getText().toString().trim();
        randTest  = eT20.getText().toString().trim();
        genAlgo1 = eT21.getText().toString().trim();
        genAlgo2 = eT22.getText().toString().trim();
        genAlgo3 = eT23.getText().toString().trim();
        genAlgo4 = eT24.getText().toString().trim();
        limitations = eT25.getText().toString().trim();
        availability  = eT26.getText().toString().trim();
        expDetails = eT27.getText().toString().trim();
        conclusion = eT28.getText().toString().trim();
        futureWork = eT29.getText().toString().trim();

        //validate
        if(rTitle.isEmpty() || authorName.isEmpty() || deptName.isEmpty() || orgName.isEmpty() || cityA.isEmpty() || countryA.isEmpty() || mailA.isEmpty() || authorNameB.isEmpty() || deptNameB.isEmpty() || orgNameB.isEmpty() || cityB.isEmpty() || countryB.isEmpty() ||
        mailB.isEmpty() || rAbstract.isEmpty() || rKeywords.isEmpty() || intro.isEmpty() || relatedWork.isEmpty() || impDetails.isEmpty() || toolArch.isEmpty() || testCase.isEmpty() || randTest.isEmpty() || genAlgo1.isEmpty() || genAlgo2.isEmpty() || genAlgo3.isEmpty() || genAlgo4.isEmpty() || limitations.isEmpty() || availability.isEmpty() || expDetails.isEmpty() || conclusion.isEmpty() || futureWork.isEmpty()){ // user has not entered anything
            Toast.makeText(getApplicationContext(), "Please make sure, No section is left Blank!", Toast.LENGTH_SHORT).show();

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
                    saveToTexFile(rTitle,authorName,deptName,orgName,cityA,countryA,mailA,authorNameB,deptNameB,orgNameB,cityB,countryB,mailB,rAbstract,rKeywords,intro,relatedWork,impDetails,toolArch,testCase,randTest,genAlgo1,genAlgo2,genAlgo3,genAlgo4,limitations,availability,expDetails,conclusion,futureWork);
                }
            }
            else {
                //else system OS is < marshmellow, no need for runtime permissionsm, just save data
                saveToTexFile(rTitle,authorName,deptName,orgName,cityA,countryA,mailA,authorNameB,deptNameB,orgNameB,cityB,countryB,mailB,rAbstract,rKeywords,intro,relatedWork,impDetails,toolArch,testCase,randTest,genAlgo1,genAlgo2,genAlgo3,genAlgo4,limitations,availability,expDetails,conclusion,futureWork);

            }

        }
    }
    //************************************Method to save tex file**********************************************
    private void saveToTexFile(String text1, String text2, String text3, String text4, String text5,String text6, String text7, String text8,String text9, String text10, String text11, String text12, String text13, String text14, String text15,String text16, String text17, String text18,String text19, String text20, String text21,String text22, String text23, String text24, String text25, String text26, String text27, String text28, String text29, String text30) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHMMSS", Locale.getDefault()).format(System.currentTimeMillis());
        try {
            //path to store file
            File path = Environment.getExternalStorageDirectory();
            //create foldernamed LaTeX files
            File dir = new File(path + "/LaTeX Files/");
            dir.mkdirs();
            //file name
            String fileName = "Research_Paper_LaTeX_" + timeStamp + ".tex"; //e.g, Research_Paper_LaTeX_20200726_124600.tex

            File file = new File(dir, fileName);

            //FileWriter class is used to store characters in file
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("\\documentclass[conference]{IEEEtran}");
            bw.write("\n");
            bw.write("\\IEEEoverridecommandlockouts");
            bw.write("\n");
            bw.write("\\usepackage{cite}");
            bw.write("\n");
            bw.write("\\usepackage{amsmath,amssymb,amsfonts}");
            bw.write("\n");
            bw.write("\\usepackage{algorithmic}");
            bw.write("\n");
            bw.write("\\usepackage{graphicx}");
            bw.write("\n");
            bw.write("\\usepackage{textcomp}");
            bw.write("\n");
            bw.write("\\usepackage{xcolor}");
            bw.write("\n");
            //______________________________
            bw.write("\\begin{document}");
            bw.write("\n");
            //_____________________________
            bw.write("\\title{");
            bw.write(text1);
            bw.write("\\"+"\\");
            bw.write("}");
            bw.write("\n");
            //____________________________author A____________
            bw.write("\\author{");
            bw.write("\n");
            bw.write("\\IEEEauthorblockN{");
            bw.write(text2);
            bw.write("}");
            bw.write("\n");
            bw.write("\\IEEEauthorblockA{\\textit{");
            bw.write(text3);
            bw.write("}");
            bw.write("\\"+"\\");
            bw.write("\n");
            bw.write("\\textit{");
            bw.write(text4);
            bw.write("}");
            bw.write("\\"+"\\");
            bw.write("\n");
            bw.write(text5); //cityA
            bw.write(",");
            bw.write(text6); //countryA
            bw.write("\\"+"\\");
            bw.write("\n");
            bw.write(text7); //mailA
            bw.write("}");
            bw.write("\n");

            bw.write("\\and");
            bw.write("\n");

            //copy above and paste it here
            bw.write("\\IEEEauthorblockN{");
            bw.write(text8); //authorB name
            bw.write("}");
            bw.write("\n");
            bw.write("\\IEEEauthorblockA{\\textit{");
            bw.write(text9); //authorB department
            bw.write("}");
            bw.write("\\"+"\\");
            bw.write("\n");
            bw.write("\\textit{");
            bw.write(text10); //authorB org.
            bw.write("}");
            bw.write("\\"+"\\");
            bw.write("\n");
            bw.write(text11); //cityB
            bw.write(",");
            bw.write(text12); //countryB
            bw.write("\\"+"\\");
            bw.write("\n");
            bw.write(text13); //mailB
            bw.write("}");
            bw.write("\n");
            //_________________________________
            bw.write("}"); //author bracket
            bw.write("\n");
            bw.write("\\maketitle");
            bw.write("\n");
            //_____________________________

            bw.write("\\begin{abstract}");
            bw.write("\n");
            bw.write(text14);
            bw.write("\n");
            bw.write("\\end{abstract}");
            bw.write("\n");
            bw.write("\n");
            //______________________________

            bw.write("\\begin{IEEEkeywords}");
            bw.write("\n");
            bw.write(text15);
            bw.write("\n");
            bw.write("\\end{IEEEkeywords}");
            bw.write("\n");

            //__________________________________
            bw.write("\n");
            bw.write("\\section{INTRODUCTION}");
            bw.write("\n");
            bw.write(text16);
            bw.write("\n");

            //__________________________________

            bw.write("\n");
            bw.write("\\section{RELATED WORK}");
            bw.write("\n");
            bw.write(text17);
            bw.write("\n");

            //____________________________________

            bw.write("\n");
            bw.write("\\section{IMPLEMENTATION WORK}");
            bw.write("\n");
            bw.write(text18);
            bw.write("\n");

            //____________________________________

            bw.write("\n");
            bw.write("\\subsection{Tool Architecture}");
            bw.write("\n");
            bw.write(text19);
            bw.write("\n");

            //____________________________________

            bw.write("\n");
            bw.write("\\subsection{Test Case Generation Techniques}");
            bw.write("\n");
            bw.write(text20);
            bw.write("\n");

            //____________________________________

            bw.write("\n");
            bw.write("\\subsubsection{Random Testing}");
            bw.write("\n");
            bw.write(text21);
            bw.write("\n");

            //____________________________________

            bw.write("\n");
            bw.write("\\subsubsection{Genetic Algorithm-Variant 1}");
            bw.write("\n");
            bw.write(text22);
            bw.write("\n");

            //____________________________________

            bw.write("\n");
            bw.write("\\subsubsection{Genetic Algorithm-Variant 2}");
            bw.write("\n");
            bw.write(text23);
            bw.write("\n");

            //____________________________________

            bw.write("\n");
            bw.write("\\subsubsection{Genetic Algorithm-Variant 3}");
            bw.write("\n");
            bw.write(text24);
            bw.write("\n");

            //____________________________________

            bw.write("\n");
            bw.write("\\subsubsection{Genetic Algorithm-Variant 4}");
            bw.write("\n");
            bw.write(text25);
            bw.write("\n");

            //____________________________________

            bw.write("\n");
            bw.write("\\subsection{Limitations}");
            bw.write("\n");
            bw.write(text26);
            bw.write("\n");

            //____________________________________

            bw.write("\n");
            bw.write("\\subsection{Availibility}");
            bw.write("\n");
            bw.write(text27);
            bw.write("\n");

            //____________________________________

            bw.write("\n");
            bw.write("\\section{EXPERIMENTAL RESULTS}");
            bw.write("\n");
            bw.write(text28);
            bw.write("\n");

            //____________________________________

            bw.write("\n");
            bw.write("\\subsection{CONCLUSION}");
            bw.write("\n");
            bw.write(text29);
            bw.write("\n");

            //____________________________________

            bw.write("\n");
            bw.write("\\subsection{FUTURE WORK}");
            bw.write("\n");
            bw.write(text30);
            bw.write("\n");




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
        setContentView(R.layout.activity_research_paper);
        //initialize actionbar first
        actionBar = getSupportActionBar();
        //set title now
        actionBar.setTitle("Research Paper Writing");

        eT = findViewById(R.id.resultEt);
        eT1 = findViewById(R.id.resultEt1);
        eT2 = findViewById(R.id.resultEt2);
        eT3 = findViewById(R.id.resultEt3);
        eT4 = findViewById(R.id.resultEt4);
        eT5 = findViewById(R.id.resultEt5);
        eT6 = findViewById(R.id.resultEt6);
        eT7 = findViewById(R.id.resultEt7);
        eT8 = findViewById(R.id.resultEt8);
        eT9 = findViewById(R.id.resultEt9);
        eT10 = findViewById(R.id.resultEt10);
        eT11 = findViewById(R.id.resultEt11);
        eT12 = findViewById(R.id.resultEt12);
        eT13 = findViewById(R.id.resultEt13);
        eT14 = findViewById(R.id.resultEt14);
        eT15 = findViewById(R.id.resultEt15);
        eT16 = findViewById(R.id.resultEt16);
        eT17 = findViewById(R.id.resultEt17);
        eT18 = findViewById(R.id.resultEt18);
        eT19 = findViewById(R.id.resultEt19);
        eT20 = findViewById(R.id.resultEt20);
        eT21 = findViewById(R.id.resultEt21);
        eT22 = findViewById(R.id.resultEt22);
        eT23 = findViewById(R.id.resultEt23);
        eT24 = findViewById(R.id.resultEt24);
        eT25 = findViewById(R.id.resultEt25);
        eT26 = findViewById(R.id.resultEt26);
        eT27 = findViewById(R.id.resultEt27);
        eT28 = findViewById(R.id.resultEt28);
        eT29 = findViewById(R.id.resultEt29);
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
                saveToTexFile(rTitle,authorName,deptName,orgName,cityA,countryA,mailA,authorNameB,deptNameB,orgNameB,cityB,countryB,mailB,rAbstract,rKeywords,intro,relatedWork,impDetails,toolArch,testCase,randTest,genAlgo1,genAlgo2,genAlgo3,genAlgo4,limitations,availability,expDetails,conclusion,futureWork);
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
                    /*
                    if(check==0) {
                        eT.setText(sb.toString());
                        check =1;
                    }
                    else if(check==1){
                        eT1.setText(sb.toString());
                        check = 2;
                    }
                    else  if(check==2){
                        eT2.setText(sb.toString());
                        check = 3;
                    }
                    else if(check==3){
                        eT3.setText(sb.toString());
                        check = 4;
                    }
                    else if(check==4){
                        eT4.setText(sb.toString());
                        check = 5;
                    }
                    else if (check==5){
                        eT5.setText(sb.toString());
                        check = 6;
                    }
                    else if(check==6){
                        eT6.setText(sb.toString());
                        check = 7;
                    }
                    else if (check==7){
                        eT7.setText(sb.toString());
                        check = 8;
                    }
                    else if (check==8){
                        eT8.setText(sb.toString());
                        check = 9;
                    }
                    //-----------------------------------------
                    else if (check==9){
                        eT9.setText(sb.toString());
                        check = 10;
                    }
                    else if (check==10){
                        eT10.setText(sb.toString());
                        check = 11;
                    }
                    else if (check==11){
                        eT11.setText(sb.toString());
                        check = 12;
                    }
                    else if (check==12){
                        eT12.setText(sb.toString());
                        check = 13;
                    }
                    */
                    if (check==13){
                        eT13.setText(sb.toString());
                        check = 15;
                    }
                    /*
                    else if (check==14){
                        eT14.setText(sb.toString());
                        check = 15;
                    }

                     */
                    else if (check==15){
                        eT15.setText(sb.toString());
                        check = 16;
                    }
                    else if (check==16){
                        eT16.setText(sb.toString());
                        check = 17;
                    }
                    else if (check==17){
                        eT17.setText(sb.toString());
                        check = 18;
                    }
                    else if (check==18){
                        eT18.setText(sb.toString());
                        check = 19;
                    }
                    else if (check==19){
                        eT19.setText(sb.toString());
                        check = 20;
                    }
                    else if (check==20){
                        eT20.setText(sb.toString());
                        check = 21;
                    }
                    else if (check==21){
                        eT21.setText(sb.toString());
                        check = 22;
                    }
                    else if (check==22){
                        eT22.setText(sb.toString());
                        check = 23;
                    }
                    else if (check==23){
                        eT23.setText(sb.toString());
                        check = 24;
                    }
                    else if (check==24){
                        eT24.setText(sb.toString());
                        check = 25;
                    }
                    else if (check==25){
                        eT25.setText(sb.toString());
                        check = 26;
                    }
                    else if (check==26){
                        eT26.setText(sb.toString());
                        check = 27;
                    }
                    else if (check==27){
                        eT27.setText(sb.toString());
                        check = 28;
                    }
                    else if (check==28){
                        eT28.setText(sb.toString());
                        check = 29;
                    }
                    else if (check==29){
                        eT29.setText(sb.toString());
                    }
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