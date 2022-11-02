package versionupdater;

public class UpdateXml {

	protected static String[] replaceXml(String[] contents, String version, String date) {
		String[] newcontents = contents;
		for (int i = 0; i < newcontents.length; i++) {
			int index = newcontents[i].indexOf("<property name=\"version\" value=\"");
			if (index != -1) {
				String newline = newcontents[i].substring(0, index)+"<property name=\"version\" value=\""+version+"\"/>";
				newcontents[i] = newline;
				break;
			}
		}
		return newcontents;
	}

}
