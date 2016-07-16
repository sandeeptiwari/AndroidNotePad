package com.sk.notepad.listener;

import com.sk.notepad.mvp.modle.Note;

/**
 * Created by Sandeep Tiwari on 7/16/2016.
 */
public interface ToolBarUpdtaeListener {

    void onHomeViewLaunch(boolean isHomeView);
    void lauchHomeView();
    void launchUpdateView(Note note);
}
