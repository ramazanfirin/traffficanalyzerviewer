package trafficanalyzerviewer.camera;

import java.awt.Canvas;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;

public class Camera {

	String name;
	String ip;
	String port;
	String username;
	String password;
	String connectionUrl;
	
	List<Line> lineList = new ArrayList<Line>();
	EmbeddedMediaPlayer embeddedMediaPlayer;
	
	List<Polygon> polygons = new ArrayList<Polygon>();
	
	Canvas canvas;
	
	CanvasVideoSurface canvasVideoSurface;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Line> getLineList() {
		return lineList;
	}

	public void setLineList(List<Line> lineList) {
		this.lineList = lineList;
	}

	public String getConnectionUrl() {
		return connectionUrl;
	}

	public void setConnectionUrl(String connectionUrl) {
		this.connectionUrl = connectionUrl;
	}

	public EmbeddedMediaPlayer getEmbeddedMediaPlayer() {
		return embeddedMediaPlayer;
	}

	public void setEmbeddedMediaPlayer(EmbeddedMediaPlayer embeddedMediaPlayer) {
		this.embeddedMediaPlayer = embeddedMediaPlayer;
	}

	public List<Polygon> getPolygons() {
		return polygons;
	}

	public void setPolygons(List<Polygon> polygons) {
		this.polygons = polygons;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public CanvasVideoSurface getCanvasVideoSurface() {
		return canvasVideoSurface;
	}

	public void setCanvasVideoSurface(CanvasVideoSurface canvasVideoSurface) {
		this.canvasVideoSurface = canvasVideoSurface;
	}
	
}
