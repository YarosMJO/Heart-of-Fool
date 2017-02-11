package GameState;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class User {

    final String myJarPath = heartoffool.HeartOfFool.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    final String dirPath = new File(myJarPath).getParent();
    final String settingsFilename = System.getenv("APPDATA") + File.separator
            + "Heart Of Fool" + File.separator + "Saved settings";
    final Properties props = new Properties();

    public void save() {

        final String fileName = System.getenv("APPDATA")
                + File.separator + "Heart Of Fool";
        File file = new File(fileName);
        if (!(new File(fileName)).exists()) {
            file.mkdir();
        }

        try (FileOutputStream output = new FileOutputStream(settingsFilename)) {
            props.store(output, "Saved settings");
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
    }

    public void load() {
        try (FileInputStream input = new FileInputStream(settingsFilename)) {
            props.load(input);
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }

    }
}
