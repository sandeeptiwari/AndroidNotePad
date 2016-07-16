package com.sk.notepad.mvp.modle;
import java.util.List;

/**
 * Created by Sandeep Tiwari on 7/16/2016.
 */
public interface DBInteracter {

    void saveNote(Note note);
    List<Note> getAllNotes();
    void updateNote(int id, String value, String title);
    int deleteNote(int id);
    Note getNoteById(int id);
}
