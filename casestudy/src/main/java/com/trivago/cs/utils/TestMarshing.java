package com.trivago.cs.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;


import com.trivago.cs.pojo.HotelDetail;
import com.trivago.cs.pojo.Hotel;

public class TestMarshing {
//	static Hotel employees = new Hotel();
//	static 
//	{
//		employees.setHotelDetail(new ArrayList<HotelDetail>());
//		
//		HotelDetail emp = new HotelDetail();
//		emp.setName("Lokesh");
//		emp.setAddress("Gupta");
//		emp.setStars(0);
//		emp.setContact("hani");
//		emp.setPhone("2323232-323");
//		emp.setUri("urli");
//		
//		employees.getHotelDetail().add(emp);
//		employees.getHotelDetail().add(emp);
//		employees.getHotelDetail().add(emp);
//	}
	
	private static void marshalingExample() throws JAXBException {
		Hotel employees = new Hotel();
		List<HotelDetail> hd = new ArrayList<HotelDetail>();
		employees.setHotelDetail(new ArrayList<HotelDetail>());
		
		HotelDetail emp = new HotelDetail();
		emp.setName("Lokesh");
		emp.setAddress("Gupta");
		emp.setStars(0);
		emp.setContact("hani");
		emp.setPhone("2323232-323");
		emp.setUri("urli");
		
		hd.add(emp);
		hd.add(emp);
		hd.add(emp);
		
		employees.getHotelDetail().add(hd.get(0));
		// employees.getHotelDetail().add(emp);
		// employees.getHotelDetail().add(emp);
		
		
		JAXBContext jaxbContext = JAXBContext.newInstance(Hotel.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
 
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
 
		jaxbMarshaller.marshal(employees, System.out);
		jaxbMarshaller.marshal(employees, new File("c:/temp/employees.xml"));
	}
	
	public static void main(String[] args) throws JAXBException {
		marshalingExample();
	}
}
