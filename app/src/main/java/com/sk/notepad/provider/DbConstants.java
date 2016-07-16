/*
 * Copyright (C) 2015 Saúl Molinero.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sk.notepad.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Ismael on 31/01/2015.
 */
public class DbConstants {

    public static final String PROVIDER_NAME = "com.sk.notepad.provider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/"
            + Notes.TABLE_NAME);

    public final static String DB_NAME = "notepad.db";
    public final static int DB_VERSION = 1;

    public class Notes {
        public static final String TABLE_NAME = "notes";
        public static final String ID = BaseColumns._ID;
        public static final String TITLE       = "title";
        public static final String NOTE_DETAIL  = "note_detail";
        public static final String CREATEDDATE  = "created_date";
        public static final String UPDATEDDATE  = "updated_date";

        public static final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TITLE + " TEXT, " + NOTE_DETAIL + " TEXT, " +CREATEDDATE+" TEXT, " +UPDATEDDATE+" TEXT)";
        public static final String DEFAULT_SORT_ORDER = ID + " ASC";
    }


}
