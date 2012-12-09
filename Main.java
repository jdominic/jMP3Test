import java.io.*;

public class Main {

	public static void main(String[] args) {
		//searchDir(new File("test_files"));
		try {
			MP3 mp3 = new MP3("test_files/Kalimba.mp3");
			if(mp3 != null) {
				System.out.println("Getting frames");
				mp3.getFrames();
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
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
				MP3 mp3 = new MP3(file.getPath());
				if(mp3 != null) {
					System.out.println("Getting frames");
					mp3.getFrames();
				}
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}

	}
}