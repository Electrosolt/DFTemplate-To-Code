package dftemplatetocode.parsers.chestitems.impl;

import org.json.JSONObject;
import dftemplatetocode.parsers.chestitems.ChestParser;

public class GameValueParser extends ChestParser {

    // GV(Timestamp), GV(Current Health)@Selection
    @Override
    public String getValue(JSONObject itemData) {
        String name = itemData.getString("type");
        String target = itemData.getString("target");

        String toReturn = "GV(" + name + ")";

        if (!target.equals("Default")) {
            return toReturn + "@" + target;
        }

        return toReturn;

    }
}
