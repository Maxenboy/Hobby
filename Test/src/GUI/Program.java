package GUI;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class Program {
	private static File folder;
	private BufferedWriter bw;
	private TreeMap<String, String> filenames;
	private static ArrayList<String> clickpath;
	private HashSet<String> ser, mov;

	public Program() {
		try {
			folder = new File("C:\\Users\\M@sk\\Desktop");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		ser = new HashSet<String>();
		mov = new HashSet<String>();
		filenames = new TreeMap<String, String>();

	}

	public void update() throws IOException {
		filenames.clear();

		File[] files = folder.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				recursiveTraversal(file);
			} else if (!file.getName().toUpperCase().contains("SAMPLE")
					&& (file.getName().endsWith(".avi")
							|| file.getName().endsWith(".mkv")
							|| file.getName().endsWith(".mp4")
							|| file.getName().endsWith(".MP4")
							|| file.getName().endsWith(".flv")
							|| file.getName().endsWith(".mov")
							|| file.getName().endsWith(".mpg")
							|| file.getName().endsWith(".mpg") || file
							.getName().endsWith(".m2ts"))) {
				if (isSeries(file.getName())) {
					ser.add(file.getName());
					filenames.put(file.getAbsolutePath(), file.getName());
				} else {
					mov.add(file.getName());
					filenames.put(file.getAbsolutePath(), file.getName());
				}

			}
		}
	}

	public void print(String path, String label) throws IOException {
		bw = new BufferedWriter(new FileWriter(path));
		Set<Map.Entry<String, String>> entries = filenames.entrySet();
		for (Entry<String, String> s : entries) {
			bw.write(s.getValue());
			bw.write("\r\n");
		}
		bw.write("Amount of movies & series = " + filenames.size());
		bw.close();

	}

	public void recursiveTraversal(File file) throws IOException {
		if (file.isFile()
				&& !file.getName().toUpperCase().contains("SAMPLE")
				&& (file.getName().endsWith(".avi")
						|| file.getName().endsWith(".mkv")
						|| file.getName().endsWith(".MP4")
						|| file.getName().endsWith(".mp4")
						|| file.getName().endsWith(".flv")
						|| file.getName().endsWith(".mov")
						|| file.getName().endsWith(".mpg") || file.getName()
						.endsWith(".m2ts"))) {
			if (isSeries(file.getName())) {
				ser.add(file.getName());
				filenames.put(file.getAbsolutePath(), file.getName());
			} else {
				mov.add(file.getName());
				filenames.put(file.getAbsolutePath(), file.getName());
			}
		} else if (file.isDirectory()) {
			File allFiles[] = file.listFiles();
			for (File aFile : allFiles) {
				recursiveTraversal(aFile);
			}
		}

	}

	public StringBuilder find(String s, String label) {
		StringBuilder find = new StringBuilder();
		StringBuilder movies = new StringBuilder();
		StringBuilder series = new StringBuilder();
		boolean found = false;
		Set<Map.Entry<String, String>> entries = filenames.entrySet();
		for (Entry<String, String> e : entries) {
			if (e.getValue().toUpperCase().contains(s.toUpperCase())
					&& isSeries(e.getValue())) {
				series.append(e.getValue() + "\n");
				find.append(e.getValue() + "\n");
				found = true;
			} else if (e.getValue().toUpperCase().contains(s.toUpperCase())) {
				movies.append(e.getValue() + "\n");
				find.append(e.getValue() + "\n");
				found = true;
			}

		}
		if (!found) {
			find.append("The movie you searched for is not present in this directory or you do not have it!");
			movies.append("The movie you searched for is not present in this directory or you do not have it!");
			series.append("The movie you searched for is not present in this directory or you do not have it!");
		}
		if (label.equals("Movies & Episodes")) {
			return find;
		} else if (label.equals("Movies")) {
			return movies;
		} else {
			return series;
		}

	}

	public StringBuilder where(String s) {
		StringBuilder find = new StringBuilder();
		clickpath = new ArrayList<String>();
		Set<Map.Entry<String, String>> entries = filenames.entrySet();
		for (Entry<String, String> e : entries) {
			if (e.getValue().toUpperCase().contains(s.toUpperCase())) {
				clickpath.add(e.getKey());
				find.append(e.getKey() + "\n");
			}
		}
		return find;
	}

	public String[] random(String label) {
		Random rand = new Random();
		Object[] paths = null;
		String[] v = new String[2];
		if (label.equals("Movies & Episodes")) {
			paths = filenames.values().toArray();
		} else if (label.equals("Movies")) {
			paths = mov.toArray();
		} else {
			paths = ser.toArray();
		}
		int random = rand.nextInt(paths.length);
		
		v[1] = "hej";
		v[0] = paths[random].toString();
		return v;

	}

	public void setDestination(String dest) {
		folder = new File(dest);
	}

	public String getDestination() {
		return folder.getAbsolutePath();
	}

	public String getTotal(String label) {
		if (label.equals("Movies & Episodes")) {
			return "Amount of movies & series = " + filenames.size();
		} else if (label.equals("Movies")) {
			return "Amount of movies = " + mov.size();
		} else {
			return "Amount of episodes = " + ser.size();
		}
	}

	public ArrayList<String> getClickPath() {
		return clickpath;
	}

	public boolean isSeries(String name) {
		boolean found = false;
		String f = name.replace(".", " ");
		String[] temp = f.split(" ");
		for (String g : temp) {
			if (Pattern.matches(
					"[[a-z]|[A-Z]][0-9][0-9][[a-z]|[A-Z]][0-9][0-9]", g)) {
				found = true;
			}
		}
		return found;
	}
	public void readFromFile(File pathname){
		
	}

}