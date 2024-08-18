package parsers.codeblocks.impl;

import org.json.JSONObject;
import parsers.CodeLine;
import parsers.codeblocks.CodeblockParser;

public class BracketParser extends CodeblockParser {

    private boolean isOpen;

    public BracketParser(String name, boolean isOpen) {
        super(name);
        this.isOpen = isOpen;
    }

    @Override
    public CodeLine getCodeLine(JSONObject codeblock) {
        CodeLine line = new CodeLine(name, codeblock);
        return isOpen? line.increaseIndent() : line.decreaseIndent();
    }



}
