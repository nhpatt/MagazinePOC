package com.liferay.mobile.magazine.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import com.liferay.mobile.magazine.R;
import com.liferay.mobile.magazine.utils.PicassoUtil;
import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.ddl.model.Field;
import com.liferay.mobile.screens.webcontent.WebContent;
import java.util.List;

public class MagazineActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

	private List<Field> _chapters;
	private AssetEntry _assetEntry;
	private ViewPager _viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_magazine);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		_assetEntry = getIntent().getParcelableExtra("assetEntry");

		WebContent magazine = (WebContent) _assetEntry;

		_chapters = magazine.getDDMStructure().getFields();

		SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
		_viewPager = (ViewPager) findViewById(R.id.container);
		_viewPager.setAdapter(sectionsPagerAdapter);
		_viewPager.setOffscreenPageLimit(2);

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
			R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		//FIXME
		//ListView listView = (ListView) findViewById(R.id.magazine_menu);
		//Object[][] titles = magazine.getDDMStructure().
		//MenuAdapter menuAdapter = new MenuAdapter(this, R.layout.lateral_menu, titles);
		//listView.setAdapter(menuAdapter);
		//listView.setOnItemClickListener(this);
	}

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		//FIXME
		//_viewPager.setCurrentItem(AssetUtil.getTitlePosition(_assetEntry, position));

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
	}

	public static class PlaceholderFragment extends Fragment {

		private static final String FIELD_VIEW_PAGER = "FIELD_VIEW_PAGER";

		public PlaceholderFragment() {
		}

		public static PlaceholderFragment newInstance(Field field) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putSerializable(FIELD_VIEW_PAGER, field.getCurrentValue());
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.magazine_item, container, false);

			String url = getArguments().getString(FIELD_VIEW_PAGER);

			ImageView imageView = (ImageView) rootView.findViewById(R.id.magazine_image);
			PicassoUtil.getImageWithoutCache(url).into(imageView);

			//			if (url != null && !url.contains("storage")) {
			//				url = LiferayServerContext.getServer() + url;
			//			}
			//			WebView webView = (WebView) rootView.findViewById(R.id.magazine_webview);
			//			webView.getSettings().setSupportZoom(true);
			//			webView.getSettings().setBuiltInZoomControls(true);
			//
			//			String data = "<body><div style=\"height: 100%;display: flex;align-items: center;justify-content: center\"> <img " +
			//				"src=\"" + url + "\" width=\"100%\" /></div></body>";
			//
			//			webView.loadDataWithBaseURL("file:///android_asset/", data, "text/html", "utf-8", null);

			return rootView;
		}
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return PlaceholderFragment.newInstance(_chapters.get(position));
		}

		@Override
		public int getCount() {
			return _chapters.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return _chapters.get(position).getLabel();
		}
	}
}
