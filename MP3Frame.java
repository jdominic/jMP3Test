import java.io.*;

public class MP3Frame {

	private MP3Header header;
	private byte[] body;
	
	public MP3Frame(InputStream in) throws IOException {
		header = MP3Header.readHeader(in);
		body = new byte[header.frameSize()-4 < 0 ? 0 : header.frameSize()-4];
		in.read(body);
	}
}