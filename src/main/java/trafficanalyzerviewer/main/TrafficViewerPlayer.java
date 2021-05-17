package trafficanalyzerviewer.main;

import trafficanalyzerviewer.camera.Camera;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.binding.internal.libvlc_instance_t;
import uk.co.caprica.vlcj.player.embedded.DefaultEmbeddedMediaPlayer;

public class TrafficViewerPlayer extends DefaultEmbeddedMediaPlayer{

	public TrafficViewerPlayer(LibVlc libvlc, libvlc_instance_t instance) {
		super(libvlc, instance);
		// TODO Auto-generated constructor stub
	}

	Camera camera ;

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
}
