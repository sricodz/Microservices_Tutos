package com.hotel.service;

import java.util.List;

import com.hotel.entity.Hotel;

public interface HotelService {

	 Hotel create(Hotel hotel);

	 List<Hotel> getAll();

	    
	 Hotel get(String id);

}
