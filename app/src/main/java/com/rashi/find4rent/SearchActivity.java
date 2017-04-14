package com.rashi.find4rent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    DBHelper housedb;
    List<House> houses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        housedb = DBHelper.getInstance(this);
        housedb.insertDummy();

        houses = housedb.getAllHouses();
        final RVAdapter adapter = new RVAdapter(houses);
        rv.setAdapter(adapter);

        final TextView search_txtview = (TextView)findViewById(R.id.searchtext);
        Button search_btn = (Button)findViewById(R.id.searchbutton);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String search_text = search_txtview.getText().toString();

                if (!search_text.equals(""))
                {
                    adapter.updateHouseList( housedb.searchHouses(search_text) );
                    Log.d("main", String.valueOf(houses.size()));
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    adapter.updateHouseList( housedb.getAllHouses());
                    Log.d("main", String.valueOf(houses.size()));
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }
}
