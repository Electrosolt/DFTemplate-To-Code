package parsers.codeblocks;

import org.json.JSONObject;
import parsers.CodeLine;

public class CodeblockParser {

    public String name;

    public CodeblockParser(String name) {
        this.name = name;
    }

    public CodeLine getCodeLine(JSONObject codeblock) {
        return new CodeLine(name, codeblock);
    }


}
