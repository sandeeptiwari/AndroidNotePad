package com.sk.notepad.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Sandeep Tiwari on 7/16/2016.
 */
public class DBContract {

    public static final String 	CONTENT_AUTHORITY 	= "com.sk.notepad.content";
    private static final Uri BASE_CONTENT_URI 	= Uri.parse("content://" + CONTENT_AUTHORITY);

    public interface NoteColumns {
        String ID           = BaseColumns._ID;
        String TITLE        = "title";
        String NOTE_DETAIL  = "note_detail";
        String CREATEDDATE  = "created_date";
        String UPDATEDDATE  = "updated_date";
    }

    private static final String PATH_FLOWER_APP = "note";

    public static class NoteAppUri implements NoteColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_FLOWER_APP).build();
    }
}
