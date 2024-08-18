package parsers.chestitems.impl;

import org.json.JSONObject;
import parsers.chestitems.ChestParser;

public class StringParser extends ChestParser {

    @Override
    public String getValue(JSONObject itemData) {
        return "'" + itemData.getString("name") + "'";
    }
}
