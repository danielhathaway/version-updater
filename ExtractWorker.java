package versionupdater;

import java.io.File;

class ExtractWorker implements Runnable {
	private VersionUpdater vu;
	private Thread t;

	protected ExtractWorker(VersionUpdater v) {
		vu = v;
		t = new Thread(this, "Version Extraction Worker");
		t.start();
	}
	public void run() {
		vu.startOp();
		vu.setStatusText("Looking for additional files in selected directories...");
		extractAction(vu, vu.getQueue());
		vu.endOp();
	}

	private void extractAction(VersionUpdater vu, File[] files) {
		if(files != null && files.length != 0) {
			vu.getFileCount(files);
			vu.setStatusText("Looking for version information in "+vu.getFileCount()+" files...");
			vu.softResetProgress();
			if (!vu.getPreviousVersion(files)) {
				vu.warnMessage("Unable to find a version in the selected files.", "No Joy");
				vu.setStatusText("No version information found.");
				if (vu.getPreviousVersion() == null) {
					vu.setPreviousVersion("0.00.0000");
					vu.setVersionField(vu.getPreviousVersion());
				}
				return;
			}
			else {
				vu.successMessage("Latest version was successfully extracted from the selected files.", "Done");
				vu.setStatusText("Latest version information found.");
				vu.setVersionField(vu.getPreviousVersion());
				return;
			}
		}
	}

}
