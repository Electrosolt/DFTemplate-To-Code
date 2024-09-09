package dftemplatetocode.parsers.chestitems.impl;

import org.json.JSONObject;
import dftemplatetocode.parsers.chestitems.ChestParser;

public class VariableParser extends ChestParser {

    // local:`%default coins`
    @Override
    public String getValue(JSONObject itemData) {
        String name = itemData.getString("name");
        String scope = switch (itemData.getString("scope")) {
            case "unsaved" -> "game";
            case "saved" -> "save";
            case "local" -> "local";
            case "line" -> "line";

            default -> throw new IllegalStateException("Unexpected variable scope: " + itemData.getString("scope"));
        };

        return scope + ":`" + name + "`";

    }
}
