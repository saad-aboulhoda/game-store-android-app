package com.n1akai.gamesstore.viewmodels;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.firebase.ui.common.ChangeEventType;
import com.firebase.ui.database.ChangeEventListener;
import com.firebase.ui.database.ClassSnapshotParser;
import com.firebase.ui.database.FirebaseArray;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.n1akai.gamesstore.R;
import com.n1akai.gamesstore.adapters.GenreAdapter;
import com.n1akai.gamesstore.models.Game;
import com.n1akai.gamesstore.models.Genre;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Objects;

public class HomeFragmentViewModel extends ViewModel {
    ArrayList<SlideModel> slideImgs;
    public FirebaseArray<Game> games = new FirebaseArray<>(FirebaseDatabase.getInstance().getReference("games").orderByChild("releaseDate").limitToLast(6), new ClassSnapshotParser<>(Game.class));
    public FirebaseArray<Genre> genres = new FirebaseArray<>(FirebaseDatabase.getInstance().getReference("genres"), new ClassSnapshotParser<>(Genre.class));
    KeppAliveListener keppAliveListener;

    public HomeFragmentViewModel() {
        keppAliveListener = new KeppAliveListener();
        games.addChangeEventListener(keppAliveListener);
        genres.addChangeEventListener(keppAliveListener);
    }

    static class KeppAliveListener implements ChangeEventListener {

        @Override
        public void onChildChanged(@NonNull ChangeEventType type, @NonNull DataSnapshot snapshot, int newIndex, int oldIndex) {

        }

        @Override
        public void onDataChanged() {
            Log.d("MYTAG", "HELLO WORLD!");
        }

        @Override
        public void onError(@NonNull DatabaseError databaseError) {

        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        games.removeChangeEventListener(keppAliveListener);
        genres.removeChangeEventListener(keppAliveListener);
    }

    public void sliderImage() {
        slideImgs = new ArrayList<>();
        slideImgs.add(new SlideModel(R.drawable.new_slide_img_1, ScaleTypes.CENTER_CROP));
        slideImgs.add(new SlideModel(R.drawable.new_slide_img_2, ScaleTypes.CENTER_CROP));
        slideImgs.add(new SlideModel(R.drawable.new_slide_img_3, ScaleTypes.CENTER_CROP));
        slideImgs.add(new SlideModel(R.drawable.new_slide_img_4, ScaleTypes.CENTER_CROP));
    }


    public ArrayList<SlideModel> getSlideImgs() {
        return slideImgs;
    }
}
