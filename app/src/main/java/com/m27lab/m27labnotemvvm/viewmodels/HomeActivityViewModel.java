package com.m27lab.m27labnotemvvm.viewmodels;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.m27lab.m27labnotemvvm.repository.repos.NoteDbRepository;
import com.m27lab.m27labnotemvvm.repository.models.NoteModel;
import java.util.ArrayList;

public class HomeActivityViewModel extends AndroidViewModel {
    private Application application;
    private MutableLiveData<ArrayList<NoteModel>> allnotes;
    private NoteDbRepository noteRepo;

    public HomeActivityViewModel(@NonNull Application application) {
        super(application);
        this.application=application;
        init();
    }
    //init the repository
    private void init(){
        if(allnotes!=null){
            return;
        }//already we have retrieve the data
        noteRepo=NoteDbRepository.getInstance(application);
        allnotes=noteRepo.getAllNotes();
    }

    //get immutable live data
    public LiveData<ArrayList<NoteModel>> getNoteList(){
        return allnotes;
    }

    //insert new note
    public void addNote(NoteModel note){
        noteRepo.addNewNote(note);//added to the database
        ArrayList<NoteModel> updatedList=allnotes.getValue();//get
        allnotes.setValue(updatedList);

        //Toast.makeText(getApplication().getApplicationContext(),"Added New Note.",Toast.LENGTH_SHORT).show();
    }
    //delete note
    public void deleteNote(NoteModel note){
        noteRepo.removeNote(note);//added to the database
        ArrayList<NoteModel> updatedList=allnotes.getValue();//get
        allnotes.setValue(updatedList);
        //Toast.makeText(getApplication().getApplicationContext(),"Removed Note.",Toast.LENGTH_SHORT).show();
    }




}
