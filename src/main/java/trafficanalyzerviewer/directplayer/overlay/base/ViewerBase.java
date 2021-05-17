package trafficanalyzerviewer.directplayer.overlay.base;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Timer;

import trafficanalyzerviewer.camera.Camera;
import trafficanalyzerviewer.camera.Line;

public class ViewerBase {
	

	public void processdata(List<Camera> cameraList) {
    	for (Camera camera : cameraList) {
    		if(camera.getProcessData()) {
				for (Line line : camera.getLineList()) {
					for (Long  duration : line.getData()) {
					Timer timer = new Timer(duration.intValue(), new ActionListener() {
						  @Override
						  public void actionPerformed(ActionEvent arg0) {
							lineCrossed(line);
						  }
						});
			    	timer.setRepeats(false); // Only execute once
			    	timer.start(); // Go go go!
					}
				}
			}
		}
    }
	
	public void lineCrossed(Line line) {
    	if(line!=null) {
	    	line.setCount(line.getCount()+1);
	    	line.setColor(Color.red);
	    	System.out.println(line.getId()+ ":"+ line.getColor() +" yapıldı");
	    	Timer timer = new Timer(100, new ActionListener() {
				  @Override
				  public void actionPerformed(ActionEvent arg0) {
					line.setColor(Color.yellow);
				  }
				});
	    	timer.setRepeats(false); // Only execute once
	    	timer.start(); // Go go go!
    	}
    }
	
}
