package com.trivago.cs.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.springframework.web.multipart.MultipartFile;

import com.trivago.cs.pojo.HotelDetail;

public interface CsvService {

	List<HotelDetail> convertCsvToObject(String newFile, String fullFileName) throws FileNotFoundException;

	String convertObjectToJson(List<HotelDetail> dataList) throws IOException;

	void convertCsvToXml(String newFile, List<HotelDetail> dataList) throws IOException, JAXBException;

	void dumpdataIntoJsonFile(String newFile, String convertObjectToJson);

	boolean saveUploadCsvFiles(String uPLOADED_FOLDER, List<MultipartFile> asList) throws IOException;

	String getUploadedFileFromTextFile() throws IOException;

}
