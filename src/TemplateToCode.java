import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import parsers.CodeLine;
import parsers.codeblocks.CodeblockParsers;
import util.ActionDumpUtil;
import util.GZipUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class TemplateToCode {

    public static final String GEN_DIR = "gendir";

    public static void main(String[] args) throws IOException {

        ActionDumpUtil.loadActionDump();

        List<JSONObject> shulkers = getShulkerListFromFile();
        for(var shulker : shulkers) {
            List<JSONArray> shulkerTemplates = getTemplateArrayFromShulker(shulker);
            for(var codeblocks : shulkerTemplates) {
                writeCode(codeblocks);
            }
        }


    }


    public static List<JSONObject> getShulkerListFromFile() {

        List<JSONObject> lines = new ArrayList<>();

        try (var reader = new BufferedReader(new FileReader("templatedata.txt"))) {

            String line;

            while((line = reader.readLine()) != null) {
                try {
                    lines.add(new JSONObject(line));
                } catch (JSONException exception) {
                    System.out.println("Could not parse json.");
                }

            }

        } catch (Exception ignored) {
            System.out.println("Could not set up reader");
        }

        return lines;
    }

    public static List<JSONArray> getTemplateArrayFromShulker(JSONObject shulkerData) {
        JSONArray templateArray = shulkerData.getJSONObject("tag").getJSONObject("BlockEntityTag").getJSONArray("Items");

        List<JSONArray> codeData = new ArrayList<>();

        for (int i = 0; i < templateArray.length(); i++) {
            String rawTemplateData = templateArray
                    .getJSONObject(i)
                    .getJSONObject("tag")
                    .getJSONObject("PublicBukkitValues")
                    .getString("hypercube:codetemplatedata");

            var templateData = new JSONObject(rawTemplateData.replace("\\", ""));

            var compressedTemplateData = templateData.getString("code");
            //System.out.println(compressedTemplateData);
            String decompressedTemplateData;
            try {
                 decompressedTemplateData = GZipUtils.decompress(Base64.getDecoder().decode(compressedTemplateData));
            } catch (IOException e) {
                System.out.println("Failed to decompress");
                continue;
            }

            //System.out.println(decompressedTemplateData);

            JSONObject code = new JSONObject(decompressedTemplateData);
            codeData.add(code.getJSONArray("blocks"));

        }

        return codeData;
    }

    public static CodeLine getCodeline(JSONObject codeblock) {
        String blockType = codeblock.getString("id");

        String blockName = switch (blockType) {
            case "block" -> codeblock.getString("block");
            case "bracket" -> codeblock.getString("direct") + "_bracket";
            default -> throw new IllegalStateException("Unexpected block type: " + blockType);
        };

        var parser = CodeblockParsers.getCodeblockParser(blockName);
        return parser.getCodeLine(codeblock);

    }

    public static void writeCode(JSONArray codeblocks) throws IOException {
        int indentLevel = 0;

        CodeLine header = getCodeline(codeblocks.getJSONObject(0)).setHeader();

        String directory = header.getDirectory() == null? GEN_DIR : GEN_DIR + "/" + header.getDirectory();
        Files.createDirectories(Paths.get(directory));
        String filePath = directory + "/" + header.getFileName();

        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filePath));

        for (int i = 0; i < codeblocks.length(); i++) {
            JSONObject codeblock = codeblocks.getJSONObject(i);
            CodeLine codeLine = getCodeline(codeblock);
            if (i == 0) codeLine.setHeader();
            if (codeLine.isDecreasingIndent()) {
                indentLevel--;
            }
            fileWriter.write(codeLine.toString().indent(4 * indentLevel).stripTrailing() + "\n");
            if (codeLine.isIncreasingIndent()) {
                indentLevel++;
            }
        }

        fileWriter.close();
    }


}