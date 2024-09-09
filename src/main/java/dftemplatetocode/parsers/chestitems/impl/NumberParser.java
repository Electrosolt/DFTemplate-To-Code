package dftemplatetocode.parsers.chestitems.impl;

import org.json.JSONObject;
import dftemplatetocode.parsers.chestitems.ChestParser;

public class NumberParser extends ChestParser {
    @Override
    public String getValue(JSONObject itemData) {
        return itemData.getString("name");
    }
}
