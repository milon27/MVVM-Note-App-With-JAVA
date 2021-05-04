package com.m27lab.m27labnotemvvm.views.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.m27lab.m27labnotemvvm.R;
import com.m27lab.m27labnotemvvm.repository.models.NoteModel;
import com.m27lab.m27labnotemvvm.viewmodels.HomeActivityViewModel;
import com.m27lab.m27labnotemvvm.views.activity.HomeActivity;
import com.m27lab.m27labnotemvvm.views.activity.SingleNoteActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.NoteViewHolder>{
    private Context context;
    private ArrayList<NoteModel> noteList;

    public NotesListAdapter(Context context,ArrayList<NoteModel> noteList) {
        this.noteList = noteList;
        this.context = context;
    }//Constructor

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_note_list,parent,false);
        return new NoteViewHolder(view);
    }//onCreateViewHolder

    @Override
    public void onBindViewHolder(@NonNull final NoteViewHolder holder, final int position) {
        holder.notetitle.setText(""+noteList.get(position).getTitle());
        holder.noteDesc.setText(""+noteList.get(position).getDescription());
        holder.notelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair[] pairs=new Pair[3];

                pairs[0] =new Pair<View,String>(holder.notetitle,"noteTitle");
                pairs[1] =new Pair<View,String>(holder.noteDesc,"noteDESC");
                pairs[2] =new Pair<View,String>(((HomeActivity)(context)).getButton(),"noteButton");

                ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(((Activity)(context)),pairs);

                Intent i=new Intent(context, SingleNoteActivity.class);
                i.putExtra("note_obj",noteList.get(position));
                context.startActivity(i,options.toBundle());

            }
        });
    }//onBindViewHolder

    //get note item
    public NoteModel getNoteAt(int position){
        return noteList.get(position);
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }//getItemCount

     //view holder class
     class NoteViewHolder extends RecyclerView.ViewHolder {
        CardView notelayout;
        TextView notetitle,noteDesc;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            notelayout=itemView.findViewById(R.id.notelayout);
            notetitle=itemView.findViewById(R.id.notetitle);
            noteDesc=itemView.findViewById(R.id.description);
        }
    }


//    ===================filter note list by search...
    public void setFilteredList(ArrayList<NoteModel> newlist){
        this.noteList=newlist;
        notifyDataSetChanged();
    }



}
