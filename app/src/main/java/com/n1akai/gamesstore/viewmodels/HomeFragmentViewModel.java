package com.n1akai.gamesstore.viewmodels;

import android.os.Parcelable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.n1akai.gamesstore.R;
import com.n1akai.gamesstore.adapters.GenreAdapter;
import com.n1akai.gamesstore.models.Genre;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class HomeFragmentViewModel extends ViewModel {
    ArrayList<SlideModel> slideImgs;
    LinkedHashMap<String, Genre> genres;
    GenreAdapter adapter;
    public View v = null;

    public void sliderImage() {
        slideImgs = new ArrayList<>();
        slideImgs.add(new SlideModel(R.drawable.new_slide_img_1, ScaleTypes.CENTER_CROP));
        slideImgs.add(new SlideModel(R.drawable.new_slide_img_2, ScaleTypes.CENTER_CROP));
        slideImgs.add(new SlideModel(R.drawable.new_slide_img_3, ScaleTypes.CENTER_CROP));
        slideImgs.add(new SlideModel(R.drawable.new_slide_img_4, ScaleTypes.CENTER_CROP));
    }

    private void genres() {
        genres = new LinkedHashMap<>();
//        genresListener();
        adapter = new GenreAdapter(genres);
    }




    public ArrayList<SlideModel> getSlideImgs() {
        return slideImgs;
    }
}
