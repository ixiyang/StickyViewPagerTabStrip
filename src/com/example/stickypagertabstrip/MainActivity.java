package com.example.stickypagertabstrip;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

import com.example.stickypagertabstrip.PagerFragment.OnScrollChanged;
public class MainActivity extends FragmentActivity implements OnScrollChanged {

	
	private ViewPager mPager;
	private ViewPagerAdapter mPagerAdapter;
	private PagerSlidingTabStrip mTabStrip;
	private View mTopView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		mPager = (ViewPager) findViewById(R.id.pager);
		mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
		mTopView=findViewById(R.id.topview);
		mPager.setAdapter(mPagerAdapter);
		mPager.setOffscreenPageLimit(mPagerAdapter.getCount());
		mTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		mTabStrip.setViewPager(mPager);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	private class ViewPagerAdapter extends FragmentPagerAdapter{

		
		final int PAGE_COUNT = 2;
		// Tab Titles
		private String tabtitles[] = new String[] { "fragment1", "fragment2" };
		private Fragment[] fragments=new Fragment[PAGE_COUNT];
		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
			for(int i=0;i<PAGE_COUNT;i++){
				Bundle bundle=new Bundle();
				bundle.putInt("params", i+1);
				fragments[i]=PagerFragment.newInstance(bundle);
			}
		}
		
		@Override
		public int getCount() {
			return PAGE_COUNT;
		}
		
		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return fragments[0];
			case 1:
				return fragments[1];
			default:
				break;
			}
			return null;
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			return tabtitles[position];
		}
		
	}


	@Override
	public void onScrollChange(ListView listView, View placeHolder) {
		View v = listView.getChildAt(0);
		int top = (v == null) ? 0 : v.getTop();
		Log.e("first item top====>", top + "");
		Log.e("placeholder top====>", placeHolder.getTop() + "");
		if (placeHolder.getTop() <= 0) {
			return;
		}
		Log.e("first item positon", "  "+listView.getFirstVisiblePosition());
		if (listView.getFirstVisiblePosition() == 0) {
				mTabStrip.setTranslationY(Math.max(0, placeHolder.getTop() + top));
				mTopView.setTranslationY(top);
		}/*else if(listView.getFirstVisiblePosition()>2){
			//move the stick bar to top
			mTabStrip.setTranslationY(0);
			mTopView.setTranslationY(-mTopView.getBottom());
		}*/
		
	}
	
	
}
