package parsers.chestitems.impl;

import org.json.JSONObject;
import parsers.chestitems.ChestParser;

import java.util.regex.Pattern;

// To properly parse items in the future, use https://github.com/TheNullicorn/Nedit, a dependency-free NBT library.
public class ItemParser extends ChestParser {

    // item:grass_block
    @Override
    public String getValue(JSONObject item) {
        String nbt = item.getString("item");
        Pattern regex = Pattern.compile("\"minecraft:(?<id>[^\"]+)\"");
        var matcher = regex.matcher(nbt);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Could not match item ID");
        }
        String ID = matcher.group("id");
        return "item:" + ID;
    }
}
