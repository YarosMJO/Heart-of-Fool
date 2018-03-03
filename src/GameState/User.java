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

    public void checkDefSettings(){
        File file = new File(settingsFilename);
        if (!file.exists()) {
            props.setProperty("hero_type","Drago");
            props.setProperty("hero_health", "5");
            props.setProperty("hero_position_x", "100");
            props.setProperty("hero_position_y", "200");
            props.setProperty("game_state", "Level1State");
            props.setProperty("tilemap_position_x", "0.0");
            props.setProperty("tilemap_position_y", "0.0");
            save();
        }
    }
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
        checkDefSettings();
        try (FileInputStream input = new FileInputStream(settingsFilename)) {
            props.load(input);
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }

    }
}
