package com.sk.notepad.mvp.modle;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.sk.notepad.provider.BusProvider;
import com.sk.notepad.provider.DBContract;
import com.sk.notepad.provider.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sandeep Tiwari on 7/16/2016.
 */
public class DBInteracterImpl implements DBInteracter {
    private static final String TAG = DBInteracterImpl.class.getSimpleName();
    private final DataBaseReader db;

    public DBInteracterImpl(DataBaseReader db) {
        this.db = db;
    }

    @Override
    public void saveNote(Note note) {
        if(db.getContext() == null)
            return;

        ContentValues values = new ContentValues();
        values.put(DBContract.NoteColumns.TITLE, note.getTitle());
        values.put(DBContract.NoteColumns.NOTE_DETAIL, note.getTxt());
        values.put(DBContract.NoteColumns.CREATEDDATE, note.getCreatedDate());
        values.put(DBContract.NoteColumns.UPDATEDDATE, note.getUpdateDate());
        db.getContext().getContentResolver().insert(DBContract.NoteAppUri.CONTENT_URI, values);
        Log.i(TAG, "insertion is complete");
        BusProvider.getInstance().post(new Event.onDateInsertedEvent());
    }

    @Override
    public List<Note> getAllNotes() {
        if (db.getContext() == null) {
            Log.i(TAG, "Context Null "+db.getContext());
            return null;
        }

        List<Note> flowers = new ArrayList<Note>();
        String where = "";

        Log.i(TAG, "check where clause in PostDataHelper :->" + where);

        Cursor cursor = db.getContext().getContentResolver().query(DBContract.NoteAppUri.CONTENT_URI,
                null, null, null, null);

        int count = cursor.getCount();
        Log.i(TAG, "cursor count :->" + count);

        if (count == 0)
            return null;

        while (cursor.moveToNext()) {
            Note note = new Note();
            int noteId = cursor.getInt(cursor.getColumnIndex(DBContract.NoteColumns.ID));
            note.setNoteId(noteId);

            String title = cursor.getString(cursor.getColumnIndex(DBContract.NoteColumns.TITLE));
            note.setTitle(title);

            String detail = cursor.getString(cursor.getColumnIndex(DBContract.NoteColumns.NOTE_DETAIL));
            note.setTxt(detail);

            String createddate = cursor.getString(cursor.getColumnIndex(DBContract.NoteColumns.CREATEDDATE));
            note.setCreatedDate(createddate);

            String updateddate = cursor.getString(cursor.getColumnIndex(DBContract.NoteColumns.UPDATEDDATE));
            note.setUpdateDate(updateddate);

            flowers.add(note);
        }
        Log.i(TAG, "note size" +flowers.size());
        return flowers;
    }

    @Override
    public void updateNote(int id, String value, String title) {
        if (db.getContext() == null) {
            Log.i(TAG, "Context Null "+db.getContext());
            return;
        }
        ContentValues values = new ContentValues();
        String where = DBContract.NoteColumns.ID + "==" + id;
        values.put(DBContract.NoteColumns.NOTE_DETAIL, value);
        values.put(DBContract.NoteColumns.TITLE, title);
        db.getContext().getContentResolver().update(DBContract.NoteAppUri.CONTENT_URI, values, where, null);
        Log.i(TAG, "note Update successfully");
        BusProvider.getInstance().post(new Event.onUpdateNoteEvent());
    }

    @Override
    public int deleteNote(int id) {
        if (db.getContext() == null) {
            Log.i(TAG, "Context Null "+db.getContext());
            return -1;
        }

        String where = DBContract.NoteColumns.ID + "==" + id;
        int result = db.getContext().getContentResolver().delete(DBContract.NoteAppUri.CONTENT_URI, where, null);
        Log.i(TAG, "note deleted successfully");
        BusProvider.getInstance().post(new Event.onNoteDeletedEvent());
        return result;
    }

    @Override
    public Note getNoteById(int id) {
        Note note = new Note();
        String where = DBContract.NoteColumns.ID + "==" + id;
        Log.i(TAG, "check where clause in UserDataHelper :->"+where);

        Cursor cursor = db.getContext().getContentResolver().query(DBContract.NoteAppUri.CONTENT_URI,
                null, where, null, null);

        int count = cursor.getCount();
        Log.i(TAG, "cursor count :->"+count);

        if(count == 0)
            return null;

        while(cursor.moveToNext())
        {
            int noteId = cursor.getInt(cursor.getColumnIndex(DBContract.NoteColumns.ID));
            note.setNoteId(noteId);

            String title = cursor.getString(cursor.getColumnIndex(DBContract.NoteColumns.TITLE));
            note.setTitle(title);

            String detail = cursor.getString(cursor.getColumnIndex(DBContract.NoteColumns.NOTE_DETAIL));
            note.setTxt(detail);

            String createddate = cursor.getString(cursor.getColumnIndex(DBContract.NoteColumns.CREATEDDATE));
            note.setCreatedDate(createddate);

            String updateddate = cursor.getString(cursor.getColumnIndex(DBContract.NoteColumns.UPDATEDDATE));
            note.setUpdateDate(updateddate);
        }

        return note;
    }
}
