package com.sk.notepad.views.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.sk.notepad.R;
import com.sk.notepad.listener.ToolBarUpdtaeListener;
import com.sk.notepad.mvp.modle.DataBaseReader;
import com.sk.notepad.mvp.modle.Note;
import com.sk.notepad.mvp.presenters.NoteViewPresenter;
import com.sk.notepad.mvp.views.NoteView;
import com.sk.notepad.views.fragments.CreateNoteFragment;
import com.sk.notepad.views.fragments.NotePadViewFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NoteView,
        ToolBarUpdtaeListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private NotePadViewFragment notePadFragment;
    private NoteViewPresenter mNoteViewPresenter;

    //@BindView(R.id.tool_bar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ButterKnife.bind(this);
        initializeToolbar();
        DataBaseReader db = new DataBaseReader(this); // this being context
        mNoteViewPresenter = new NoteViewPresenter();
        mNoteViewPresenter.attachView(this, db);

        if (findViewById(R.id.frag_container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            notePadFragment = new NotePadViewFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            notePadFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getFragmentManager().beginTransaction()
                    .add(R.id.frag_container, notePadFragment).addToBackStack("notePadFrag").commit();
            notePadFragment.setPresenter(mNoteViewPresenter);
        }
    }
    private Menu menu;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_main, menu);
        onHomeViewLaunch(true);
        return true;
    }

    @Override
    public void onHomeViewLaunch(boolean isShow){
        if(menu == null)
            return;

        if(isShow){
            MenuItem item = menu.findItem(R.id.menu_save);
            item.setVisible(false);
            MenuItem item1 = menu.findItem(R.id.menu_delete);
            item1.setVisible(false);
            MenuItem item2 = menu.findItem(R.id.new_note);
            item2.setVisible(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }else{
            MenuItem item = menu.findItem(R.id.menu_save);
            item.setVisible(true);
            MenuItem item1 = menu.findItem(R.id.menu_delete);
            item1.setVisible(true);
            MenuItem item2 = menu.findItem(R.id.new_note);
            item2.setVisible(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public void lauchHomeView() {
        getFragmentManager().popBackStackImmediate();
    }

    @Override
    public void launchUpdateView(Note note) {
         Bundle bundle = new Bundle();
        bundle.putSerializable("Note", note);
        createNewNote(bundle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.new_note) {
            Toast.makeText(MainActivity.this, "Refresh App", Toast.LENGTH_LONG).show();
            createNewNote(null);
        }
        if (id == R.id.menu_save) {
            newNote.saveNote(mNoteViewPresenter);
        }
        if (id == R.id.menu_delete) {
            Toast.makeText(MainActivity.this, "Wait till next check-in", Toast.LENGTH_LONG).show();
        }
        if (item.getItemId() == android.R.id.home){
            lauchHomeView();
        }
        return true;
    }
    private CreateNoteFragment newNote;
    private void createNewNote(Bundle bundle){
        newNote = new CreateNoteFragment();
        if(bundle != null){
            newNote.setArguments(bundle);
        }
        getFragmentManager().beginTransaction()
                .replace(R.id.frag_container, newNote).addToBackStack("newNote").commit();
    }
    private void initializeToolbar() {
        mToolbar = (Toolbar)findViewById(R.id.tool_bar);
        Log.i(TAG, "Toolbar "+mToolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("NotePad");
    }

    @Override
    public void onStart() {
        super.onStart();
        mNoteViewPresenter.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        mNoteViewPresenter.stop();
    }

    @Override
    public void showNotes(List<Note> noteList) {
        Log.i(TAG, "Set Adapter");
        notePadFragment.showNotes(noteList);
    }

    @Override
    public void showLoading() {
        notePadFragment.showLoading();
    }

    @Override
    public void hideLoading() {
        notePadFragment.hideLoading();
    }

    @Override
    public void showLoadingLabel() {
        notePadFragment.showLoadingLabel();
    }

    @Override
    public void hideActionLabel() {
        notePadFragment.hideActionLabel();
    }

    @Override
    public boolean isTheListEmpty() {
        return notePadFragment.isTheListEmpty();
    }

    @Override
    public void appendNote(List<Note> noteList) {
        notePadFragment.appendNote(noteList);
    }

    @Override
    public void refreshView(int mode) {
        Log.i(TAG, "refresh view mode"+mode);
    }

    @Override
    public Context getContext() {
        return this;
    }



}
