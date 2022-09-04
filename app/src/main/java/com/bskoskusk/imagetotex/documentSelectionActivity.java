package com.bskoskusk.imagetotex;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bskoskusk.imagetotex.R;

import java.util.ArrayList;
import java.util.List;

public class documentSelectionActivity extends AppCompatActivity {
    ListView lV;
    List<String> li = new ArrayList<String>();
    ArrayAdapter<String> aA;
    ActionBar actionBar;

    String mTitle[] = {"Simple Latex file", "Research Paper Writing"};
    int images[] = {R.drawable.simpletexlogo, R.drawable.researchpaperlogo};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_selection);

        //initialize actionbar first
        actionBar = getSupportActionBar();
        //then set title
        actionBar.setTitle("Select Document Type");

        lV = findViewById(R.id.listViewDocumentSelection);
        MyAdapter adapter = new MyAdapter(this,mTitle,images);
        lV.setAdapter(adapter);

        lV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    Intent simpleFileActivity = new Intent(getApplicationContext(), com.bskoskusk.imagetotex.simpleFileActivity.class);
                    startActivity(simpleFileActivity);
                }

                if(position == 1){
                    Intent researchPaperActivity = new Intent(getApplicationContext(), com.bskoskusk.imagetotex.researchPaperActivity.class);
                    startActivity(researchPaperActivity);
                }
            }
        });
        /*
        //add list items
        li.add("Simple TeX file");
        li.add("Research Paper Writing");
        //sync list items to listView through array adapter
        aA = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,li);
        lV.setAdapter(aA);
        //set item click listener
        lV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position == 0){
                Intent simpleFileActivity = new Intent(getApplicationContext(), com.bskoskusk.imagetotex.simpleFileActivity.class);
                startActivity(simpleFileActivity);
                }

                if(position == 1){
                Intent researchPaperActivity = new Intent(getApplicationContext(), com.bskoskusk.imagetotex.researchPaperActivity.class);
                startActivity(researchPaperActivity);
                }

            }
        });
        */
    }
    class MyAdapter extends ArrayAdapter<String>{
        Context context;
        String rTitle[];
        int rImgs[];
        MyAdapter(Context c,String title[], int imgs[]){
            super(c, R.layout.listview_layout,R.id.textViewTitle,title);
            this.context = c;
            this.rTitle=title;
            this.rImgs = imgs;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View listView_layout = layoutInflater.inflate(R.layout.listview_layout,parent,false);
            ImageView images = listView_layout.findViewById(R.id.image);
            TextView myTitle = listView_layout.findViewById(R.id.textViewTitle);

            images.setImageResource(rImgs[position]);
            myTitle.setText(rTitle[position]);


            return listView_layout;
        }
    }
}