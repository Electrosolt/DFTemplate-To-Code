package parsers.chestitems.impl;

import org.json.JSONObject;
import parsers.chestitems.ChestParser;

public class SoundParser extends ChestParser {

    // "Sound{Bamboo Break#place1|p=1|vol=2}"
    @Override
    public String getValue(JSONObject itemData) {

        double pitch = itemData.getDouble("pitch");
        double volume = itemData.getDouble("vol");
        String name = itemData.getString("sound");
        String variant = itemData.optString("variant", null);

        StringBuilder sb = new StringBuilder("Sound{").append(name);
        if (variant != null) {
            sb.append("#").append(variant);
        }
        if (pitch != 1) {
            sb.append("|p=").append(roundVal(pitch));
        }
        if (volume != 2) {
            sb.append("|vol=").append(roundVal(volume));
        }
        return sb.append("}").toString();
    }
}
