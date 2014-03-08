package com.example.rtims_android;

import com.google.android.gms.maps.model.Marker;

// MARKER MODEL
public class RTIMSMarker {

	private Marker mMarker;
	private String mCategory;
	private String mType;
	private String mId;
	private String mName;
	private String mDesc;
	private String mStart;
	private String mEnd;
	private String mStatus;
	private String mStreet;
	private String mBarangay;
	private String mLat;
	private String mLong;
	
	

	public RTIMSMarker(Marker marker, String category, String type, String id,
						String desc, String start, String end, String street,
						String barangay, String latitude, String longitude){
		mMarker = marker;
		mCategory = category;
		mType = type;
		mId = id;
		mDesc = desc;
		mStart = start;
		mEnd = end;
		mStreet = street;
		mBarangay = barangay;
		mLat = latitude;
		mLong = longitude;
	}
	
	public RTIMSMarker(Marker marker, String category, String type, String id,
			String name, String desc, String start, String end, String status,
			String street, String barangay, String latitude, String longitude){
	mMarker = marker;
	mCategory = category;
	mType = type;
	mId = id;
	mName = name;
	mDesc = desc;
	mStart = start;
	mEnd = end;
	mStatus = status;
	mStreet = street;
	mBarangay = barangay;
	mLat = latitude;
	mLong = longitude;
	
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

	public String getId() {
		return mId;
	}

	public void setId(String id) {
		mId = id;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}


	public String getDesc() {
		return mDesc;
	}

	public void setDesc(String desc) {
		mDesc = desc;
	}

	public String getStart() {
		return mStart;
	}

	public void setStart(String start) {
		mStart = start;
	}

	public String getEnd() {
		return mEnd;
	}

	public void setEnd(String end) {
		mEnd = end;
	}

	public String getStatus() {
		return mStatus;
	}

	public void setStatus(String status) {
		mStatus = status;
	}
	
	public String getStreet() {
		return mStreet;
	}

	public void setStreet(String street) {
		mStreet = street;
	}

	public String getBarangay() {
		return mBarangay;
	}

	public void setBarangay(String barangay) {
		mBarangay = barangay;
	}

	public String getLat() {
		return mLat;
	}

	public void setLat(String lat) {
		mLat = lat;
	}

	public String getLong() {
		return mLong;
	}

	public void setLong(String l) {
		mLong = l;
	}
	
	
	
	
}
