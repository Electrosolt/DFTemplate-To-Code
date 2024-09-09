package dftemplatetocode.parsers.chestitems.impl;

import org.json.JSONObject;
import dftemplatetocode.parsers.chestitems.ChestParser;

import java.text.DecimalFormat;


public class VectorParser extends ChestParser {



    @Override
    public String getValue(JSONObject itemData) {
        double x = itemData.getDouble("x");
        double y = itemData.getDouble("y");
        double z = itemData.getDouble("z");

        return String.format("〈%s,%s,%s〉", roundVal(x), roundVal(y), roundVal(z));

    }
}
