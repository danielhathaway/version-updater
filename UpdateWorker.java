package versionupdater;

import java.io.File;

import javax.swing.JOptionPane;

class UpdateWorker implements Runnable {
	private VersionUpdater vu;
	private Thread t;

	protected UpdateWorker(VersionUpdater v) {
		vu = v;
		t = new Thread(this, "Version Update Worker");
		t.start();
	}
	public void run() {
		vu.startOp();
		updateAction(vu.getQueue());
		vu.endOp();
	}

	private void updateAction(File[] files) {
		int backup = vu.promptDialog("Backup files before changing them?", "Backup Prompt");
		if (backup == JOptionPane.CANCEL_OPTION) {
			return;
		}
		else if (backup == JOptionPane.YES_OPTION) {
			vu.setStatusText("Gathering files to backup...");

		}
		else {
			vu.setStatusText("Gathering files to update...");
		}
		vu.getFileCount(files);
		if (backup == JOptionPane.YES_OPTION) {
			vu.setStatusText("Searching for previous version...");
			vu.softResetProgress();
			if (!vu.getPreviousVersion(files)) {
				vu.setPreviousVersion("0.0.0");
			}
			vu.setStatusText("Backing up previous version...");
			vu.softResetProgress();
			if (!BackupOperations.backupPreviousVersion(vu, files)) {
				vu.errorMessage("An error occurred during the backup process. Aborting version update.", "Abort");
				vu.setStatusText("Backup and version update were aborted due to an error.");
				return;
			}
		}
		vu.setStatusText("Updating version information for " + vu.getFileCount() + " files...");
		vu.softResetProgress();
		if (!vu.recursiveChange(files, vu.getVersionField(), vu.getDateField())) {
			vu.errorMessage("An error occurred during the version update process. Aborting version update.", "Abort");
			vu.setStatusText("Version update was aborted due to an error.");
			return;
		}
		else {
			vu.successMessage("Version update was successful.", "Done");
			vu.setStatusText("Version information updated.");
			return;
		}
	}

}
