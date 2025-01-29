package com.acarpio.acarpio_projectdb;

import android.provider.BaseColumns;

public final class Entities {

    // Private constructor to avoid it being instantiated.
    // Following: https://developer.android.com/training/data-storage/sqlite#java
    private Entities() {} ;

    // Inner class that will define the table.

    public static class CommentEntry implements BaseColumns {
        public static final String TABLE_NAME = "comments";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_TEXT = "text";
    }

}
