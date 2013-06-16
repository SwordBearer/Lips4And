package xmu.swordbearer.lips.activity.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import xmu.swordbearer.lips.R;
import xmu.swordbearer.lips.activity.line.AddLineActivity;

public class PageStart extends Fragment implements View.OnClickListener {
    private View addView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_page_start, container, false);
        initViews(rootView);
        return rootView;
    }

    public void initViews(View rootView) {
        addView = rootView.findViewById(R.id.frag_start_create);
        addView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == addView) {
            startActivity(new Intent(this.getActivity(), AddLineActivity.class));
        }
    }
}
