package com.m27lab.m27labnotemvvm.views.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.HeaderViewListAdapter;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.m27lab.m27labnotemvvm.R;
import com.m27lab.m27labnotemvvm.helpers.getRandomString;
import com.m27lab.m27labnotemvvm.repository.models.NoteModel;
import com.m27lab.m27labnotemvvm.viewmodels.HomeActivityViewModel;
import com.m27lab.m27labnotemvvm.views.adapters.NotesListAdapter;

import java.util.ArrayList;
import java.util.Date;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity {
    private HomeActivityViewModel homeActivityViewModel;
    private RecyclerView noteListRV;
    private FloatingActionButton addnote;
    private NotesListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //find view by id
        noteListRV=findViewById(R.id.noteListRV);
        addnote=findViewById(R.id.addnote);
        EditText note_search = findViewById(R.id.note_search);

        //init paper
        Paper.init(this);

        //init view-model
        homeActivityViewModel=  new ViewModelProvider(this).get(HomeActivityViewModel.class);
        homeActivityViewModel.getNoteList().observe(this, new Observer<ArrayList<NoteModel>>() {
            @Override
            public void onChanged(ArrayList<NoteModel> noteList) {
                //Toast.makeText(getApplicationContext(),"called observer",Toast.LENGTH_SHORT).show();
                //update recycler view
                //adapter.setNoteList(noteList);
                adapter.notifyDataSetChanged();
            }
        });

        initRecyclerView();

        addnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),SingleNoteActivity.class);
                Pair[] pairs=new Pair[1];
                pairs[0]=new Pair<View,String>(addnote,"noteButton");
                ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(HomeActivity.this,pairs);
                startActivity(i,options.toBundle());
            }
        });

        //set textwatcher..
        note_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                filterNow(s.toString());
            }
        });



    }//onCreate

    private void filterNow(String str) {
        ArrayList<NoteModel> filterdList=new ArrayList<>();
        for(NoteModel item:homeActivityViewModel.getNoteList().getValue()){
            if(item.getTitle().toLowerCase().contains(str.toLowerCase()) || item.getDescription().toLowerCase().contains(str.toLowerCase())){
                    filterdList.add(item);
            }
        }

        adapter.setFilteredList(filterdList);
    }

    private void initRecyclerView() {
        adapter=new NotesListAdapter(this,homeActivityViewModel.getNoteList().getValue());
        noteListRV.setLayoutManager(new LinearLayoutManager(this));
        new ItemTouchHelper(touchHelper).attachToRecyclerView(noteListRV);
        noteListRV.setAdapter(adapter);


    }//initRecyclerView

    //sarch option
    ItemTouchHelper.SimpleCallback touchHelper=new ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int pos=viewHolder.getAdapterPosition();
            homeActivityViewModel.deleteNote(adapter.getNoteAt(pos));

            //check do we need to call notifiy or not
        }
    };

    //get button view
    public View getButton(){
        return this.addnote;
    }

}
