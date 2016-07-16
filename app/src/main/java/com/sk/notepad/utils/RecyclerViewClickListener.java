package com.sk.notepad.utils;

import android.view.View;

/**
 * Created by Sandeep Tiwari on 7/16/2016.
 */
public interface RecyclerViewClickListener {

    /**
     * Callback method to be invoked when a item in a
     * RecyclerView is clicked
     *  @param v The view within the RecyclerView.Adapter
     * @param position The position of the view in the adapter
     */
    public void onClick(View v, int position);
}
