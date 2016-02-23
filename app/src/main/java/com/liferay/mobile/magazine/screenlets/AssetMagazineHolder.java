package com.liferay.mobile.magazine.screenlets;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.liferay.mobile.magazine.R;
import com.liferay.mobile.magazine.utils.PicassoUtil;
import com.liferay.mobile.screens.assetlist.AssetEntry;
import com.liferay.mobile.screens.base.list.BaseListAdapter;
import com.liferay.mobile.screens.base.list.BaseListAdapterListener;
import com.liferay.mobile.screens.ddl.model.Field;

public class AssetMagazineHolder extends BaseListAdapter.ViewHolder implements View.OnClickListener {

	public AssetMagazineHolder(View view, BaseListAdapterListener listener) {
		super(view, listener);

		imageView = (ImageView) view.findViewById(R.id.magazine_picture);
		magazineTitle = (TextView) view.findViewById(R.id.magazine_title);
		magazinePrize = (TextView) view.findViewById(R.id.magazine_prize);

		_listener = listener;
		view.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		_listener.onItemClick(getLayoutPosition(), v);
	}

	public void bind(AssetEntry assetEntry) {
		magazineTitle.setText((String) assetEntry.getFieldByName("magazineTitle").getCurrentValue());
		magazinePrize.setText((String) assetEntry.getFieldByName("price").getCurrentValue());

		Field magazineThumbnail = assetEntry.getFieldByName("magazineThumbnail");
		if (magazineThumbnail != null) {
			String url = (String) magazineThumbnail.getCurrentValue();

			PicassoUtil.getImageWithCache(url).into(imageView);
		}

	}

	private ImageView imageView;
	private TextView magazineTitle;
	private TextView magazinePrize;
	private BaseListAdapterListener _listener;

}