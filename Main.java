import java.io.*;

public class Main {

	public static void main(String[] args) throws Exception {
		//searchDir(new File("test_files"));
		MP3.parseMP3("test_files/Kalimba.mp3");
	}
	
	public static void searchDir(File dir) {
		for(File sub : dir.listFiles()) {
			if(sub.isDirectory()) searchDir(sub);
			else processFile(sub);
		}
	} 
	
	public static void processFile(File file) {
		if(file.getName().endsWith(".mp3")) {
			System.out.println(file.getName());
			try {
				MP3 mp3 = MP3.parseMP3(file.getPath());
				if(mp3 != null) mp3.getFrames();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}

	}
}