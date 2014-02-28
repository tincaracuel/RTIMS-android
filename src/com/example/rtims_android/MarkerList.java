package com.example.rtims_android;

import java.util.ArrayList;
import java.util.List;

public class MarkerList {

	private static MarkerList instance;
	
	public static MarkerList getInstance() {
		if(instance == null) {
			instance = new MarkerList();
		}
		return instance;
	}
	
	private List<RTIMSMarker> markerList;
	
	private MarkerList() {
		markerList = new ArrayList<RTIMSMarker>();
	}
	
	public void add(RTIMSMarker marker) {
		markerList.add(marker);
	} 
	
	public List<RTIMSMarker> getList() {
		return markerList;
	}
	
	public List<RTIMSMarker> getListInCategory(String category) {
		List<RTIMSMarker> temp = new ArrayList<RTIMSMarker>();
		
		for(int i = 0; i < markerList.size(); i++) {
			if(markerList.get(i).getCategory().equalsIgnoreCase(category))
				temp.add(markerList.get(i));
		}
		
		return temp;
	}
}
