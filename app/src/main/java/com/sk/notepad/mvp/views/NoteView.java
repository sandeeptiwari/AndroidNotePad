package com.sk.notepad.mvp.views;

import com.sk.notepad.mvp.modle.Note;

import java.util.List;

/**
 * Created by Sandeep Tiwari on 7/16/2016.
 */
public interface NoteView extends MVPView {

    void showNotes(List<Note> noteList);

    void showLoading ();

    void hideLoading ();

    void showLoadingLabel();

    void hideActionLabel ();

    boolean isTheListEmpty ();

    void appendNote (List<Note> movieList);

    void refreshView(int mode);
}
