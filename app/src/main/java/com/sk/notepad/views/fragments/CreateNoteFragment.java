package com.sk.notepad.views.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.sk.notepad.R;
import com.sk.notepad.listener.ToolBarUpdtaeListener;
import com.sk.notepad.mvp.modle.Note;
import com.sk.notepad.mvp.presenters.NoteViewPresenter;
import com.sk.notepad.utils.Utils;
import com.sk.notepad.views.adapters.NotepadAdapter;

/**
 * Created by Sandeep Tiwari on 7/16/2016.
 */
public class CreateNoteFragment extends Fragment {

    private static final String TAG = NotePadViewFragment.class.getSimpleName();
    private NotepadAdapter mNotepadAdapter;
    private Snackbar mSnackbar;
    //@BindView(R.id.title)
    private EditText mTitle;
    //@BindView(R.id.notelist_date)
    private TextView noteTxt;
    //@BindView(R.id.body)
    private EditText mBody;
    private Context mContext;
    private ToolBarUpdtaeListener mToolBarUpdtaeListener;
    private boolean isUpdate;
    private int id = -1;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mToolBarUpdtaeListener = (ToolBarUpdtaeListener)context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
        mToolBarUpdtaeListener = (ToolBarUpdtaeListener)activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_note_frag, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle noteBundle = getArguments();

        mToolBarUpdtaeListener.onHomeViewLaunch(false);
        mTitle = (EditText)getView().findViewById(R.id.title);
        mBody = (EditText)getView().findViewById(R.id.body);
        noteTxt = (TextView)getView().findViewById(R.id.notelist_date);
        noteTxt.setText(Utils.getDateTime());
        if(noteBundle != null){
            Note note = (Note) noteBundle.getSerializable("Note");
            String title = note.getTitle();
            String detail = note.getTxt();
            String date = note.getUpdateDate();
            mTitle.setText(title);
            mBody.setText(detail);
            noteTxt.setText(date);
            id = note.getNoteId();
            isUpdate = true;
        }else{
            isUpdate = false;
            id = -1;
        }
    }

    public void saveNote(NoteViewPresenter mNoteViewPresenter){

        if(isUpdate){
            mNoteViewPresenter.updateNote(id, mBody.getText().toString(), mTitle.getText().toString());
        }else {
            Note note = new Note();
            note.setTitle(mTitle.getText().toString());
            note.setTxt(mBody.getText().toString());
            note.setCreatedDate(noteTxt.getText().toString());
            note.setUpdateDate(noteTxt.getText().toString());
            mNoteViewPresenter.saveNote(note);
        }
        mToolBarUpdtaeListener.lauchHomeView();
    }
}
