package com.developer.lecai.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.developer.lecai.R;
import com.developer.lecai.activity.LoginActivity;


/**
 * Created by json on 2016/3/10 0010.
 */
public class GuideFragment extends Fragment {
    private View view;
    //中间大图View
    private ImageView bigPicView;
    private int pic = R.drawable.guide_3;

    private boolean visibility = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_guide, null);

        bigPicView = (ImageView) view.findViewById(R.id.guide_iv);
        bigPicView.setOnClickListener(new ItemOnClick());
        bigPicView.setBackgroundResource(pic);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        if (viewGroup != null) {
            viewGroup.removeAllViewsInLayout();
        }
        return view;

    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    //设置引导页大图片
    public void setBigPicImage(int bigPic) {
        this.pic = bigPic;
    }


    class ItemOnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.guide_iv:
                    if (visibility)
                        goHome();
                    break;
            }
        }

        private void goHome() {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }
}
