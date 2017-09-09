package com.trivago.cs.services;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.filechooser.FileSystemView;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.trivago.cs.pojo.HotelDetail;
import com.trivago.cs.pojo.Hotel;
import com.trivago.cs.utils.Util;

@Service
public class CsvServicImpl implements CsvService {
	
	private static final String FILENAME = FileSystemView.getFileSystemView().getHomeDirectory().toString()+"/casestudy/uploadedfile.txt";
	
	
	@Override
	public List<HotelDetail> convertCsvToObject(String newFile, String fullFileName) throws FileNotFoundException {
		String csvFile = fullFileName;
        int lineNumber = 0;
        List<HotelDetail> dataList = new ArrayList<HotelDetail>();
        CSVReader reader1 = new CSVReader(new FileReader(csvFile)); 
        String [] curnextLine;
        
        try {
			while ((curnextLine = reader1.readNext()) != null) {
				
				if (lineNumber !=0 && curnextLine.length > 1) {
					
					HotelDetail ucsv = new HotelDetail();

					if (curnextLine[0] != null && Util.isValidUTF8(curnextLine[0])) {
						ucsv.setName(curnextLine[0]);
					} else {
						ucsv.setName("");
					}
					if (curnextLine[1] != null) {
						ucsv.setAddress(curnextLine[1]);
					}
					if (curnextLine[2] != null && Integer.parseInt(curnextLine[2]) >= 0 && Integer.parseInt(curnextLine[2]) <= 5) {
						ucsv.setStars(Integer.parseInt(curnextLine[2]));
					} else {
						ucsv.setStars(0);
					}
					if (curnextLine[3] != null) {
						ucsv.setContact(curnextLine[3]);
					}
					if (curnextLine[4] != null) {
						ucsv.setPhone(curnextLine[4]);
					}
					
					if (curnextLine[5] != null && Util.isValidUrl(curnextLine[5])) {
						ucsv.setUri(curnextLine[5]);
					}
					
					
					
					
					
					
					dataList.add(ucsv);
				}
				lineNumber++; 
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataList;
	}

	@Override
	public String convertObjectToJson(List<HotelDetail> dataList) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(dataList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jsonString;
	}

	@Override
	public void convertCsvToXml(String newFile, List<HotelDetail> dataList) throws IOException, JAXBException{
		
		File storeFile = new File(newFile);
		if (!storeFile.exists()) {
			storeFile.createNewFile();
		}
		Hotel hotel = new Hotel();
		hotel.setHotelDetail(new ArrayList<HotelDetail>());
		
		for (HotelDetail hd : dataList) {
			hotel.getHotelDetail().add(hd);
		}		
		
		JAXBContext jaxbContext = JAXBContext.newInstance(Hotel.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
 
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
 
		// jaxbMarshaller.marshal(hotel, System.out);
		jaxbMarshaller.marshal(hotel, storeFile);
		
	}

	@Override
	public void dumpdataIntoJsonFile(String newFile, String convertObjectToJson) {
		BufferedWriter bw = null;
		FileWriter fw = null;
		
		try {

			File storeFile = new File(newFile);

			// if file doesnt exists, then create it
			if (!storeFile.exists()) {
				storeFile.createNewFile();
			}

			// true = append file
			fw = new FileWriter(storeFile.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);

			bw.write(convertObjectToJson);

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}
		}
		
	}

	@Override
	public boolean saveUploadCsvFiles(String uPLOADED_FOLDER, List<MultipartFile> asList) throws IOException {
		
		for (MultipartFile file : asList) {

            if (file.isEmpty()) {
                continue; //next file entry
            }

            byte[] bytes = file.getBytes();
            Path path = Paths.get(uPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
            
            saveFileNameInTextFile(file);
        }
		return false;
	}

	private void saveFileNameInTextFile(MultipartFile file) {
		BufferedWriter bw = null;
		FileWriter fw = null;
		String s = System.lineSeparator();
		try {

			String data = file.getOriginalFilename();

			File storeFile = new File(FILENAME);

			// if file doesn't exists, then create it
			if (!storeFile.exists()) {
				storeFile.createNewFile();
			}

			// true = append file
			fw = new FileWriter(storeFile.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);

			bw.write(data);
			bw.write(s);

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}
		}
		
	}

	@Override
	public String getUploadedFileFromTextFile() throws IOException {
		String jsonString = null;
		List<Object> fileInfo = new ArrayList<Object>();
		ObjectMapper mapper = new ObjectMapper();
		BufferedReader br = null;
		File f = new File(FILENAME);
		
		try {
			if (f.exists()) {
				br = new BufferedReader(new FileReader(FILENAME));
				String line = br.readLine();

				while (line != null) {

					if (!fileInfo.contains(line)) {
						fileInfo.add(line);
					}
					line = br.readLine();
				}

				jsonString = mapper.writeValueAsString(fileInfo);
				;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				br.close();
			}
		}
			
		return jsonString;
	}
	

}
