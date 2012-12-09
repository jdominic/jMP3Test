import java.io.*;
import java.util.HashMap;

public class ID3 {
	
	public static ID3 NONE = new ID3();
	
	HashMap<String, String> tags;
	
	private ID3(){}

	public ID3(InputStream in) throws IOException {
		tags = new HashMap<String, String>();
		byte[] id = new byte[3];
		in.read(id);
		byte[] version = new byte[2];
		in.read(version);
		byte[] flags = new byte[1];
		in.read(flags);
		byte[] size = new byte[4];
		in.read(size);
		int headerSize = Utils.convertSafeSynch(size);
		System.out.println("skipping id3 - " + headerSize);
		in.skip(headerSize);
	}
}