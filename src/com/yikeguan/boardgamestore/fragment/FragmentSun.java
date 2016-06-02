package com.yikeguan.boardgamestore.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yikeguan.boardgamestore.MainActivity;
import com.yikeguan.boardgamestore.R;

public class FragmentSun extends FragmentBasic {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, container, savedInstanceState);
		((MainActivity) getActivity()).iv_title_left.setVisibility(View.GONE);
		((MainActivity) getActivity()).iv_title_right.setVisibility(View.GONE);
		((MainActivity) getActivity()).tv_title.setText("晒");

		View child = inflater.inflate(R.layout.fragment_sun, null);
		ll_fragment.addView(child);

		return v;
	}
}