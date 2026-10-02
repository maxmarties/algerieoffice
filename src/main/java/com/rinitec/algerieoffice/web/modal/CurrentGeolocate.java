package com.rinitec.algerieoffice.web.modal;

import java.io.Serializable;

import org.springframework.util.StringUtils;

public class CurrentGeolocate implements Serializable {
	private static final long serialVersionUID = 3889091534866315412L;
	
	private String latitude = "12";
	private String longitude = "12";
	
	private void init() {
		this.latitude = "12";
		this.longitude = "12";
	}
	
	public CurrentGeolocate() {
		this.init();
	}
	
	public CurrentGeolocate(final String builder) {
		if(!StringUtils.isEmpty(builder)) {
			try {
				final String[] geo = builder.split("%");
				this.latitude = geo[0];
				this.longitude = geo[1];
			} catch (Exception e) {
				this.init();
				System.out.println("Catched in current geolocate");
			} 
		} else this.init();
	}
	
	public CurrentGeolocate(final String latitude, final String longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	@Override
	public String toString() {
		try {
			final StringBuilder builder = new StringBuilder();
			builder.append(latitude);
			builder.append("%");
			builder.append(longitude);
			return builder.toString();
		} catch (Exception e) {return "";}
	}

}
