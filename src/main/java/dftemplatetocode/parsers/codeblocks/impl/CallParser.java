package dftemplatetocode.parsers.codeblocks.impl;

import org.json.JSONObject;
import dftemplatetocode.parsers.CodeLine;
import dftemplatetocode.parsers.codeblocks.CodeblockParser;

public class CallParser extends CodeblockParser {

    public CallParser(String name) {
        super(name);
    }

    @Override
    public CodeLine getCodeLine(JSONObject codeblock) {
        return new CodeLine(name, codeblock)
                .parseDataAsAction()
                .parseChest()
                .addSemicolon();
    }
}
