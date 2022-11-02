package versionupdater;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class BackupOperations {

	protected static boolean backupPreviousVersion(VersionUpdater vu, File[] files) {
		File backupdir = new File(files[0].getParentFile().getAbsolutePath() + File.separator + "v" + vu.getPreviousVersion() + " backup");
		backupdir.mkdirs();
		if (!copyToBackup(vu, files, backupdir)) {
			return false;
		}
		return true;
	}

	protected static boolean copyToBackup(VersionUpdater vu, File[] files, File backupdir) {
		for(File f : files) {
			if(f.isDirectory()) {
				File backupsubdir = new File(backupdir.getAbsolutePath() + File.separator + f.getName());
				backupsubdir.mkdirs();
				if (!copyToBackup(vu, f.listFiles(), backupsubdir)) {
					return false;
				}
			}
			else {
				try {
					Files.copy(f.toPath(), new File(backupdir + File.separator + f.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
				}
				catch (IOException e) {
					vu.errorMessage("Failed to copy " + f.getName()+" to the backup directory!", "Failed");
					return false;
				}
			}
		}
		vu.fileDone();
		return true;
	}

}
