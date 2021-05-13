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

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.sun.awt.AWTUtilities;
import com.sun.jna.platform.WindowUtils;

import trafficanalyzerviewer.camera.Camera;
import trafficanalyzerviewer.camera.Line;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;

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
public abstract class ViewerBase {
	
	Logger logger = LoggerFactory.getLogger(ViewerBase.class);
	
	final String url = "rtsp://192.168.173.217:8085";
	final String url2 ="http://wmccpinetop.axiscam.net/mjpg/video.mjpg";
	final String murl = "C:\\Users\\ramazan\\Downloads\\Location1-EVENING-Part1-KALE_PASAJLAR_20210415170000_20210415171350_84587.mp4";
    final String murl2 = "C:\\Users\\ramazan\\Downloads\\bandicam_output.mp4";
	
    List<Camera> cameraList = new ArrayList<Camera>();
    
    
	final MediaPlayerFactory factory = new MediaPlayerFactory();
	Frame f= new Frame("Test Player");
	JPanel jPanel = new JPanel(); 
	List<EmbeddedMediaPlayer> mediaPlayerList = new ArrayList<EmbeddedMediaPlayer>();
	
//    public static void main(final String[] args) throws Exception {
//    	new NativeDiscovery().discover();
//
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new OverlayTestBasic("");
//            }
//        });
//    }

    public ViewerBase(String mrl) {

   	f.setSize(1280, 720);
//    	f.setExtendedState(Frame.MAXIMIZED_BOTH);
        f.setBackground(Color.black);
        f.setVisible(true);
        //f.setLayout(new BorderLayout());
        f.setLayout(new GridLayout(2, 2));

        
        //f.add(jPanel,BorderLayout.CENTER);
        addListeners();
        prepareCameras();
        addCameras();
        processdata();
    }
    
    public void processdata() {
    	for (Camera camera : cameraList) {
			for (Line line : camera.getLineList()) {
				for (Long  duration : line.getData()) {
				Timer timer = new Timer(duration.intValue(), new ActionListener() {
					  @Override
					  public void actionPerformed(ActionEvent arg0) {
						lineCrossed(line.getId());
					  }
					});
		    	timer.setRepeats(false); // Only execute once
		    	timer.start(); // Go go go!
				}
			}
		}
    }
    
    protected void addCameras() {
    	for (Camera camera : cameraList) {
    		addMediaPlayer(camera);
		}
    }
    
    public void addMediaPlayer(Camera camera) {
    	EmbeddedMediaPlayer mediaPlayer = factory.newEmbeddedMediaPlayer();
    	mediaPlayerList.add(mediaPlayer);
    	camera.setEmbeddedMediaPlayer(mediaPlayer);
    	
    	Canvas vs = new Canvas();
    	vs.setSize(1290, 740);
    	CanvasVideoSurface videoSurface = factory.newVideoSurface(vs);
        mediaPlayer.setVideoSurface(videoSurface);
        camera.setCanvas(vs);
        camera.setCanvasVideoSurface(videoSurface);
        
        //        f.add(vs,BorderLayout.CENTER);
        f.add(vs);
        AnnotationWindow aw = new AnnotationWindow(f, videoSurface.canvas(), mediaPlayer,camera);
        mediaPlayer.setOverlay(aw);
        mediaPlayer.enableOverlay(true);
        mediaPlayer.playMedia(camera.getConnectionUrl());
        mediaPlayer.pause();

//        mediaPlayer.setSubTitleFile(null);
        
//        mediaPlayer.
        setFrameLayout();
    }

    public void setFrameLayout() {
    	if(cameraList.size()==1)
    		f.setLayout(new GridLayout(1, 1));
    	else if(cameraList.size()==1)
    		f.setLayout(new GridLayout(2, 1));
    	else if(cameraList.size()==3 || cameraList.size()==4)
       		f.setLayout(new GridLayout(2, 2));
    }
    
    public abstract void prepareCameras();
    
    public void addListeners() {
    	f.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {
                    case KeyEvent.VK_F11:
                        for (EmbeddedMediaPlayer embeddedMediaPlayer : mediaPlayerList) {
                        	embeddedMediaPlayer.enableOverlay(!embeddedMediaPlayer.overlayEnabled());
                        }
                    	break;

                    case KeyEvent.VK_SPACE:
                        for (EmbeddedMediaPlayer embeddedMediaPlayer2 : mediaPlayerList) {
                        	embeddedMediaPlayer2.pause();
						}
                    	break;
                    	
                    case KeyEvent.VK_1:
                    	lineCrossed(1l);
                        break;	
                    case KeyEvent.VK_2:
                    	lineCrossed(2l);
                        break;	
                    case KeyEvent.VK_3:
                    	lineCrossed(3l);
                        break;	
                    case KeyEvent.VK_4:
                    	lineCrossed(4l);
                        break;	    
                }
            }
        });

        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for (EmbeddedMediaPlayer embeddedMediaPlayer : mediaPlayerList) {
                	embeddedMediaPlayer.release();
                }
                factory.release();
                System.exit(0);
            }
        });
    }
    
    public Line getLineById(Long id) {
    	Line result = null;
    	for (Camera camera : cameraList) {
        	for (Line line : camera.getLineList()) {
				if(line.getId()==id)
					result = line;
			}
		}
    	
    	return result;
    }
    
    public void lineCrossed(Long id) {
    	Line line = getLineById(id);
    	if(line!=null) {
	    	line.setCount(line.getCount()+1);
	    	line.setColor(Color.red);
	    	restartPlayer(line.getCamera().getEmbeddedMediaPlayer());
	    	System.out.println(line.getId()+ ":"+ line.getColor() +" yapıldı");
	    	Timer timer = new Timer(100, new ActionListener() {
				  @Override
				  public void actionPerformed(ActionEvent arg0) {
					line.setColor(Color.yellow);
					restartPlayer(line.getCamera().getEmbeddedMediaPlayer());
			    	
				  }
				});
	    	timer.setRepeats(false); // Only execute once
	    	timer.start(); // Go go go!
    	}
    }
    
    public void restartPlayer(EmbeddedMediaPlayer embeddedMediaPlayer) {
    	embeddedMediaPlayer.enableOverlay(false);
    	embeddedMediaPlayer.enableOverlay(true);
    }
    
}

