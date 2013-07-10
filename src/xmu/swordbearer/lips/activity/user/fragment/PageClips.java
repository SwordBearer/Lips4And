package xmu.swordbearer.lips.activity.user.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import xmu.swordbearer.lips.R;
import xmu.swordbearer.lips.api.UserAPI;

/** Created by SwordBearer on 13-6-22. */
public class PageClips extends Fragment implements View.OnClickListener {
    private Button btnAddClips;
    private ListView lvClips;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_page_clips, container, false);
        return rootView;
    }

    private void initViews(View rootView) {
        btnAddClips = (Button) rootView.findViewById(R.id.page_clips_add);
        lvClips = (ListView) rootView.findViewById(R.id.page_clips_lv);
        //如果是当前用户,jiu，就显示添加按钮
        if (UserAPI.isLogin(getActivity())) {
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnAddClips) {
        } else if (v == lvClips) {
        }
    }
}
