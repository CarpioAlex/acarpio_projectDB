package com.acarpio.acarpio_projectdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context c) {
        super(c, "comments.db", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + Entities.CommentEntry.TABLE_NAME +
                " (" + Entities.CommentEntry._ID + " INTEGER PRIMARY KEY," +
                Entities.CommentEntry.COLUMN_NAME_TITLE + " TEXT UNIQUE," +
                Entities.CommentEntry.COLUMN_NAME_TEXT + " TEXT )" );

        Log.d("DB", "Database created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Entities.CommentEntry.TABLE_NAME);
        onCreate(db);
    }

    public void addComment(Comment comment) throws SQLiteConstraintException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Entities.CommentEntry.COLUMN_NAME_TITLE, comment.getTitle());
        cv.put(Entities.CommentEntry.COLUMN_NAME_TEXT, comment.getText());
        long result = db.insert(Entities.CommentEntry.TABLE_NAME, null, cv);
        if (result == -1) {
            throw new SQLiteConstraintException();
        }
        db.close();
    }


    // For the purpose of the exercise it doesn't make sense to retrieve everything and only use
    // the title, however I wanted to try returning an object array instad of the title.
    public ArrayList<Comment> getAllComments() {
        ArrayList<Comment> comments = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from " + Entities.CommentEntry.TABLE_NAME, null);
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    Comment comment = new Comment();
                    //Using orthrow because intellij says so.
                    comment.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(Entities.CommentEntry.COLUMN_NAME_TITLE)));
                    comment.setText(cursor.getString(cursor.getColumnIndexOrThrow(Entities.CommentEntry.COLUMN_NAME_TEXT)));
                    comments.add(comment);
                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();
        }
        return comments;
    }

    public String getCommentByTitle(String title) {
        String comment = null;

        SQLiteDatabase db = this.getWritableDatabase();
        String[] projection = {Entities.CommentEntry.COLUMN_NAME_TEXT };

        String selection = Entities.CommentEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = {title};
        Cursor cursor = db.query(
                Entities.CommentEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
                );
        while(cursor.moveToNext()) {
            comment = cursor.getString(cursor.getColumnIndexOrThrow(Entities.CommentEntry.COLUMN_NAME_TEXT));

        }
        if (comment == null) {
            throw new NullPointerException();
        } else {
            return comment;
        }
    }

    public void deleteCommentByTitle(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = Entities.CommentEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = {title};
        db.delete(Entities.CommentEntry.TABLE_NAME, selection, selectionArgs);

    }

}
