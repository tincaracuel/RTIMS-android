package com.example.rtims_android;

import com.google.android.gms.maps.model.Marker;

// MARKER MODEL
public class RTIMSMarker {

	private Marker mMarker;
	private String mCategory;
	private String mType;
	
	public RTIMSMarker(Marker marker, String category, String type){
		mMarker = marker;
		mCategory = category;
		mType = type;
	}
	
	public Marker getMarker() {
		return mMarker;
	}
	public void setMarker(Marker marker) {
		mMarker = marker;
	}
	public String getCategory() {
		return mCategory;
	}
	public void setCategory(String category) {
		this.mCategory = category;
	}
	public String getType() {
		return mType;
	}
	public void setType(String type) {
		mType = type;
	}
	
	
	
}
