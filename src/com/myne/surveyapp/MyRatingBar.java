package com.myne.surveyapp;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MyRatingBar extends RelativeLayout {

	private int bitmapChecked;
	private int bitmapUnChecked;
	private byte rating;

	public MyRatingBar(Context context, int bitmapChecked, int bitmapUnChecked,
			int numSelectors, int ratingBarWidth, int ratingBarHeight) {
		super(context);
		this.bitmapChecked = bitmapChecked;
		this.bitmapUnChecked = bitmapUnChecked;
 
		int selectorWidth = ratingBarWidth / numSelectors;
		this.rating = -1;

		for (byte i = 0; i < numSelectors; i++) {
			ImageView newSelector = new ImageView(context);
			newSelector.setImageResource(bitmapUnChecked);
			this.addView(newSelector);
			newSelector.setLayoutParams(new RelativeLayout.LayoutParams(
					selectorWidth, ratingBarHeight));
			((RelativeLayout.LayoutParams) newSelector.getLayoutParams())
					.setMargins(selectorWidth * i, 0, 0, 0);
		}
	}

	public byte getRating() {
		return this.rating;
	}

	public void setRating(byte rating) {
		this.rating = rating;
		for (int currentChildIndex = 0; currentChildIndex < this
				.getChildCount(); currentChildIndex++) {
			ImageView currentChild = (ImageView) this
					.getChildAt(currentChildIndex);
			if (currentChildIndex < rating) {
				currentChild.setImageResource(this.bitmapChecked);
			} else {
				currentChild.setImageResource(this.bitmapUnChecked);
			}
		}
	}
 
}
