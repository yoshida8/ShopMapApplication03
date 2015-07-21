package jp.android.myapp.shopmapapplication03;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class DeleteActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        Intent deleteIntent = getIntent();
        String id = deleteIntent.getStringExtra("_id");

        MyDbHelper dbHelper = new MyDbHelper(DeleteActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //DBからデータを削除
        dbHelper.deleteToDB(db, id);

        Intent delete = new Intent(DeleteActivity.this, ShopListActivity.class);
        startActivity(delete);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_delete, menu);
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
}
