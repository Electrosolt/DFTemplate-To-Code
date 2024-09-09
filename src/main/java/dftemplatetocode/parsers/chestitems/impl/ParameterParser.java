package dftemplatetocode.parsers.chestitems.impl;

import org.json.JSONObject;
import dftemplatetocode.parsers.chestitems.ChestParser;
import dftemplatetocode.parsers.chestitems.ChestParsers;

public class ParameterParser extends ChestParser {

    // str(s)*: varName = 'hello', or num: coins
    @Override
    public String getValue(JSONObject itemData) {
        String name = itemData.getString("name");
        String rawType = itemData.getString("type");
        boolean isPlural = itemData.getBoolean("plural");
        boolean isOptional = itemData.getBoolean("optional");
        JSONObject defaultValue = itemData.optJSONObject("default_value", null);

        // this does e.g. "comp" -> "txt", or "part" -> "pfx", etc.
        String goodType;
        try {
            goodType = ChestParsers.getChestParserStruct(rawType).getID();
        } catch (IllegalArgumentException e) {
            goodType = rawType;
        }


        String parsedDefault = null;
        if (defaultValue != null) {
            parsedDefault = ChestParsers.parse(defaultValue).get();
        }

        StringBuilder sb = new StringBuilder(goodType);
        if (isPlural) {
            sb.append("(s)");
        }
        if (isOptional) {
            sb.append("*");
        }

        sb.append(": ").append(name);

        if (parsedDefault != null) {
            sb.append(" = ").append(parsedDefault);
        }

        return sb.toString();
    }
}
