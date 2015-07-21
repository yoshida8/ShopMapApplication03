package jp.android.myapp.shopmapapplication03;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class RegisterActivity extends Activity {

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        //登録ボタンが押されたとき
        Button register_button = (Button)findViewById(R.id.Register_Button);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register_intent = new Intent(RegisterActivity.this, ShopListActivity.class);

                EditText name = (EditText)findViewById(R.id.NameEdit);
                EditText add = (EditText)findViewById(R.id.AddEdit);
                EditText kind = (EditText)findViewById(R.id.KindEdit);
                EditText menu = (EditText)findViewById(R.id.MenuEdit);
                EditText contents = (EditText)findViewById(R.id.ContentsEdit);

                final String[] regData = new String[5];

                regData[0] = name.getText().toString();
                regData[1] = add.getText().toString();
                regData[2] = kind.getText().toString();
                regData[3] = menu.getText().toString();
                regData[4] = contents.getText().toString();

                final MyDbHelper myDbHelper = new MyDbHelper(RegisterActivity.this);
                db = myDbHelper.getWritableDatabase();

                try{

                    insertToDB(regData);

                }catch (Exception e){

                    e.printStackTrace();

                }

                startActivity(register_intent);

            }
        });

        //キャンセルボタンが押されたとき
        Button cancel_button = (Button)findViewById(R.id.Cancel_Button);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent cancel_intent = new Intent(RegisterActivity.this,ShopListActivity.class);
                startActivity(cancel_intent);

            }
        });


    }
    //DBへデータ挿入
    private void insertToDB(String[] regData) throws Exception {

        db.execSQL("insert into myData("
                + MyDbHelper.SHOP_NAME + "," + MyDbHelper.SHOP_ADD + ","
                + MyDbHelper.SHOP_KIND + "," + MyDbHelper.SHOP_MENU + ","
                + MyDbHelper.SHOP_CONTENTS + ")values('"
                + regData[0] + "','" + regData[1] + "','" + regData[2] + "','" + regData[3] + "','" + regData[4] + "')");

    }

}
