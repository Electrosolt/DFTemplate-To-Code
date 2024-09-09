package dftemplatetocode.parsers.chestitems.impl;

import org.json.JSONObject;
import dftemplatetocode.parsers.chestitems.ChestParser;

public class PotionParser extends ChestParser {

    // Pot{Levitation 0 for 1:00}
    @Override
    public String getValue(JSONObject itemData) {
        String type = itemData.getString("pot");
        int ticks = itemData.getInt("dur");
        int seconds = ticks / 20;
        int level = itemData.getInt("amp") + 1;

        String duration;
        if (ticks >= 1_000_000) {
            duration = "∞";
        } else if (ticks < 20) {
            duration = ticks + "t";
        } else if (ticks > 72000){ // 1 hour
            duration = String.format("%02d:%02d:%02d", seconds / 3600, (seconds / 60) % 60, seconds % 60);
        } else {
            duration = String.format("%02d:%02d", (seconds / 60) % 60, seconds % 60);
        }
        return "Pot{" + type + " " + level + " for " + duration + "}";
    }
}
