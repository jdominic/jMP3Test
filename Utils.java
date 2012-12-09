public class Utils {
	private static char[] letters = {'0','1','2','3','4','5','6','7',
			  '8','9','A','B','C','D','E','F'};

	public static void printBytes(byte[] bs) {
		System.out.println(bytesToHex(bs));
	}

	public static String byteToHex(byte b) {
		int mask = 0x0000000F;
		String toRet = ""+letters[(b & mask)];
		toRet = letters[(b >> 4) & mask] + toRet;
		return toRet;
	}
	
	public static String bytesToHex(byte[] bs) {
		StringBuilder sb = new StringBuilder();
		for(byte b : bs) {
			sb.append(byteToHex(b));
		}
		return sb.toString();
	}
	
	public static int convert(byte[] bytes) {
		int toRet = 0;
		int con = bytes[0] & 255;
		for(int i = 1;i < bytes.length;i++) {
			toRet <<= 8;
			con = bytes[i] & 255;
			toRet |= con;
		}
		return toRet;	
	}
	
	public static int convertSafeSynch(byte[] bytes) {
		int a = 0;
		a |= bytes[0];		
		for(int i = 1;i < bytes.length;i++) {
			a <<= 7;
			a |= bytes[i];
		}	
		return a;
	}
}