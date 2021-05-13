/*
 * This file is part of VLCJ.
 *
 * VLCJ is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * VLCJ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with VLCJ.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2009-2018 Caprica Software Limited.
 */

package trafficanalyzerviewer.main;

import java.awt.Point;
import java.awt.Polygon;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.SwingUtilities;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import trafficanalyzerviewer.camera.Camera;
import trafficanalyzerviewer.camera.Line;
import trafficanalyzerviewer.domain.VideoRecordQueryVM;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;

/**
 * A test player demonstrating how to achieve a transparent overlay and translucent painting.
 * <p>
 * Press SPACE to pause the video play-back.
 * <p>
 * Press F11 to toggle the overlay.
 * <p>
 * If the video looks darker with the overlay enabled, then most likely you are using a compositing
 * window manager that is doing some fancy blending of the overlay window and the main application
 * window. You have to turn off those window effects.
 * <p>
 * Note that it is not possible to use this approach if you also want to use Full-Screen Exclusive
 * Mode. If you want to use an overlay and you need full- screen, then you have to emulate
 * full-screen by changing your window bounds rather than using FSEM.
 * <p>
 * This approach <em>does</em> work in full-screen mode if you use your desktop window manager to
 * put your application into full-screen rather than using the Java FSEM.
 * <p>
 * If you want to provide an overlay that dynamically updates, e.g. if you want some animation, then
 * your overlay should sub-class <code>JWindow</code> rather than <code>Window</code> since you will
 * get double-buffering and eliminate flickering. Since the overlay is transparent you must take
 * care to erase the overlay background properly.
 * <p>
 * Specify a single MRL to play on the command-line.
 */
public class Viewer extends ViewerBase{
	
	final String url = "rtsp://192.168.173.217:8085";
	final String url2 ="http://wmccpinetop.axiscam.net/mjpg/video.mjpg";
	final String murl2 = "C:\\Users\\ramazan\\Downloads\\bandicam_output.mp4";
	
	final String kbb1 = "C:\\Users\\ramazan\\Downloads\\Location1-EVENING-Part1-KALE_PASAJLAR_20210415170000_20210415171350_84587.mp4";
	final String kbb2 = "D:\\KBB\\location2\\Location2-EVENING-Part3-KARTAL+KAVŞAK_PASAJLAR_20210415174939_20210415180000_212255.mp4";
	final String kbb3 = "D:\\KBB\\location3\\Location3-EVENING-Part1-VALİLİK_PASAJLAR_20210415170000_20210415172522_84587.mp4";
	final String kbb4 = "D:\\KBB\\location3\\Location3-MORNING-valilik.mp4";
    
	
    public static void main(final String[] args) throws Exception {
    	new NativeDiscovery().discover();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Viewer("");
            }
        });
    }

    public Viewer(String mrl) {
       super(mrl);
    }

    public void addCameras() {
    	super.addCameras();
    	//super.addCameras();
    }

	@Override
	public void prepareCameras()  {
		Camera camera1 = new Camera();
		Line line1 = new Line();
		line1.setStart(new Point(516,507));
		line1.setEnd(new Point(778,630));
		line1.setId(1l);
		line1.setCamera(camera1);
		line1.setData(getCameraData());
		camera1.getLineList().add(line1);
		
		Polygon polygon = new Polygon();
		polygon.addPoint(393,537);
		polygon.addPoint(516,507);
		polygon.addPoint(778,630);
		polygon.addPoint(700,690);
//		camera1.getPolygons().add(polygon);
		
		Polygon polygon2 = new Polygon();
		polygon2.addPoint(518,505);
		polygon2.addPoint(629,466);
		polygon2.addPoint(861,555);
		polygon2.addPoint(778,628);
//		camera1.getPolygons().add(polygon2);
		
		
//		Line line11 = new Line();
//		line11.setStart(new Point(100, 100));
//		line11.setEnd(new Point(150, 150));
//		line11.setId(11l);
//		line11.setCamera(camera1);
//		
//		camera1.getLineList().add(line11);
		camera1.setConnectionUrl(kbb1);
		
		Camera camera2 = new Camera();
		Line line2 = new Line();
		line2.setStart(new Point(300, 390));
		line2.setEnd(new Point(660,270));
		line2.setId(2l);
		line2.setCamera(camera2);
//		line2.setData(getDataForCamera());
		camera2.getLineList().add(line2);
		camera2.setConnectionUrl(kbb2);
		
		Camera camera3 = new Camera();
		Line line3 = new Line();
		line3.setStart(new Point(210, 260));
		line3.setEnd(new Point(300, 300));
		line3.setId(3l);
		line3.setCamera(camera3);
//		line3.setData(getDataForCamera());
		camera3.getLineList().add(line3);
		camera3.setConnectionUrl(kbb3);
		
		Camera camera4 = new Camera();
		Line line4 = new Line();
		line4.setStart(new Point(420, 280));
		line4.setEnd(new Point(560, 320));
		line4.setId(4l);
		line4.setCamera(camera4);
//		line4.setData(getDataForCamera());
		camera4.getLineList().add(line4);
		camera4.setConnectionUrl(kbb4);
		
		cameraList.add(camera1);
//		cameraList.add(camera2);
//		cameraList.add(camera3);
//		cameraList.add(camera4);
	}
    

	public List<Long>  getDataForCamera() {
		Random rand = new Random();
		List<Long> result = new ArrayList<Long>();
		for (int i = 0; i < 10; i++) {
			int rand_int1 = rand.nextInt(10000);
			result.add(new Long(rand_int1+1000));
		}
		
		Collections.sort(result);
		return result;
	}
	
	
	public List<Long> getCameraData() {
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
}

