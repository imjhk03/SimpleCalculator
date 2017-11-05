package induk.comsoft.jhk.simplecalculator;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by JHK on 16. 6. 22..
 */
public class CalcHistory extends AppCompatActivity{
    SQLiteDatabase sqlitedb;
    DBManager dbmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calc_history);

        setTitle("History");

        LinearLayout layout = (LinearLayout) findViewById(R.id.calc_history);

        try {
            dbmanager = new DBManager(this);
            sqlitedb = dbmanager.getReadableDatabase();
            Cursor cursor = sqlitedb.query("calculate", null, null, null, null, null, null);

            int i = 0;
            while (cursor.moveToNext()) {
                String date = cursor.getString(cursor.getColumnIndex("calc_date"));
                String calculation = cursor.getString(cursor.getColumnIndex("calculation"));

                LinearLayout layout_list = new LinearLayout(this);

                LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                //LinearLayout.LayoutParams LLParams2 = new LinearLayout.LayoutParams (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                //LLParams2.gravity = layout.setGravity(gravi);

                layout_list.setOrientation(LinearLayout.VERTICAL);
                layout_list.setLayoutParams(LLParams);
                //layout_list.widt
                //layout_list.setPadding(0,0,0,0);
                //if (i % 2 == 1)
                //    layout_list.setBackgroundColor(Color.rgb(100,255,218));

                TextView tv_list = new TextView(this);
                //tv_list.setLayoutDirection();
                //tv_list.setWidth(MATC);
                tv_list.setHeight(50);
                tv_list.setText(date);
                tv_list.setTextSize(15);
                tv_list.setGravity(0x03);
                layout_list.addView(tv_list);

                TextView tv_list2 = new TextView(this);
                tv_list2.setText(calculation);
                tv_list2.setHeight(75);
                tv_list2.setTextSize(18);
                tv_list2.setTextColor(Color.BLACK);
                tv_list2.setGravity(Gravity.RIGHT);
                //tv_list2.setLayoutParams(LLParams2);
                //tv_list2.setPadding(0,0,0,0);
                //tv_list2.setBackgroundColor(Color.rgb(100,255,218));
                //tv_list2.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                //tv_list2.setTextAlignment(Text);
                layout_list.addView(tv_list2);

                layout.addView(layout_list);

                i++;
            }
            cursor.close();
            sqlitedb.close();
            dbmanager.close();
        } catch (SQLException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings1) {
            Intent it = new Intent(this, Calc.class);
            startActivity(it);
            finish();
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }
}
