import java.io.*;
import java.util.Arrays;

public class MP3Header {

	public enum MPEG_VERSION {
		TWO_FIVE,
		RESERVED,
		TWO,
		ONE
	}
	
	public enum LAYER {
		RESERVED,
		THREE,
		TWO,
		ONE
	}
	
	public enum CRC {
		YES,
		NO
	}
	
	//[VERSION][LAYER][INDEX]
	private static final float[][][] BITRATE = {
		{
			{},
			{0, 8000, 16000, 24000, 32000, 40000, 48000, 56000, 64000, 80000, 96000, 112000, 128000, 144000, 160000, 0},
			{0, 8000, 16000, 24000, 32000, 40000, 48000, 56000, 64000, 80000, 96000, 112000, 128000, 144000, 160000, 0},
			{0, 32000, 48000, 56000, 64000, 80000, 96000, 112000, 128000, 144000, 160000, 176000, 192000, 224000, 256000, 0}
			
		},
		{},
		{
			{},
			{0, 8000, 16000, 24000, 32000, 40000, 48000, 56000, 64000, 80000, 96000, 112000, 128000, 144000, 160000, 0},
			{0, 8000, 16000, 24000, 32000, 40000, 48000, 56000, 64000, 80000, 96000, 112000, 128000, 144000, 160000, 0},
			{0, 32000, 48000, 56000, 64000, 80000, 96000, 112000, 128000, 144000, 160000, 176000, 192000, 224000, 256000, 0}
		},
		{
			{},
			{0, 32000, 40000, 48000, 56000, 64000, 80000, 96000, 112000, 128000, 160000, 192000, 224000, 256000, 320000, 0},
			{0, 32000, 48000, 56000, 64000, 80000, 96000, 112000, 128000, 160000, 192000, 224000, 256000, 320000, 384000, 0},
			{0, 32000, 64000, 96000, 128000, 160000, 192000, 224000, 256000, 288000, 320000, 352000, 384000, 416000, 448000, 0}
		}
	};
	
	//[VERSION][INDEX]
	private static final float[][] FREQ = {
		{11025, 12000, 8000, 0},
		{0, 0, 0, 0},
		{22050, 24000, 16000, 0},
		{44100, 48000, 32000, 0}
	};
	
	//[VERSION][LAYER]
	private static final float[][] SAMPLES = {
		{0, 576, 1152, 384},
		{0, 0, 0, 0},
		{0, 576, 1152, 384},
		{0, 1152, 1152, 384}
	};
	
	public enum PADDING {
		NO,
		YES
	}
	
	public enum CHANNEL {
		STEREO,
		JOINT_STEREO,
		DUAL,
		MONO
	}
	
	public enum INTENSITY {
		OFF,
		ON
	}
	
	public enum MS {
		OFF,
		ON
	}
	
	public enum COPYRIGHT {
		NO,
		YES
	}
	
	public enum ORIGINAL {
		NO,
		YES
	}
	
	public enum EMPHASIS {
		NONE,
		FIFTY_FIFTEEN,
		RESERVED,
		CCIT
	}
	
	float bitrate;
	float freq;
	
	private MPEG_VERSION version;
	private LAYER layer;
	private CRC crc;
	private PADDING pad;
	private CHANNEL channel;
	private INTENSITY intensity;
	private MS ms;
	private COPYRIGHT copyright;
	private ORIGINAL original;
	private EMPHASIS emphasis;
	
	private MP3Header(MPEG_VERSION mv, LAYER l, CRC c, float b, float f, PADDING p, CHANNEL ch, INTENSITY i, MS ms, COPYRIGHT co, ORIGINAL o, EMPHASIS e) {
		version = mv;
		layer = l;
		crc = c;
		bitrate = b;
		freq = f;
		pad = p;
		channel = ch;
		intensity = i;
		this.ms = ms;
		copyright = co;
		original = o;
		emphasis = e;
	}
	
	public static MP3Header readHeader(InputStream in) throws IOException {
		byte[] header = new byte[4];
		in.read(header);
		return readHeader(header);
	}
	
	public int frameSize() {
		return (int)(((SAMPLES[version.ordinal()][layer.ordinal()] / 8.0) * bitrate / freq) + (pad == PADDING.YES ? 1 : 0));
	}
	
	public static MP3Header readHeader(byte[] header) throws IOException {
		//Check first byte for all 0's and proper header length
		if (header[0] != -1 || header.length != 4) throw new IOException("Header value: " + Arrays.toString(header));
		//Read values from the second byte
		CRC c = CRC.values()[(header[1] & 0x01)];
		LAYER l = LAYER.values()[((header[1] >> 1) & 0x03)];
		MPEG_VERSION m = MPEG_VERSION.values()[((header[1] >> 3) & 0x03)];
		//Read values from the third byte
		PADDING p = PADDING.values()[((header[2] >> 1) & 0x01)];
		float f = FREQ[m.ordinal()][((header[2] >> 2) & 0x03)];
		float b = BITRATE[m.ordinal()][l.ordinal()][((header[2] >> 4) & 0x0F)];
		//Read values from last byte
		EMPHASIS e = EMPHASIS.values()[(header[3]& 0x03)];
		ORIGINAL o = ORIGINAL.values()[((header[3] >> 2) & 0x01)];
		COPYRIGHT co = COPYRIGHT.values()[((header[3] >> 3) & 0x01)];
		MS ms = MS.values()[((header[3] >> 4) & 0x01)];
		INTENSITY i = INTENSITY.values()[((header[3] >> 5) & 0x01)];
		CHANNEL ch = CHANNEL.values()[((header[3] >> 6) & 0x03)];
		return new MP3Header(m, l, c, b, f, p, ch, i, ms, co, o, e);
	}
	
	public String toString() {
		return String.format("MPEG Version: %s\nLayer: %s\nBitrate: %.0f\nFreq: %.0f\nSize: %d\n"+
					"CRC: %s\nPadding: %s", version, layer, bitrate, freq, frameSize(), crc, pad);
	}
}