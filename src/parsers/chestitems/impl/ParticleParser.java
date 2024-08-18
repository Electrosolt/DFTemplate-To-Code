package parsers.chestitems.impl;

import org.json.JSONObject;
import parsers.chestitems.ChestParser;

public class ParticleParser extends ChestParser {

    // PFX{5xCloud(0x0)|x=1|y=2|z=3|motionVariation=100}
    @Override
    public String getValue(JSONObject itemData) {

        String name = itemData.getString("particle");
        JSONObject cluster = itemData.getJSONObject("cluster");
        double count = cluster.getDouble("amount");
        double spreadH = cluster.getDouble("horizontal");
        double spreadV = cluster.getDouble("vertical");

        JSONObject additionalData = itemData.getJSONObject("data");

        StringBuilder sb = new StringBuilder("PFX{");
        if (count != 1) {
            sb.append(count).append("x");
        }

        sb.append(name);

        if (spreadH != 0 || spreadV != 0) {
            sb.append("(").append(spreadH).append("x").append(spreadV).append(")");
        }

        for(String key : additionalData.keySet()) {
            Object val = additionalData.get(key);
            if (val instanceof Number num) {
                sb.append("|").append(key).append("=").append(roundVal(num));
            } else {
                sb.append("|").append(key).append("=").append(val);
            }
        }

        return sb.append("}").toString();
    }
}
