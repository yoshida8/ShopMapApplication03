package jp.android.myapp.shopmapapplication03;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class ShopListActivity extends Activity {

    private ListView myListView;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shop_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void onStart() {
        super.onStart();

        myListView = (ListView) findViewById(R.id.listView);
        final MyDbHelper myDbHelper = new MyDbHelper(ShopListActivity.this);
        db = myDbHelper.getWritableDatabase();

        try {

            //RegisterActivityで登録されたデータを検索
            final Cursor c = selectToDB();


            String[] from = {MyDbHelper.SHOP_NAME,
                            MyDbHelper.SHOP_ADD,
                            MyDbHelper.SHOP_KIND,
                            MyDbHelper.SHOP_MENU,
                            MyDbHelper.SHOP_CONTENTS};

            int[] to = {R.id.shop_name,
                        R.id.shop_add,
                        R.id.shop_kind,
                        R.id.shop_menu,
                        R.id.shop_contents};


            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.listitem, c, from, to, 0);
            myListView.setAdapter(adapter);

            //アイテムがクリックされたらアラートダイアグラムを表示
            myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    final AlertDialog.Builder alert = new AlertDialog.Builder(ShopListActivity.this);

                    final String name = ((TextView) view.findViewById(R.id.shop_name)).getText().toString();
                    final String add = ((TextView) view.findViewById(R.id.shop_add)).getText().toString();
                    final String kind = ((TextView) view.findViewById(R.id.shop_kind)).getText().toString();
                    final String menu = ((TextView) view.findViewById(R.id.shop_menu)).getText().toString();
                    final String contents = ((TextView) view.findViewById(R.id.shop_contents)).getText().toString();


                    alert.setTitle(name);
                    alert.setMessage("住所：" + add);


                    alert.setPositiveButton("MAP表示", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //MAP表示ボタンがクリックされたとき
                            Intent ListIntent = new Intent(ShopListActivity.this, MapsActivity.class);
                            ListIntent.putExtra("Name",name);
                            ListIntent.putExtra("Add",add);
                            ListIntent.putExtra("Kind",kind);
                            ListIntent.putExtra("Menu",menu);
                            ListIntent.putExtra("Contents",contents);
                            startActivity(ListIntent);

                        }
                    });

                    alert.setNegativeButton("データ削除", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            AlertDialog.Builder delete = new AlertDialog.Builder(ShopListActivity.this);
                            delete.setTitle("削除");
                            delete.setMessage("本当にこのデータを削除しますか？");
                            delete.setPositiveButton("YES",new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    //データ削除ボタンをクリックし、本当に削除するとき
                                    Intent deleteIntent = new Intent(ShopListActivity.this, DeleteActivity.class);
                                    deleteIntent.putExtra("_id", c.getString(0));
                                    startActivity(deleteIntent);

                                }
                            });

                            delete.setNegativeButton("NO",new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            delete.show();

                        }
                    });
                    alert.show();
                }
            });


        } catch (Exception e) {

            e.printStackTrace();


        } finally {

            db.close();

        }

        //登録画面へのボタンを押したとき
        Button main_button = (Button)findViewById(R.id.Main_button);
        main_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent main_intent = new Intent(ShopListActivity.this,RegisterActivity.class);
                startActivity(main_intent);

            }
        });
    }



    //DBのデータ検索
    private Cursor selectToDB() throws Exception {

        Cursor c = db.rawQuery("SELECT * FROM " + MyDbHelper.TABLE_NAME , null);
        return c;

    }

}