package versionupdater;

public class UpdateJava {

	protected static String[] replaceJava(String[] contents, String version, String date) {
		String[] newcontents = contents;
		boolean v = false;
		boolean d = false;
		int versionindex = 0;
		int dateindex = 0;
		for (int i = 0; i < newcontents.length; i++) {
			if (!v) {
				int index = newcontents[i].indexOf("@version");
				if (index != -1) {
					String newline = newcontents[i].substring(0, index)+"@version "+version;
					newcontents[i] = newline;
					v = true;
					versionindex = i;
					if (d) {
						break;
					}
				}
			}
			if (!d) {
				int index = newcontents[i].indexOf("@updated");
				if (index != -1) {
					String newline = newcontents[i].substring(0, index)+"@updated "+date;
					newcontents[i] = newline;
					dateindex = i;
					d = true;
					if (v) {
						break;
					}
				}
			}
		}
		if (!v && !d) {
			String[] fixcontents = new String[newcontents.length + 4];
			fixcontents[0] = "/**";
			fixcontents[1] = " * @version "+version;
			fixcontents[2] = " * @updated "+date;
			fixcontents[3] = " */";
			for (int i = 0; i < newcontents.length; i++) {
				fixcontents[i+4] = newcontents[i];
			}
			newcontents = fixcontents;
		}
		else if (v && !d) {
			String[] fixcontents = new String[newcontents.length + 1];
			for (int i = 0; i < fixcontents.length; i++) {
				if (d) {
					fixcontents[i] = newcontents[i - 1];
				}
				else {
					if (i == versionindex + 1) {
						fixcontents[i] = " * @updated "+date;
						d = true;
					}
					else {
						fixcontents[i] = newcontents[i];
					}
				}
			}
			newcontents = fixcontents;
		}
		else if (!v && d) {
			String[] fixcontents = new String[newcontents.length + 1];
			for (int i = 0; i < fixcontents.length; i++) {
				if (v) {
					fixcontents[i] = newcontents[i - 1];
				}
				else {
					if (i == dateindex) {
						fixcontents[i] = " * @version "+version;
						v = true;
					}
					else {
						fixcontents[i] = newcontents[i];
					}
				}
			}
			newcontents = fixcontents;
		}
		return newcontents;
	}

}
