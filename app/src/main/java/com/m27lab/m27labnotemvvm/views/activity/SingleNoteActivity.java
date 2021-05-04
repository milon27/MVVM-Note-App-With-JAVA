package com.m27lab.m27labnotemvvm.views.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.m27lab.m27labnotemvvm.R;
import com.m27lab.m27labnotemvvm.helpers.Helpers;
import com.m27lab.m27labnotemvvm.repository.models.NoteModel;
import com.m27lab.m27labnotemvvm.viewmodels.HomeActivityViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class SingleNoteActivity extends AppCompatActivity {
    private NoteModel singlenote;
    private EditText title,content;
    private TextView date;
    private ImageView saveNote;
    private NoteModel note;
    private HomeActivityViewModel homeViewmodel;
    String ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_note);

        //find view by id
        title=findViewById(R.id.title);
        content=findViewById(R.id.content);
        date=findViewById(R.id.date);
        saveNote=findViewById(R.id.saveNote);
        ID=String.valueOf((int)System.currentTimeMillis());

        if(getIntent().getSerializableExtra("note_obj")!=null){
            singlenote=(NoteModel)getIntent().getSerializableExtra("note_obj");
            title.setText(singlenote.getTitle()+"");
            content.setText(singlenote.getDescription()+"");
            date.setText("Edited : "+new SimpleDateFormat("MMM d").format(singlenote.getCrated_time()));

            ID=singlenote.getId();
        }

        //get the view-model
        homeViewmodel=new ViewModelProvider(this).get(HomeActivityViewModel.class);


        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title_txt=title.getText().toString().trim();
                String content_txt=content.getText().toString().trim();
                System.out.println("milon: id : "+ID+" title: "+title_txt);

                note=new NoteModel(ID,title_txt,content_txt,new Date());

                homeViewmodel.addNote(note);
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            }
        });



    }
}
