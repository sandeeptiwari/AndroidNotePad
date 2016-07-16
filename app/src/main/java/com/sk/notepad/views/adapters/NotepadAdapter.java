package com.sk.notepad.views.adapters;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sk.notepad.R;
import com.sk.notepad.mvp.modle.Note;
import com.sk.notepad.utils.RecyclerViewClickListener;

import java.util.List;

/**
 * Created by Sandeep Tiwari on 7/16/2016.
 */
public class NotepadAdapter extends RecyclerView.Adapter<NotepadAdapter.NotePadViewHolder> {
    private static final String TAG = NotepadAdapter.class.getSimpleName();
    private Context mContext;
    private List<Note> mNoteList;
    private RecyclerViewClickListener mRecyclerClickListener;

    public NotepadAdapter(List<Note> mNoteList) {

        this.mNoteList = mNoteList;
    }

    public List<Note> getNoteList() {

        return mNoteList;
    }

    public void appendNotes(List<Note> movieList){
        mNoteList.addAll(movieList);
        notifyDataSetChanged();
    }

    public void setRecyclerListListener(RecyclerViewClickListener mRecyclerClickListener) {
        this.mRecyclerClickListener = mRecyclerClickListener;
    }

    @Override
    public NotePadViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.notepad_item, parent, false);

        // Return a new holder instance
        NotePadViewHolder viewHolder = new NotePadViewHolder(contactView, mRecyclerClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NotePadViewHolder holder, int position) {
        Note selectedNotes = mNoteList.get(position);

        holder.titleTextView.setText(selectedNotes.getTitle());
        holder.noteDateTextView.setText(selectedNotes.getCreatedDate());
    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "Items "+mNoteList.size());
        return mNoteList.size();
    }


    public static  class NotePadViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewClickListener onClickListener;
        TextView titleTextView, noteDateTextView;
        private RelativeLayout mParent;

        public NotePadViewHolder(View itemView, final RecyclerViewClickListener onClickListener) {

            super(itemView);
            mParent = (RelativeLayout)itemView.findViewById(R.id.parent);
            titleTextView = (TextView) itemView.findViewById(R.id.text_note_title);
            noteDateTextView = (TextView) itemView.findViewById(R.id.note_date_txt);
            this.onClickListener = onClickListener;
            mParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onClick(view, getAdapterPosition());
                }
            });
        }
    }

}
