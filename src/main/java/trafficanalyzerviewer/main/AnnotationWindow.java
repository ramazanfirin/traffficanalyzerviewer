package trafficanalyzerviewer.main;

import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Iterator;


import javax.swing.JWindow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jna.platform.WindowUtils;

import trafficanalyzerviewer.camera.Camera;
import trafficanalyzerviewer.camera.Line;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

public class AnnotationWindow extends JWindow{

	private static final long serialVersionUID = 8498200660685726854L;
	
	Logger logger = LoggerFactory.getLogger(AnnotationWindow.class);
	
	private Dimension videoDimension;
	private Canvas videoSurface;
	private MediaPlayer mediaPlayer;
	private Camera camera;
	
	public AnnotationWindow(Window owner, Canvas videoSurface, MediaPlayer mediaPlayer,Camera camera) {
		
		super(owner, WindowUtils.getAlphaCompatibleGraphicsConfiguration());
		this.camera = camera;
		
		
		
		videoDimension = mediaPlayer.getVideoDimension();
		
		owner.addComponentListener(new ComponentAdapter() {
        	@Override
        	public void componentResized(ComponentEvent e) {
        		repaint();
        	}
		});
		
		videoSurface.addComponentListener(new ComponentAdapter() {
        	@Override
        	public void componentResized(ComponentEvent e) {
        		repaint();
        	}
		});
		
		mediaPlayer.addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
			@Override
			public void timeChanged(MediaPlayer mediaPlayer, long newTime) {
				repaint();
			}
		});
		
		this.videoSurface = videoSurface;
		this.mediaPlayer = mediaPlayer;
		
		setOpacity(0.5f);
		setBackground(new Color(1.0f, 1.0f, 1.0f, 0.0f));
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		if(videoDimension == null) {
			videoDimension = mediaPlayer.getVideoDimension();
		}
		
		
		
		if(videoDimension != null) {
			
			//Input in Video-Dimension: 1920 x 1080 ... this would be saved in an AnnotationFormat File
			int annoInput_X = 1000;
			int annoInput_Y = 500;
			int annoInput_W = 200;
			int annoInput_H = 100;
			
			int w = videoSurface.getWidth();
			int h = videoSurface.getHeight();
			
			//linear Interpolation from 1920 x 1080 to VideoSurface (e.g. 1000 x 500)
			int interpolated_x = (int) (1.0f * annoInput_X * w / videoDimension.width);
			int interpolated_y = (int) (1.0f * annoInput_Y * h / videoDimension.height);
			int interpolated_w = (int) (1.0f * annoInput_W * w / videoDimension.width);
			int interpolated_h = (int) (1.0f * annoInput_H * h / videoDimension.height);
			
			float aspectRatio = 1.0f * videoDimension.width / videoDimension.height;
			float surfaceRatio = 1.0f * w / h;
			
			//Determine black borders
			if(surfaceRatio > aspectRatio) {
				//border left/right -> change x / width
				
				int actualWidth = (int) (aspectRatio * h);				
				int borderSize = w - actualWidth; //left and right
				
				//recalculate values with actual width and add half of border size
				interpolated_x = (int) (1.0f * annoInput_X * actualWidth / videoDimension.width) + borderSize/2;
				interpolated_w = (int) (1.0f * annoInput_W * actualWidth / videoDimension.width);
				
			} else {
				//border up/down -> change y / height
				
				int actualHeight = (int) (w / aspectRatio);		
				int borderSize = h - actualHeight; //top and down
				
				//recalculate values with actual height and add half of border size
				interpolated_y = (int) (1.0f * annoInput_Y * actualHeight / videoDimension.height) + borderSize/2;
				interpolated_h = (int) (1.0f * annoInput_H * actualHeight / videoDimension.height);
			}
			
			
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			
//			g2.drawRect(interpolated_x, interpolated_y, interpolated_w, interpolated_h);
//            g2.fillRect(interpolated_x, interpolated_y, interpolated_w, interpolated_h);
            
           
            for (Iterator iterator = camera.getLineList().iterator(); iterator.hasNext();) {
    			Line line = (Line) iterator.next();
    			g.setColor(line.getColor());
//    			if(line.getId()==1l)
//    				logger.info(line.getId()+":"+line.getColor().toString());
    			 Polygon polygon = new Polygon();
    			polygon.addPoint((int)line.getStart().getX(),(int)line.getStart().getY());
    			polygon.addPoint((int)line.getStart().getX()+40,(int)line.getStart().getY());
    			
    			polygon.addPoint((int)line.getEnd().getX()+40,(int)line.getEnd().getY());
    			polygon.addPoint((int)line.getEnd().getX(),(int)line.getEnd().getY());
    			g2.fillPolygon(polygon);
    			

    		}

    		Font myFont = new Font ("Courier New", 1, 13);
    		g2.setFont (myFont);
    		g2.setColor(Color.BLACK);
    		g2.setComposite(AlphaComposite.SrcOver.derive(1f));
    		for (Iterator iterator = camera.getLineList().iterator(); iterator.hasNext();) {
    			Line line = (Line) iterator.next();
    			int countX = ((int)line.getStart().getX()+(int)line.getEnd().getX())/2;
    			int countY = ((int)line.getStart().getY()+(int)line.getEnd().getY())/2;
    			g2.drawString(line.getCount().toString(), countX, countY);
    			
    		}
    		
//    		Font myFont2 = new Font ("Courier New", 1, 13);
//    		g2.setFont (myFont2);
//    		g2.setColor(Color.BLACK);
//    		g2.setComposite(AlphaComposite.SrcOver.derive(1f));
//			if(mediaPlayer.getTime() % 1000 ==0)
//				g2.drawString(Long.toString(mediaPlayer.getTime()), 100, 100);
    		
    		
    		for (Iterator iterator = camera.getPolygons().iterator(); iterator.hasNext();) {
    			Polygon polygon = (Polygon) iterator.next();
    			g.setColor(Color.red);
//    			
    			g2.fillPolygon(polygon);
    			

    		}

    		
    			System.out.println(camera.getCanvas().getHeight());
    			System.out.println(camera.getCanvas().getWidth());
    		
		}
		
		
	}
	
	public void drawPolygon(Graphics g) {
		
	}
}
