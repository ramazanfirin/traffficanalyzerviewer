package trafficanalyzerviewer.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import trafficanalyzerviewer.domain.VideoRecordQueryVM;

public class Util {

	public static List<Long> getCameraData() {
		List<Long> result = new ArrayList<Long>();
		
		
		try {
			URL url = new URL("http://localhost:8080/api/video-records/getAllData/1");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			int status = con.getResponseCode();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
					
			System.out.println(content.toString());
			ObjectMapper mapper = new ObjectMapper();
	        mapper.findAndRegisterModules();
	  	    List<VideoRecordQueryVM> asList = mapper.readValue(content.toString(), new TypeReference<List<VideoRecordQueryVM>>() { });

	  	    for (VideoRecordQueryVM videoRecordQueryVM : asList) {
	  	    	result.add(videoRecordQueryVM.getDuration());
			}
	  	    
			System.out.println("bitti");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return result;
	}
	
	public static List<Long>  getRandomDataForCamera() {
		Random rand = new Random();
		List<Long> result = new ArrayList<Long>();
		for (int i = 0; i < 10; i++) {
			int rand_int1 = rand.nextInt(10000);
			result.add(new Long(rand_int1+1000));
		}
		
		Collections.sort(result);
		return result;
	}
	
	
	
}
