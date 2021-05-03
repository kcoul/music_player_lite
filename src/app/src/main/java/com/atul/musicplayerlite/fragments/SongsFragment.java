package com.atul.musicplayerlite.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atul.musicplayerlite.MainViewModel;
import com.atul.musicplayerlite.R;
import com.atul.musicplayerlite.adapter.SongsAdapter;
import com.atul.musicplayerlite.factory.MainViewModelFactory;
import com.atul.musicplayerlite.listener.SongSelectListener;
import com.atul.musicplayerlite.model.Album;
import com.atul.musicplayerlite.model.Music;
import com.atul.musicplayerlite.player.PlayerManager;

import java.util.List;

public class SongsFragment extends Fragment implements SongSelectListener {

    private MainViewModel viewModel;

    public SongsFragment() {

    }

    public static SongsFragment newInstance() {
        SongsFragment fragment = new SongsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity(), new MainViewModelFactory(requireActivity())).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_songs, container, false);

        List<Music> musicList = viewModel.getSongs(false);

        RecyclerView recyclerView = view.findViewById(R.id.songs_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration( new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new SongsAdapter(this, musicList));

//        PlayerManager playerManager = new PlayerManager(getContext(), musicList);
//        playerManager.start(); // start from first song

        return view;

    }

    @Override
    public void playAlbum(Album album) {
        new PlayerManager(getActivity(), album.music);
    }

    @Override
    public void playQueue(List<Music> musicList) {
        new PlayerManager(getActivity(), musicList);
    }
}