package com.sk.notepad.views.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.sk.notepad.R;
import com.sk.notepad.listener.ToolBarUpdtaeListener;
import com.sk.notepad.mvp.modle.DataBaseReader;
import com.sk.notepad.mvp.modle.Note;
import com.sk.notepad.mvp.presenters.NoteViewPresenter;
import com.sk.notepad.mvp.views.NoteView;
import com.sk.notepad.utils.DividerItemDecoration;
import com.sk.notepad.utils.RecyclerViewClickListener;
import com.sk.notepad.views.adapters.NotepadAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sandeep Tiwari on 7/16/2016.
 */
public class NotePadViewFragment extends Fragment implements
        RecyclerViewClickListener, View.OnClickListener {

    private static final String TAG = NotePadViewFragment.class.getSimpleName();
    private ToolBarUpdtaeListener mToolBarUpdtaeListener;
    private NotepadAdapter mNotepadAdapter;
    private Snackbar mSnackbar;


    // @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    //@BindView(R.id.activity_movies_progress)
    ProgressBar mProgressBar;
    //@BindView(R.id.coordinatorLayout)
    CoordinatorLayout mCoordinatorLayout;
    private Context mContext;
    private NoteViewPresenter presenter;
    private List<Note> noteList;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mToolBarUpdtaeListener = (ToolBarUpdtaeListener) context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
        mToolBarUpdtaeListener = (ToolBarUpdtaeListener) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmet_notpadview, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mToolBarUpdtaeListener.onHomeViewLaunch(true);
        init();
    }

    private void init() {
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.my_recycler_view);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        //mProgressBar = (ProgressBar)getView().findViewById(R.id.activity_movies_progress);
        mCoordinatorLayout = (CoordinatorLayout) getView().findViewById(R.id.coordinatorLayout);
        if (presenter != null)
            showNotes(presenter.showAllNotes());
    }

    public void setPresenter(NoteViewPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onClick(View v, int position) {
        if (mNotepadAdapter != null) {
            Note note = mNotepadAdapter.getNoteList().get(position);
            mToolBarUpdtaeListener.launchUpdateView(note);
        }
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    public void showNotes(List<Note> noteList) {
        Log.i(TAG, "Set Adapter " + noteList.size());
        if (noteList != null && noteList.size() > 0) {
            mNotepadAdapter = new NotepadAdapter(noteList);
            mNotepadAdapter.setRecyclerListListener(this);
            mRecyclerView.setAdapter(mNotepadAdapter);
            Log.i(TAG, "Set Adapter done");
        }
    }

    public void showLoading() {
        if (mProgressBar != null)
            mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void showLoadingLabel() {
        mSnackbar = Snackbar
                .make(mCoordinatorLayout, "Message is deleted", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar snackbar1 = Snackbar.make(mCoordinatorLayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                    }
                });

        mSnackbar.show();
    }

    public void hideActionLabel() {
        if (mSnackbar != null) {
            mSnackbar.dismiss();
        }
    }

    public boolean isTheListEmpty() {
        return (mNotepadAdapter == null) || mNotepadAdapter.getNoteList().isEmpty();
    }

    public void appendNote(List<Note> movieList) {
        mNotepadAdapter.appendNotes(movieList);
    }

    public void refreshView(int mode) {
        Log.i(TAG, "refresh view mode" + mode);
    }


    public NotepadAdapter getmNotepadAdapter() {
        return mNotepadAdapter;
    }
}
