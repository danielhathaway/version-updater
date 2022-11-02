package versionupdater;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileOperations {

	protected static String[] load(VersionUpdater vu, File file) {
		FileReader fileReader;
		try {
			fileReader = new FileReader(file);
		}
		catch (FileNotFoundException e) {
			vu.errorMessage("Failed to load the contents of " + file.getName() + ".", "Failed");
			return null;
		}
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		List<String> lines = new ArrayList<String>();
		String line = null;
		try {
			while ((line = bufferedReader.readLine()) != null) {
				lines.add(line);
			}
		}
		catch (IOException e1) {
			vu.errorMessage("An error occured while reading the contents of " + file.getName() + ".", "Failed");
			return null;
		}
		try {
			bufferedReader.close();
		}
		catch (IOException e) {
			vu.warnMessage("Unable to close the file reader.", "Warning");
		}
		return lines.toArray(new String[lines.size()]);
	}

	@SuppressWarnings("resource")
	protected static boolean writeContents (VersionUpdater vu, File file, String[] contents) {
		BufferedWriter outputWriter;
		try {
			outputWriter = new BufferedWriter(new FileWriter(file));
		}
		catch (IOException e) {
			vu.errorMessage("Unable to open " + file.getName() + " for writing.", "Failed");
			return false;
		}
		for (int i = 0; i < contents.length; i++) {
			try {
				outputWriter.write(contents[i] + System.lineSeparator());
			}
			catch (IOException e) {
				vu.errorMessage("Unable to write to " + file.getName() + ".", "Failed");
				return false;
			}
		}
		try {
			outputWriter.flush();
		}
		catch (IOException e) {
			vu.warnMessage("Unable to flush the file writer.", "Warning");
		}
		try {
			outputWriter.close();
		}
		catch (IOException e) {
			vu.warnMessage("Unable to close the file writer.", "Warning");
		}
		return true;
	}

}
