package trafficanalyzerviewer.directplayer.overlay;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.springframework.beans.factory.annotation.Autowired;

import trafficanalyzerviewer.camera.Camera;
import trafficanalyzerviewer.directplayer.overlay.base.ViewerBase;
import trafficanalyzerviewer.directplayer.overlay.base.ViewerOverlay;
import trafficanalyzerviewer.service.CameraService;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayer;

public class Viewer extends ViewerBase{

	@Autowired
	CameraService cameraService = new CameraService();
	
	JFrame jframe = new JFrame("Video Player");
	JPanel jPanel = new JPanel();
	Dimension DimMax = Toolkit.getDefaultToolkit().getScreenSize();
	int viewWitdh = (int)DimMax.getWidth()/2;
	int viewHeight = (int)DimMax.getHeight()/2;
	int viewCount = 4;
	
	List<ViewerOverlay> overlayList = new ArrayList<ViewerOverlay>();
	List<JPanel> imagePaneList = new ArrayList<JPanel>();
	
    public void run(String[] args) throws InvocationTargetException, InterruptedException {

    	
    	jPanel.setLayout(new GridLayout(2, 2));
		List<Camera> cameraList = cameraService.getCameraList();
		prepareOverlays(viewCount);
		
		play(cameraList);
		
		
		jframe.setSize(1920, 1080);
		jframe.setVisible(true);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setContentPane(jPanel);
		
		processdata(cameraList);
		
		
    }
	
    public void play(List<Camera> cameraList) throws InvocationTargetException, InterruptedException {
    	for (int i = 0; i < cameraList.size(); i++) {
			Camera camera = cameraList.get(i);
			if(i<viewCount) {
				ViewerOverlay directTestPlayer = overlayList.get(i);
				directTestPlayer.setCamera(camera);
				directTestPlayer.play();
			}
		}
    }
    
    public void prepareOverlays(int viewCount) throws InvocationTargetException, InterruptedException {
    	int count =0;
    	for (int i = 0; i < viewCount; i++) {
    		ViewerOverlay directTestPlayer = new ViewerOverlay(null,viewWitdh, viewHeight, null);
			MediaPlayer mediaPlayer = directTestPlayer.getMediaPlayer();
			JPanel imagePane = directTestPlayer.getImagePane();
			jPanel.add(imagePane);
			
			overlayList.add(directTestPlayer);
    	}
    	    	
    }
    
	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		new NativeDiscovery().discover();
		new Viewer().run(args);;

	}
	
	

}


