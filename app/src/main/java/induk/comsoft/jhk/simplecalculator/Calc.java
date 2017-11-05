package induk.comsoft.jhk.simplecalculator;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class Calc extends AppCompatActivity implements View.OnClickListener {

    Button one, two, three, four, five, six, seven, eight, nine, zero, d_zero, add, sub, mul, div, clear, del, percent, equal, dot;
    EditText display;
    TextView result, history;
    double op3;
    double op4;
    String optr;
    int count = 0;
    //String calculation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_calc);

        one = (Button) findViewById(R.id.one);
        two = (Button) findViewById(R.id.two);
        three = (Button) findViewById(R.id.three);
        four = (Button) findViewById(R.id.four);
        five = (Button) findViewById(R.id.five);
        six = (Button) findViewById(R.id.six);
        seven = (Button) findViewById(R.id.seven);
        eight = (Button) findViewById(R.id.eight);
        nine = (Button) findViewById(R.id.nine);
        d_zero = (Button) findViewById(R.id.d_zero);
        zero = (Button) findViewById(R.id.zero);
        add = (Button) findViewById(R.id.add);
        sub = (Button) findViewById(R.id.sub);
        mul = (Button) findViewById(R.id.mul);
        div = (Button) findViewById(R.id.div);
        clear = (Button) findViewById(R.id.clear);
        del = (Button) findViewById(R.id.del);
        percent = (Button) findViewById(R.id.percent);
        equal = (Button) findViewById(R.id.equal);
        dot = (Button) findViewById(R.id.dot);

        display = (EditText) findViewById(R.id.display);
        result = (TextView) findViewById(R.id.result);
        history = (TextView) findViewById(R.id.history);

        try {
            one.setOnClickListener(this);
            two.setOnClickListener(this);
            three.setOnClickListener(this);
            four.setOnClickListener(this);
            five.setOnClickListener(this);
            six.setOnClickListener(this);
            seven.setOnClickListener(this);
            eight.setOnClickListener(this);
            nine.setOnClickListener(this);
            d_zero.setOnClickListener(this);
            zero.setOnClickListener(this);
            add.setOnClickListener(this);
            sub.setOnClickListener(this);
            mul.setOnClickListener(this);
            div.setOnClickListener(this);
            clear.setOnClickListener(this);
            del.setOnClickListener(this);
            percent.setOnClickListener(this);
            equal.setOnClickListener(this);
            dot.setOnClickListener(this);
        } catch (Exception e) {

        }
    }

    public void operation() {
        if(optr.equals("+")) {
            String[] parts = display.getText().toString().split(Pattern.quote("+"));
            String s_op3 = parts[0];
            String s_op4 = parts[1];
            op3 = Double.parseDouble(s_op3);
            op4 = Double.parseDouble(s_op4);
            //op4 = Double.parseDouble(display.getText().toString().trim());
            //display.setText("");
            op3 = op3 + op4;
            result.setText(Double.toString(op3));
            count++;
        }
        else if(optr.equals("-")) {
            String[] parts = display.getText().toString().split(Pattern.quote("-"));
            String s_op3 = parts[0];
            String s_op4 = parts[1];
            op3 = Double.parseDouble(s_op3);
            op4 = Double.parseDouble(s_op4);
            //op4 = Double.parseDouble(display.getText().toString());
            //display.setText("");
            op3 = op3 - op4;
            result.setText(Double.toString(op3));
            count++;
        }
        else if(optr.equals("x")) {
            String[] parts = display.getText().toString().split(Pattern.quote("x"));
            String s_op3 = parts[0];
            String s_op4 = parts[1];
            op3 = Double.parseDouble(s_op3);
            op4 = Double.parseDouble(s_op4);
            //op4 = Double.parseDouble(display.getText().toString());
            //display.setText("");
            op3 = op3 * op4;
            result.setText(Double.toString(op3));
            count++;
        }
        else if(optr.equals("÷")) {
            String[] parts = display.getText().toString().split(Pattern.quote("÷"));
            String s_op3 = parts[0];
            String s_op4 = parts[1];
            op3 = Double.parseDouble(s_op3);
            op4 = Double.parseDouble(s_op4);
            //op4 = Double.parseDouble(display.getText().toString());
            //display.setText("");
            op3 = op3 / op4;
            result.setText(Double.toString(op3));
            count++;
        }
        else if(optr.equals("%")) {
            String re = display.getText().toString();
            re = re.replace("%", "x");
            String[] parts = re.split(Pattern.quote("x"));
            System.out.println(parts[0] + " " + parts[1]);
            String s_op3 = parts[0];
            String s_op4 = parts[1];
            op3 = Double.parseDouble(s_op3);
            op4 = Double.parseDouble(s_op4);
            //op4 = Double.parseDouble(display.getText().toString());
            //display.setText("");
            op3 = op3 * op4 * 0.01;
            result.setText(Double.toString(op3));
            count++;
        }
        String text = display.getText() + "=" + result.getText();
        //history.setText(display.getText() + "=" + result.getText());
        insertHistory(text);
        System.out.println("insert 후 op3, op4 : " + op3 + " " + op4);
    }

    public void insertHistory(String result) {
        Intent it = new Intent(this, Calc.class);
        it.putExtra("history",result);

        SQLiteDatabase sqlitedb;
        DBManager dbmanager;

        //Intent n_it = getIntent();
        String cal_history = it.getStringExtra("history");

        try {
            dbmanager = new DBManager(this);
            sqlitedb = dbmanager.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("calc_date", getDateTime());
            values.put("calculation", cal_history);
            System.out.println(values.get("calc_date"));
            System.out.println(values.get("calculation"));
            long newRowId = sqlitedb.insert("calculate", null, values);
            sqlitedb.close();
            dbmanager.close();

            if(newRowId != -1) {
                //Toast.makeText(this, "히스토리 등록 성공", Toast.LENGTH_LONG).show();
                System.out.println("히스토리 등록 성공!");
            } else {
                //Toast.makeText(this, "히스토리 등록 에러!", Toast.LENGTH_LONG).show();
                System.out.println("히스토리 등록 에러!");
            }
        } catch (SQLiteException e) {
            //Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            System.out.println(e.getMessage());
        }
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    public void onClick(View arg0) {
        // 출력용
        Editable str = display.getText();
        String str_b = "";
        String result_b = "";
        String re_history = "";

        if(count != 0) {
            history.setText(display.getText() + "=" + result.getText());
            //re_history = history.getText().toString();
            //if(!re_history.equals("")) {
            //    insertHistory(re_history);
            //}
            //op3 = 0;
            //op4 = 0;
            str_b = str_b + str.toString();
            System.out.println("str_b : " + str_b);
            result_b = result_b + result.getText().toString();
            System.out.println("result_b : " + result_b);
            str.clear();
            display.setText("");
            result.setText("");
            count = 0;
        }

        switch (arg0.getId())
        {
            case R.id.one :
                if(op3 != 0) {
                    System.out.println("R.id.one op3 : " + op3);
                    op3 = 0;
                    // display.setText("");
                }
                System.out.println("R.id.one op3 : " + op3);
                str = str.append(one.getText());
                display.setText(str);
                break;
            case R.id.two :
                if(op3 != 0) {
                    op3 = 0;
                    //display.setText("");
                }
                str = str.append(two.getText());
                display.setText(str);
                break;
            case R.id.three :
                if(op3 != 0) {
                    op3 = 0;
                    //display.setText("");
                }
                str = str.append(three.getText());
                display.setText(str);
                break;
            case R.id.four :
                if(op3 != 0) {
                    op3 = 0;
                    //display.setText("");
                }
                str = str.append(four.getText());
                display.setText(str);
                break;
            case R.id.five :
                if(op3 != 0) {
                    op3 = 0;
                    //display.setText("");
                }
                str = str.append(five.getText());
                display.setText(str);
                break;
            case R.id.six :
                if(op3 != 0) {
                    op3 = 0;
                    //display.setText("");
                }
                str = str.append(six.getText());
                display.setText(str);
                break;
            case R.id.seven :
                if(op3 != 0) {
                    op3 = 0;
                    //display.setText("");
                }
                str = str.append(seven.getText());
                display.setText(str);
                break;
            case R.id.eight :
                if(op3 != 0) {
                    op3 = 0;
                    //display.setText("");
                }
                str = str.append(eight.getText());
                display.setText(str);
                break;
            case R.id.nine :
                if(op3 != 0) {
                    op3 = 0;
                    //display.setText("");
                }
                str = str.append(nine.getText());
                display.setText(str);
                break;
            case R.id.zero :
                if(op3 != 0) {
                    op3 = 0;
                    //display.setText("");
                }
                str = str.append(zero.getText());
                display.setText(str);
                break;
            case R.id.d_zero :
                if(op3 != 0) {
                    op3 = 0;
                    //display.setText("");
                }
                str = str.append(d_zero.getText());
                display.setText(str);
                break;
            case R.id.clear :
                op3 = 0;
                op4 = 0;
                count = 0;
                str.clear();
                display.setText("");
                result.setText("");
                break;
            case R.id.add :
                optr = "+";
                if(op3 == 0) {
                    op3 = Double.parseDouble(display.getText().toString());
                    System.out.println("R.id.add op3 : " + op3);
                    str = str.append(optr);
                    display.setText(str);
                    //display.setText("");
                }
                else if(op4 != 0) {
                    op4 = 0;
                    //display.setText("");
                    str.clear();
                    str.append(result_b + "+");
                    display.setText(str);
                }
                if(str.toString().charAt(str.toString().length() -1) == '+') break;
                else {
                    System.out.println("3");
                    op3 = Double.parseDouble(display.getText().toString());
                    str.clear();
                    str = str.append(Double.toString(op3));
                    System.out.println(op3 + " " + str);
                    display.setText(str + "+");
                    //op3 = op3 - op4;
                    //display.setText(Double.toString(op3));
                }
                /*
                else if(op3 != 0){
                    //String pre_result = result.getText().toString();
                    //op4 = Double.parseDouble(display.getText().toString());
                    //str = str.append(Double.toString(op4));
                    System.out.println("3");
                    str.clear();
                    str.append(result_b + "+");
                    display.setText(str);
                    //op3 = op3 + op4;
                    //result.setText(Double.toString(op3));
                }
                */
                break;
            case R.id.sub :
                optr = "-";
                //char sub = str.toString().charAt(str.toString().length() -1);
                if(op3 == 0) {
                    op3 = Double.parseDouble(display.getText().toString());
                    str = str.append(optr);
                    display.setText(str);
                    //display.setText("");
                }
                else if(op4 != 0) {
                    op4 = 0;
                    //display.setText("");
                    str.clear();
                    str.append(result_b + "-");
                    display.setText(str);
                }
                if(str.toString().charAt(str.toString().length() -1) == '-') break;
                else {
                    System.out.println("3");
                    op3 = Double.parseDouble(display.getText().toString());
                    str.clear();
                    str = str.append(Double.toString(op3));
                    System.out.println(op3 + " " + str);
                    display.setText(str + "-");
                    //op3 = op3 - op4;
                    //display.setText(Double.toString(op3));
                }

                break;
            case R.id.mul :
                optr = "x";
                if(op3 == 0) {
                    op3 = Double.parseDouble(display.getText().toString());
                    str = str.append(optr);
                    display.setText(str);
                    //display.setText("");
                }
                else if(op4 != 0) {
                    op4 = 0;
                    //display.setText("");
                    str.clear();
                    str.append(result_b + "x");
                    display.setText(str);
                }
                if(str.toString().charAt(str.toString().length() -1) == 'x') break;
                else {
                    System.out.println("3");
                    op3 = Double.parseDouble(display.getText().toString());
                    str.clear();
                    str = str.append(Double.toString(op3));
                    System.out.println(op3 + " " + str);
                    display.setText(str + "x");
                    //op3 = op3 - op4;
                    //display.setText(Double.toString(op3));
                }
                /*
                else {
                    op4 = Double.parseDouble(display.getText().toString());
                    str = str.append(Double.toString(op4));
                    display.setText(str);
                    op3 = op3 * op4;
                    display.setText(Double.toString(op3));
                }
                */
                break;
            case R.id.div :
                optr = "÷";
                if(op3 == 0) {
                    op3 = Double.parseDouble(display.getText().toString());

                    str = str.append(optr);
                    display.setText(str);
                    //display.setText("");
                }
                else if(op4 != 0) {
                    op4 = 0;
                    //display.setText("");
                    str.clear();
                    str.append(result_b + "÷");
                    display.setText(str);
                }
                if(str.toString().charAt(str.toString().length() -1) == '÷') break;
                else {
                    System.out.println("3");
                    op3 = Double.parseDouble(display.getText().toString());
                    str.clear();
                    str = str.append(Double.toString(op3));
                    System.out.println(op3 + " " + str);
                    display.setText(str + "÷");
                    //op3 = op3 - op4;
                    //display.setText(Double.toString(op3));
                }
                /*
                else {
                    op4 = Double.parseDouble(display.getText().toString());
                    str = str.append(Double.toString(op4));
                    display.setText(str);
                    op3 = op3 / op4;
                    display.setText(Double.toString(op3));
                }
                */
                break;
            case R.id.percent :
                optr = "%";
                if(op3 == 0) {
                    //op3 = Double.parseDouble(display.getText().toString());
                    str = str.append(optr);
                    display.setText(str);
                    //display.setText("");
                }
                else if(op4 != 0) {
                    op4 = 0;
                    //display.setText("");
                    str.clear();
                    str.append(result_b + "%");
                    display.setText(str);
                }
                if(str.toString().charAt(str.toString().length() -1) == '%') break;
                else {
                    System.out.println("3");
                    op3 = Double.parseDouble(display.getText().toString());
                    str.clear();
                    str = str.append(Double.toString(op3));
                    System.out.println(op3 + " " + str);
                    display.setText(str + "%");
                    //op3 = op3 - op4;
                    //display.setText(Double.toString(op3));
                }
                /*
                else {
                    op4 = Double.parseDouble(display.getText().toString());
                    str = str.append(Double.toString(op4));
                    display.setText(str);
                    op3 = op3 * op4;
                    display.setText(Double.toString(op3));
                }
                */
                break;
            case R.id.equal:
                if (!optr.equals("")) {
                    if (op4 != 0) {
                        if (optr.equals("+")) {
                            //display.setText("");
                            //display.setText(Double.toString(op3));
                            operation();
                        }
                        else if(optr.equals("-")) {
                            //display.setText("");
                            //display.setText(Double.toString(op3));
                            operation();
                        }
                        else if(optr.equals("x")) {
                            //display.setText("");
                            //display.setText(Double.toString(op3));
                            operation();
                        }
                        else if(optr.equals("÷")) {
                            //display.setText("");
                            //display.setText(Double.toString(op3));
                            operation();
                        }
                        else if(optr.equals("%")) {
                            //display.setText("");
                            //display.setText(Double.toString(op3));
                            operation();
                        }
                    } else {
                        operation();
                    }
                }
                break;
            case R.id.dot:
                if (op4 != 0) {
                    op4 = 0;
                    //display.setText("");
                }
                str = str.append(dot.getText());
                display.setText(str);
                break;
            case R.id.del:
                if (op4 != 0) {
                    op4 = 0;
                    //display.setText("");
                }
                // Get edit text characters
                String textInBox = "";
                if(str_b.equals("")) textInBox = display.getText().toString();
                if(!str_b.equals("")) textInBox = str_b.toString();
                System.out.println("textInBox : " + textInBox);
                if (textInBox.length() > 0) {
                    //Remove last character//
                    String newText = textInBox.substring(0, textInBox.length() - 1);
                    // Update edit text
                    display.setText(newText);
                    str_b = "";
                }
                break;

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
        if(id == R.id.action_settings2) {
            Intent it = new Intent(this, CalcHistory.class);
            startActivity(it);
            finish();
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }
}
