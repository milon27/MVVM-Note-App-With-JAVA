package com.m27lab.m27labnotemvvm.helpers;

import android.content.Context;
import android.widget.Toast;

import com.m27lab.m27labnotemvvm.repository.models.NoteModel;

import java.util.ArrayList;

public class Helpers {
    public static void TOAST(Context context,String msge){
        Toast.makeText(context,msge,Toast.LENGTH_SHORT).show();
    }

    public static int binarySearch(ArrayList<NoteModel> list, NoteModel key ) {

        Comparable comp = (Comparable)key.getId();
        /*
        -1 no value
        or position
         */
        int res = -1, min = 0, max = list.size() - 1, pos;
        while( ( min <= max ) && ( res == -1 ) ) {
            pos = (min + max) / 2;
            int comparison = comp.compareTo(list.get(pos).getId());
            if( comparison == 0)
                res = pos;
            else if( comparison < 0)
                max = pos - 1;
            else
                min = pos + 1;
        }
        return res;
    }
}
