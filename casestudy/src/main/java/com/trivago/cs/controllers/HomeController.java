package com.trivago.cs.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;
import javax.xml.bind.JAXBException;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.trivago.cs.pojo.ConvertFile;
import com.trivago.cs.pojo.HotelDetail;
import com.trivago.cs.services.CsvService;
import com.trivago.cs.utils.Util;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	CsvService csvService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
	private static String UPLOADED_FOLDER = FileSystemView.getFileSystemView().getHomeDirectory().toString()+"/casestudy/";

	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest req) {
		LOGGER.info("Welcome home!");
		
		 System.out.println(FileSystemView.getFileSystemView().getHomeDirectory());
		 
		return "home";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/upload/file.html", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity uploadFile(@RequestParam("file") MultipartFile uploadfile ) {
		if(uploadfile.isEmpty()) {
			return new ResponseEntity("Please select a file!", HttpStatus.OK);
		}
		
		try {
			Util.makeDirIfNotExist(UPLOADED_FOLDER);
			csvService.saveUploadCsvFiles(UPLOADED_FOLDER, Arrays.asList(uploadfile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity("Successfully uploaded @" +UPLOADED_FOLDER+
                uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
		
	}
	
	@RequestMapping( value = "/getfile/list.json", method = RequestMethod.GET)
	@ResponseBody
	public String getFileList() throws IOException{
		String jsonString = null;
		
		try {
			jsonString = csvService.getUploadedFileFromTextFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jsonString;
		
	}
	
	@RequestMapping(value = "/convert/file.json", method = RequestMethod.POST)
	@ResponseBody
	public String convertFile(@RequestParam("jsonObj") String jsonObj, HttpServletResponse response) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		ConvertFile cFile = mapper.readValue(jsonObj, ConvertFile.class);
		String fullFileName = cFile.getFileName();
		String newFile;
		
		if (fullFileName != null && !fullFileName.equals("")) {
			String fileName = fullFileName.substring(0, fullFileName.length() - 4);
			newFile = UPLOADED_FOLDER+fileName+"."+cFile.getFileType();
			
			List<HotelDetail> dataList = csvService.convertCsvToObject(newFile, UPLOADED_FOLDER+fullFileName);
			
			if (cFile.getFileType() != null && cFile.getFileType().equals("json")) {
				
				String convertObjectToJson = csvService.convertObjectToJson(dataList);
				csvService.dumpdataIntoJsonFile(newFile, convertObjectToJson);
				
			} else if (cFile.getFileType() != null && cFile.getFileType().equals("xml")) {
				
				try {
					csvService.convertCsvToXml(newFile, dataList);
				} catch (JAXBException e) {
					e.printStackTrace();
				}
			}
		} else {
			newFile = null;
		}
		
		
		return newFile;		
	}
	
}
