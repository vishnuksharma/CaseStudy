package com.trivago.cs.pojo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "hotel")
@XmlAccessorType(XmlAccessType.FIELD)
public class Hotel {
	@XmlElement(name = "hotelDetail")
    private List<HotelDetail> hotelDetail = null;

	public List<HotelDetail> getHotelDetail() {
		return hotelDetail;
	}

	public void setHotelDetail(List<HotelDetail> hotelDetail) {
		this.hotelDetail = hotelDetail;
	}
 
    
}
