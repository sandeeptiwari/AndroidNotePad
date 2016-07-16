package com.sk.notepad.mvp.presenters;

import android.util.Log;

import com.sk.notepad.mvp.modle.DBInteracter;
import com.sk.notepad.mvp.modle.DBInteracterImpl;
import com.sk.notepad.mvp.modle.DataBaseReader;
import com.sk.notepad.mvp.modle.Note;
import com.sk.notepad.mvp.views.NoteView;
import com.sk.notepad.provider.BusProvider;
import com.sk.notepad.provider.Event;
import com.squareup.otto.Subscribe;

import java.util.List;

/**
 * Created by Sandeep Tiwari on 7/16/2016.
 */
public class NoteViewPresenter extends Presenter {

    private static final String TAG = NoteViewPresenter.class.getSimpleName();
    private NoteView mNotesView;
    private DBInteracter mDbInteracter;


    public void attachView (NoteView notesView, DataBaseReader db) {
        mNotesView = notesView;
        mDbInteracter = new DBInteracterImpl(db);
    }

    @Subscribe
    public void onDateInserted(Event.onDateInsertedEvent event) {
        Log.i(TAG, "EVENT RECIEVE data inserted");
        List<Note> notes = mDbInteracter.getAllNotes();

        if (mNotesView.isTheListEmpty()) {
            mNotesView.hideLoading();
            mNotesView.appendNote(notes);
        } else {
            mNotesView.hideActionLabel();
            mNotesView.showNotes(notes);
        }
    }

    @Subscribe
    public void onNoteDeleted(Event.onNoteDeletedEvent event){
        mNotesView.refreshView(0);
    }

    @Subscribe
    public void onUpdateNote(Event.onUpdateNoteEvent event){
        mNotesView.refreshView(1);
    }



    @Override
    public void start() {

        if (mNotesView.isTheListEmpty()) {
            Log.i(TAG, "register bus in presenter");
            BusProvider.getInstance().register(this);
            mNotesView.showLoading();
        }

    }

    @Override
    public void stop() {
    }

    @Override
    public void saveNote(Note note) {
        mDbInteracter.saveNote(note);
    }

    @Override
    public List<Note> showAllNotes() {
        return  mDbInteracter.getAllNotes();
    }

    public Note getNote(int id) {
        return mDbInteracter.getNoteById(id);
    }

    public void deleteNote(int id) {
        mDbInteracter.deleteNote(id);
    }

    public void updateNote(int id, String noteDetails, String title) {
        mDbInteracter.updateNote(id, noteDetails, title);
    }
}
