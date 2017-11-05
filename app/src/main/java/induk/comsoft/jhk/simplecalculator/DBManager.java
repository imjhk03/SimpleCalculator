package induk.comsoft.jhk.simplecalculator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JHK on 16. 6. 22..
 */
public class DBManager extends SQLiteOpenHelper {

    public DBManager(Context context) {
        super(context, "myDBTest", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table calculate (calc_date DATETIME default CURRENT_TIMESTAMP, calculation text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}