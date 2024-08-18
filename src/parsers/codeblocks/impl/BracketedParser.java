package parsers.codeblocks.impl;

import org.json.JSONObject;
import parsers.CodeLine;
import parsers.codeblocks.CodeblockParser;

public class BracketedParser extends CodeblockParser {
    public BracketedParser(String name) {
        super(name);
    }

    @Override
    public CodeLine getCodeLine(JSONObject codeblock) {
        return new CodeLine(name, codeblock)
                .parseAll();
    }
}
