package com.m27lab.m27labnotemvvm.repository.repos;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.m27lab.m27labnotemvvm.helpers.Helpers;
import com.m27lab.m27labnotemvvm.repository.models.NoteModel;
import com.m27lab.m27labnotemvvm.define.Define;

import java.util.ArrayList;

import io.paperdb.Paper;

public class NoteDbRepository {
    private static final String NOTE_TABLE_KEY= Define.NOTE_TABLE_KEY;
    private ArrayList<NoteModel> noteList;
    private static NoteDbRepository mInstance;
    private static Context mCtx;

    private NoteDbRepository(Context context) {
        this.mCtx=context;
    }

    public static synchronized NoteDbRepository getInstance(Context context){
        if (mInstance == null) {
            mInstance = new NoteDbRepository(context);
        }
        return mInstance;
    }

    //get data from data source( offline db / from web service / fire-store db)
    public MutableLiveData<ArrayList<NoteModel>> getAllNotes(){
        //get data from paper
        loadNote();
        //make them mutable live data
        MutableLiveData<ArrayList<NoteModel>> mutableNoteList=new MutableLiveData<>();
        mutableNoteList.setValue(noteList);
        return mutableNoteList;
    }

    public void addNewNote(NoteModel note) {
        boolean newnote=true;
        int pos=0;

        //int posi=Helpers.binarySearch(noteList,note);

        for(NoteModel snote: noteList){
            if(snote.getId().equals(note.getId())){
                newnote=false;
                pos=noteList.indexOf(snote);
                break;
            }
        }

        //System.out.println("posi"+posi+" pos="+pos);


        if(newnote){
            //add new note
            noteList.add(0,note);
            System.out.println("milon: add new note");
        }else{
            noteList.set(pos,note);
            System.out.println("milon: update note");
        }


        Paper.book().write(NOTE_TABLE_KEY,noteList);
    }

    public void removeNote(NoteModel note) {
        noteList.remove(note);
        Paper.book().write(NOTE_TABLE_KEY,noteList);
    }




    public void loadNote(){
        if(Paper.book().read(NOTE_TABLE_KEY)==null){
            noteList=new ArrayList<>();
        }else {
            noteList=Paper.book().read(NOTE_TABLE_KEY);
        }
    }

}
