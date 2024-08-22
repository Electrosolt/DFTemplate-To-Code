package dftemplatetocode.parsers.chestitems.impl;

import org.json.JSONObject;
import dftemplatetocode.parsers.chestitems.ChestParser;
import dftemplatetocode.util.ActionDumpUtil;
import dftemplatetocode.util.StringUtils;

public class TagParser extends ChestParser {
    @Override
    public String getValue(JSONObject item) {
        String tagName = item.getString("tag");
        String setTo = item.getString("option");
        String codeBlock = item.getString("block");
        String action = item.getString("action");

        String tagDefault = ActionDumpUtil.getTagDefault(ActionDumpUtil.mapToDumpCodeblock(codeBlock), action, tagName);
        if (tagDefault == null || tagDefault.equals(setTo)) {
            return null;
        }

        tagName = StringUtils.toTitleCase(tagName, "").replace("'", "");
        setTo = StringUtils.toTitleCase(setTo, "").replace("'", "");
        return tagName + "=" + setTo;
    }
}
