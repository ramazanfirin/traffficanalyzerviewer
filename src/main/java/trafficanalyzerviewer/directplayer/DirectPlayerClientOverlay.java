package trafficanalyzerviewer.directplayer;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayer;

public class DirectPlayerClientOverlay {

	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		new NativeDiscovery().discover();
		
		//System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//		Mat mat = Mat.eye(3, 3, CvType.CV_8UC1);
//		System.out.println("Mat: " + mat.dump());
		
		JFrame player = new JFrame("Video Player");
		
		//1 - Direct Test Player
		DirectTestPlayerOverlay directTestPlayer = new DirectTestPlayerOverlay(1280, 720, args);
		
		MediaPlayer mediaPlayer = directTestPlayer.getMediaPlayer();
		JPanel imagePane = directTestPlayer.getImagePane();
		
		//2 - EmbeddedMediaPlayer
//		EmbeddedMediaPlayerComponent embeddedMediaPlayerComponent = new EmbeddedMediaPlayerComponent();
//		MediaPlayer mediaPlayer = embeddedMediaPlayerComponent.getMediaPlayer();
//		
		
		player.setSize(1280, 720);
		player.setVisible(true);
		player.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		player.setContentPane(imagePane);
//		player.setContentPane(embeddedMediaPlayerComponent);
		
//		mediaPlayer.playMedia("videos/bbb.mp4");
		mediaPlayer.playMedia("C:\\Users\\ramazan\\Downloads\\Location1-EVENING-Part1-KALE_PASAJLAR_20210415170000_20210415171350_84587.mp4");
	}
}


