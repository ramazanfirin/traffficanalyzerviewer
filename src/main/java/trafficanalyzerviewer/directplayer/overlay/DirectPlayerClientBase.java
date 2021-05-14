package trafficanalyzerviewer.directplayer.overlay;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import trafficanalyzerviewer.camera.Camera;
import trafficanalyzerviewer.camera.Line;
import trafficanalyzerviewer.util.Util;

public class DirectPlayerClientBase {
	
	final String url = "rtsp://192.168.173.217:8085";
	final String url2 ="http://wmccpinetop.axiscam.net/mjpg/video.mjpg";
	final String murl2 = "C:\\Users\\ramazan\\Downloads\\bandicam_output.mp4";
	
	final String kbb1 = "C:\\Users\\ramazan\\Downloads\\Location1-EVENING-Part1-KALE_PASAJLAR_20210415170000_20210415171350_84587.mp4";
	final String kbb2 = "D:\\KBB\\location2\\Location2-EVENING-Part3-KARTAL+KAVŞAK_PASAJLAR_20210415174939_20210415180000_212255.mp4";
	final String kbb3 = "D:\\KBB\\location3\\Location3-EVENING-Part1-VALİLİK_PASAJLAR_20210415170000_20210415172522_84587.mp4";
	final String kbb4 = "D:\\KBB\\location3\\Location3-MORNING-valilik.mp4";

	public List<Camera> cameraList = new ArrayList<Camera>();
	
	public void prepareCameras()  {
		
		Camera camera1 ;
		Camera camera2 ;
		Camera camera3 ;
		Camera camera4 ;


		camera1 = new Camera();
		camera2 = new Camera();
		camera3 = new Camera();
		camera4 = new Camera();

		Line line1 = new Line();
		line1.setStart(new Point(516,507));
		line1.setEnd(new Point(778,630));
		line1.setId(1l);
		line1.setCamera(camera1);
		line1.setData(Util.getCameraData());
		camera1.getLineList().add(line1);
		camera1.setShow(true);
		
//		
//		camera1.getLineList().add(line11);
		camera1.setConnectionUrl(kbb1);
		
		
		Line line2 = new Line();
		line2.setStart(new Point(1600, 690));
		line2.setEnd(new Point(700,1090));
		line2.setId(2l);
		line2.setCamera(camera2);
//		line2.setData(getDataForCamera());
		camera2.getLineList().add(line2);
		camera2.setConnectionUrl(kbb2);
		camera2.setShow(false);
		
		Line line3 = new Line();
		line3.setStart(new Point(600, 700));
		line3.setEnd(new Point(850, 800));
		line3.setId(3l);
		line3.setCamera(camera3);
//		line3.setData(getDataForCamera());
		camera3.getLineList().add(line3);
		camera3.setConnectionUrl(kbb3);
		camera3.setShow(false);
		
		Line line4 = new Line();
		line4.setStart(new Point(1100, 750));
		line4.setEnd(new Point(1600, 850));
		line4.setId(4l);
		line4.setCamera(camera4);
//		line4.setData(getDataForCamera());
		camera4.getLineList().add(line4);
		camera4.setConnectionUrl(kbb4);
		camera4.setShow(false);
		
		cameraList.add(camera1);
//		cameraList.add(camera2);
//		cameraList.add(camera3);
//		cameraList.add(camera4);
//		
		
	}

	public List<Camera> getCameraList() {
		return cameraList;
	}

	public void setCameraList(List<Camera> cameraList) {
		this.cameraList = cameraList;
	}
}
