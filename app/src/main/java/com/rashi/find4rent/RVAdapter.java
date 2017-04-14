package com.rashi.find4rent;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by nithin on 11/4/17.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView houseName;
        TextView houseRent;
        ImageView housePhoto;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            houseName = (TextView)itemView.findViewById(R.id.house_name);
            houseRent = (TextView)itemView.findViewById(R.id.house_rent);
            housePhoto = (ImageView)itemView.findViewById(R.id.house_photo);
        }

    }
    List<House> houses;

    RVAdapter(List<House> houses){
        this.houses = houses;
    }

    @Override
    public int getItemCount() {
        return houses.size();
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_card_item, viewGroup, false);
//        v.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));

        WindowManager windowManager = (WindowManager)(viewGroup.getContext()).getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();
        v.setLayoutParams(new RecyclerView.LayoutParams(width, RecyclerView.LayoutParams.WRAP_CONTENT));

        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.houseName.setText(houses.get(i).name);
        personViewHolder.houseRent.setText(houses.get(i).rent.toString());
        personViewHolder.housePhoto.setImageResource(houses.get(i).photoId);
    }

    public void updateHouseList(List<House> newlist) {
        houses.clear();
        houses.addAll(newlist);
        this.notifyDataSetChanged();
    }
}
