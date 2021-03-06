import java.util.*;
import java.io.*;
import java.math.*;

public class MP3 {

	private float[] BITRATE = {0, 32000, 40000, 48000, 56000, 64000, 80000, 96000,
				112000, 128000, 160000, 192000, 224000, 256000, 320000};
	
	private float[] FREQ = {44100, 48000, 32000};
	
	private int ID3_VERSION;

	private int MPEG_VERSION;
	private int LAYER;

	private File file;
	private InputStream in;

	private ID3 id3;
	
/*	public static MP3 parseMP3(String filepath) throws IOException{
		File f = new File(filepath);
		FileInputStream fis = new FileInputStream(f);
		byte[] header = new byte[4];
		fis.read(header);
		fis.close();
		in = new FileInputStream(file);
		if(header[0] == 73 && header[1] == 68 && header[2] == 51) {
			return new MP3(filepath, true);
		} else if(header[0] == -1) {
			return new MP3(filepath, false);
		} else if(header[0] == 82 & header[1] == 73 & header[2] == 70 & header[3] == 70) {
			throw new IOException("RIFF header");
		} else {
			throw new IOException("Format not supported for file: " + filepath);
		}
	}
*/
	public MP3(String filepath) throws IOException {
		this(new File(filepath));
	}
	
	public MP3(File f) throws IOException {
		this(new FileInputStream(f));
		file = f;
	}
	
	public MP3(InputStream in) {
		this.in = in;
		System.out.println("MP3 loaded");
	}

	public ID3 getID3() {
		return id3;
	}
	
	public MP3Frame[] getFrames() throws IOException {
		in = new FileInputStream(file);
		id3 = new ID3(in);
		ArrayList<MP3Frame> frames = new ArrayList<MP3Frame>();
		int count = 0;
		while(hasNextFrame()) {
			frames.add(getNextFrame());
			System.out.println(++count);
		}
		MP3Frame[] toRet = new MP3Frame[0];
		return frames.toArray(toRet);
	}
	
	public MP3Frame getNextFrame() throws IOException {
		if(id3 == null) id3 = new ID3(in);
		return new MP3Frame(in);
	}
	
	public boolean hasNextFrame() throws IOException {
		return in.available() > 0;
	}
	
	public void readTagFrames() throws IOException {
		byte[] tag_size = new byte[4];
		in.read(tag_size);
		int target = Utils.convertSafeSynch(tag_size);
		int total = 0;
		while(total < target) {
			//total += readTagFrame(in);
		}
	}

}