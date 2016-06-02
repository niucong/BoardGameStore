package com.yikeguan.boardgamestore.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.umeng.analytics.MobclickAgent;
import com.yikeguan.boardgamestore.R;

public class FragmentBasic extends Fragment {

	// protected ImageView iv_title_left, iv_title_right;
	// protected TextView tv_title;
	protected LinearLayout ll_fragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_basic, null);
		// iv_title_left = (ImageView) v.findViewById(R.id.iv_title_left);
		// iv_title_right = (ImageView) v.findViewById(R.id.iv_title_right);
		// tv_title = (TextView) v.findViewById(R.id.tv_title);
		ll_fragment = (LinearLayout) v.findViewById(R.id.ll_fargment);
		return v;
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(getActivity());
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(getActivity());
	}
}
