package versionupdater;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class VersionUpdater extends javax.swing.JFrame {

	public VersionUpdater() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e) {

		}
		initComponents();
		VersionUpdater.this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/toolbox/versionupdater/ico/icons8-code-file-48.png")).getImage());
		VersionUpdater.this.getRootPane().setDefaultButton(updatebtn);
		df = DateTimeFormatter.ofPattern("yyyy MM dd");
		date = LocalDate.parse(LocalDate.now().format(df), df);
		datefield.setText(date.toString());

		updatebtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (versionfield.getText() == null || versionfield.getText().length() == 0) {
						vu.errorMessage("Invalid version number.", "Error");
					}
					else if (datefield.getText() == null || datefield.getText().length() == 0) {
						vu.errorMessage("Invalid date.", "Error");
					}
					else {
						new UpdateWorker(vu);
					}
				}
			});

		extractbtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new ExtractWorker(vu);
				}
			});

		clrbtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					queue = null;
					previousversion = null;
					versionfield.setText("");
					updatebtn.setEnabled(false);
					extractbtn.setEnabled(false);
					clrbtn.setEnabled(false);
					statustext.setText("File queue cleared.");
				}
			});

		addmorebtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					File[]files = addFilesDialog();
					if(files != null && files.length != 0) {
						updatebtn.setEnabled(true);
						extractbtn.setEnabled(true);
						clrbtn.setEnabled(true);
						if (queue == null) {
							queue = files;
							filesadded = files.length;
						}
						else {
							File[] tmp = new File[queue.length + files.length];
							for (int i = 0; i < queue.length; i++) {
								tmp[i] = queue[i];
							}
							for (int i = 0; i < files.length; i++) {
								tmp[i + queue.length] = files[i];
							}
							queue = tmp;
							filesadded = tmp.length;
						}
						updateFileCount(filesadded);
						statustext.setText("Added "+filesadded+" files to the update queue ("+filecount+" files in queue).");
					}
				}
			});

	}

    private void initComponents() {

        container = new javax.swing.JPanel();
        infopanel = new javax.swing.JPanel();
        versionfield = new javax.swing.JTextField();
        versionlabel = new javax.swing.JLabel();
        updatebtn = new javax.swing.JButton();
        datefield = new javax.swing.JTextField();
        datelabel = new javax.swing.JLabel();
        extractbtn = new javax.swing.JButton();
        addmorebtn = new javax.swing.JButton();
        clrbtn = new javax.swing.JButton();
        progresspanel = new javax.swing.JPanel();
        progressbar = new javax.swing.JProgressBar();
        statustext = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Source Code Version Updater");
        setMinimumSize(new java.awt.Dimension(571, 180));
        setName("");
        setResizable(false);

        infopanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Version"));

        versionfield.setToolTipText("Version number of the new update.");
        versionfield.setMargin(new java.awt.Insets(0, 5, 0, 0));

        versionlabel.setFont(new java.awt.Font("Dialog", 0, 12));
        versionlabel.setText("Version Number:");

        updatebtn.setText("Update Version");
        updatebtn.setToolTipText("Update the version of the files in the update queue.");
        updatebtn.setEnabled(false);
        updatebtn.setFocusPainted(false);

        datefield.setToolTipText("Date the package was last updated.");
        datefield.setMargin(new java.awt.Insets(0, 5, 0, 0));
        datefield.setMinimumSize(new java.awt.Dimension(70, 20));
        datefield.setPreferredSize(new java.awt.Dimension(70, 20));

        datelabel.setFont(new java.awt.Font("Dialog", 0, 12));
        datelabel.setText("Updated On:");

        extractbtn.setText("Extract Version");
        extractbtn.setToolTipText("Extract current version from the files in the update queue.");
        extractbtn.setEnabled(false);
        extractbtn.setFocusPainted(false);
        extractbtn.setFocusable(false);

        addmorebtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbox/versionupdater/ico/action_add.png")));
        addmorebtn.setToolTipText("Add more files.");
        addmorebtn.setFocusPainted(false);
        addmorebtn.setFocusable(false);

        clrbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbox/versionupdater/ico/action_delete.png")));
        clrbtn.setToolTipText("Clear extracted files.");
        clrbtn.setEnabled(false);
        clrbtn.setFocusPainted(false);
        clrbtn.setFocusable(false);

        javax.swing.GroupLayout infopanelLayout = new javax.swing.GroupLayout(infopanel);
        infopanel.setLayout(infopanelLayout);
        infopanelLayout.setHorizontalGroup(
            infopanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infopanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(infopanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(versionlabel)
                    .addComponent(datelabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(infopanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(datefield, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                    .addComponent(versionfield))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(infopanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(updatebtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(extractbtn, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(infopanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(clrbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addmorebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        infopanelLayout.setVerticalGroup(
            infopanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infopanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(infopanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(updatebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(versionfield)
                    .addComponent(versionlabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addmorebtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(infopanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(datelabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(datefield, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(extractbtn, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(clrbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(14, 14, 14))
        );

        versionfield.getAccessibleContext().setAccessibleName("Version Field");
        datefield.getAccessibleContext().setAccessibleName("Date Field");
        addmorebtn.getAccessibleContext().setAccessibleName("Add Files");
        clrbtn.getAccessibleContext().setAccessibleName("Clear Files");

        progresspanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Progress"));

        statustext.setFont(new java.awt.Font("Dialog", 0, 10));
        statustext.setText("No files selected.");

        javax.swing.GroupLayout progresspanelLayout = new javax.swing.GroupLayout(progresspanel);
        progresspanel.setLayout(progresspanelLayout);
        progresspanelLayout.setHorizontalGroup(
            progresspanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(progresspanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(progresspanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(statustext, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(progressbar, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE))
                .addContainerGap())
        );
        progresspanelLayout.setVerticalGroup(
            progresspanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(progresspanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(progressbar, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(statustext)
                .addContainerGap())
        );

        javax.swing.GroupLayout containerLayout = new javax.swing.GroupLayout(container);
        container.setLayout(containerLayout);
        containerLayout.setHorizontalGroup(
            containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(infopanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(progresspanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        containerLayout.setVerticalGroup(
            containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(infopanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(progresspanel, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        infopanel.getAccessibleContext().setAccessibleName("Version Panel");
        progresspanel.getAccessibleContext().setAccessibleName("Progress Panel");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }

	public static void main(String args[]) {

		java.awt.EventQueue.invokeLater(new Runnable() {
				public void run() {
					new VersionUpdater().setVisible(true);
				}
			});
	}

    private javax.swing.JButton addmorebtn;
    private javax.swing.JButton clrbtn;
    private javax.swing.JPanel container;
    private javax.swing.JTextField datefield;
    private javax.swing.JLabel datelabel;
    private javax.swing.JButton extractbtn;
    private javax.swing.JPanel infopanel;
    private javax.swing.JProgressBar progressbar;
    private javax.swing.JPanel progresspanel;
    private javax.swing.JLabel statustext;
    private javax.swing.JButton updatebtn;
    private javax.swing.JTextField versionfield;
    private javax.swing.JLabel versionlabel;
		private final VersionUpdater vu = this;
    private String previousversion = null;
		private final DateTimeFormatter df;
		private LocalDate date;
		private File[] queue = null;
		private int filecount = 0;
		private int filesadded = 0;
		private int filesdone = 0;

	protected JFrame getFrame() {
		return VersionUpdater.this;
	}

	protected String getPreviousVersion() {
		return previousversion;
	}

	protected void setPreviousVersion(String pv) {
		previousversion = pv;
	}

	protected String getVersionField() {
		return versionfield.getText();
	}

	protected void setVersionField(String version) {
		versionfield.setText(version);
	}

	protected String getDateField() {
		return datefield.getText();
	}

	protected void setStatusText(String text) {
		statustext.setText(text);
	}

	protected File[] getQueue() {
		return queue;
	}

	protected int getFileCount() {
		return filecount;
	}

	protected void startOp() {
		resetProgress();
		statustext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbox/versionupdater/ico/time.png")));
		extractbtn.setEnabled(false);
		updatebtn.setEnabled(false);
		addmorebtn.setEnabled(false);
		clrbtn.setEnabled(false);
	}

	protected void endOp() {
		resetProgress();
		statustext.setIcon(null);
		extractbtn.setEnabled(true);
		updatebtn.setEnabled(true);
		addmorebtn.setEnabled(true);
		clrbtn.setEnabled(true);
	}

	protected void getFileCount(File[] files) {
		progressbar.setIndeterminate(true);
		getFilesRecursive(files);
		progressbar.setIndeterminate(false);
	}

	private void updateFileCount(int added) {
		filecount = filecount + added;
	}

	private void getFilesRecursive(File[] files) {
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				getFilesRecursive(files[i].listFiles());
			}
			else {
				filecount++;
			}
		}
	}

	protected void fileDone() {
		filesdone++;
		updateProgress();
	}

	private void updateProgress() {
		float count = (float) filecount;
		float done = (float) filesdone;
		float progress = ( done / count ) * 100;
		progressbar.setValue( (int) progress);
	}

	private void resetProgress() {
		filecount = 0;
		filesdone = 0;
		progressbar.setValue(0);
		statustext.setIcon(null);
	}

	protected void softResetProgress() {
		filesdone = 0;
		progressbar.setValue(0);
	}

	protected void errorMessage(String msg, String title) {
		JOptionPane.showMessageDialog(getFrame(), msg, title, JOptionPane.ERROR_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/toolbox/versionupdater/ico/icons8-high-risk-50.png")));
	}

	protected void warnMessage(String msg, String title) {
		JOptionPane.showMessageDialog(getFrame(), msg, title, JOptionPane.WARNING_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/toolbox/versionupdater/ico/icons8-medium-risk-50.png")));
	}

	protected void successMessage(String msg, String title) {
		JOptionPane.showMessageDialog(getFrame(), msg, title, JOptionPane.INFORMATION_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/toolbox/versionupdater/ico/icons8-checked-50.png")));
	}

	protected File[] addFilesDialog() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setMultiSelectionEnabled(true);
		int choice = chooser.showOpenDialog(getFrame());
		File[] files = chooser.getSelectedFiles();
		if (choice == JFileChooser.OPEN_DIALOG) {
			return files;
		}
		else {
			return null;
		}
	}

	protected int promptDialog(String msg, String title) {
		return JOptionPane.showConfirmDialog(getFrame(), msg, title, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/toolbox/versionupdater/ico/icons8-medium-risk-50.png")));
	}

	protected boolean getPreviousVersion(File[] files) {
		String tmpversion = null;
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				getPreviousVersion(files[i].listFiles());
			}
			else {
				String[] fileContents = null;
				fileContents = FileOperations.load(vu, files[i]);
				if (fileContents == null) {
					vu.errorMessage("Previous version check was aborted due to an error.", "Error");
					return false;
				}
				for (int n = 0; n < fileContents.length; n++) {
					int index = fileContents[n].indexOf("@version");
					if (index != -1) {
						tmpversion = fileContents[n].substring(index + 9, fileContents[n].length());
						index = tmpversion.indexOf(" ");
						if (index == -1) {
							index = tmpversion.indexOf(System.lineSeparator());
							if (index != -1) {
								tmpversion = tmpversion.substring(0, index);
							}
						}
						else {
							tmpversion = tmpversion.substring(0, index);
						}
						break;
					}
				}
				if (tmpversion != null) {
					if (previousversion != null) {
						String[] comparenew = tmpversion.split("\\.");
						String[] compareold = previousversion.split("\\.");
						boolean comparedone = false;
						boolean morerecent = false;
						for(int n = 0; n < comparenew.length && n < compareold.length; n++) {
							if (Integer.parseInt(comparenew[n]) == Integer.parseInt(compareold[n])) {
								continue;
							}
							if (Integer.parseInt(comparenew[n]) > Integer.parseInt(compareold[n])) {
								comparedone = true;
								morerecent = true;
								break;
							}
							else {
								comparedone = true;
								morerecent = false;
							}
						}
						if (comparedone == false) {
							if (comparenew.length > compareold.length) {
								for (int n = compareold.length; n < comparenew.length; n++) {
									if (Integer.parseInt(comparenew[n]) != 0) {
										previousversion = tmpversion;
										break;
									}
								}
							}
						}
						else {
							if(morerecent) {
								previousversion = tmpversion;
							}
						}
					}
					else {
						previousversion = tmpversion;
					}
				}
			}
			fileDone();
		}
		if (previousversion != null) {
			return true;
		}
		else {
			return false;
		}
	}

	protected boolean recursiveChange(File[] files, String version, String date) {
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				if (!recursiveChange(files[i].listFiles(), version, date)) {
					return false;
				}
			}
			else {
				int index = files[i].getName().lastIndexOf(".");
				String[] c = FileOperations.load(vu, files[i]);
				if (c != null) {
					if (index != -1) {
						String ext = files[i].getName().substring(index);
						if (ext.equals(".java")) {
							c = UpdateJava.replaceJava(c, version, date);
							if (!FileOperations.writeContents(vu, files[i], c)) {
								return false;
							}
						}
						else if (ext.equals(".xml")) {
							c = UpdateXml.replaceXml(c, version, date);
							if (!FileOperations.writeContents(vu, files[i], c)) {
								return false;
							}
						}
					}
					fileDone();
				}
				else {
					vu.errorMessage("Version update was aborted due to an error.", "Abort");
					return false;
				}
			}
		}
		return true;
	}

}
