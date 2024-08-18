package parsers.chestitems;

import org.json.JSONObject;

import java.text.DecimalFormat;

public abstract class ChestParser {

    private static final DecimalFormat f = new DecimalFormat("0.###");

    public abstract String getValue(JSONObject item);

    protected String roundVal(double v) {
        return f.format(v);
    }

    protected String roundVal(Number num) {
        return f.format(num.doubleValue());
    }

}
