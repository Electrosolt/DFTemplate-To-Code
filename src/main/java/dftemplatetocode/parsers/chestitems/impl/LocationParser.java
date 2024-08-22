package dftemplatetocode.parsers.chestitems.impl;

import org.json.JSONObject;
import dftemplatetocode.parsers.chestitems.ChestParser;

import java.text.DecimalFormat;


public class LocationParser extends ChestParser {

    @Override
    public String getValue(JSONObject itemData) {
        JSONObject loc = itemData.getJSONObject("loc");
        double x = loc.getDouble("x");
        double y = loc.getDouble("y");
        double z = loc.getDouble("z");
        double pitch = loc.getDouble("pitch");
        double yaw = loc.getDouble("yaw");

        if (pitch == 0 && yaw == 0) {

            return String.format("[%s,%s,%s]", roundVal(x), roundVal(y), roundVal(z));
        } else {
            return String.format("[%s,%s,%s,%s,%s]", roundVal(x), roundVal(y), roundVal(z), roundVal(pitch), roundVal(yaw));
        }

    }
}
