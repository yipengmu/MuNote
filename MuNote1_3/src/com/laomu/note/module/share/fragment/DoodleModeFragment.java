package com.laomu.note.module.share.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.laomu.note.R;
import com.laomu.note.module.share.listener.ColorSelectorListener;
import com.laomu.note.module.share.views.DoodleTouchView;
import com.laomu.note.module.share.views.LinearColorSelectorView;

public class DoodleModeFragment extends Fragment implements ColorSelectorListener {

    private DoodleTouchView doodleTouchView;
    private LinearColorSelectorView linearColorSelectorView;

    public DoodleModeFragment() {
        // Required empty public constructor
    }

    public static DoodleModeFragment newInstance(String param1, String param2) {
        DoodleModeFragment fragment = new DoodleModeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doodle_mode, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        doodleTouchView = (DoodleTouchView) view.findViewById(R.id.doodle_touch_view);
        linearColorSelectorView = (LinearColorSelectorView) view.findViewById(R.id.color_selector_view);

        linearColorSelectorView.setColorSelectorListener(this);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onColorSelectorChange(int color) {
        doodleTouchView.setDoodlePaintColor(color);
    }
}
