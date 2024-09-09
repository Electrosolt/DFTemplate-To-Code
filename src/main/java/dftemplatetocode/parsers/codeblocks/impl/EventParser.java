package dftemplatetocode.parsers.codeblocks.impl;

import org.json.JSONObject;
import dftemplatetocode.parsers.CodeIndent;
import dftemplatetocode.parsers.CodeLine;
import dftemplatetocode.parsers.codeblocks.CodeblockParser;

public class EventParser extends CodeblockParser {
    public EventParser(String name) {
        super(name);
    }

    @Override
    public CodeLine getCodeLine(JSONObject codeblock) {
        return new CodeLine(name, codeblock)
                .parseAttribute()
                .parseAction()
                .increaseIndent();
    }
}
