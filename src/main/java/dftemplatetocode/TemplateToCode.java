package dftemplatetocode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import dftemplatetocode.parsers.CodeLine;
import dftemplatetocode.parsers.codeblocks.CodeblockParsers;
import dftemplatetocode.util.ActionDumpUtil;
import dftemplatetocode.util.GZipUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class TemplateToCode {

    public static final String GEN_DIR = "gendir";
    public static BufferedWriter FULL_FILE_WRITER;
    public static BufferedWriter RAW_DATA_WRITER;

    public static void main(String[] args) throws IOException {

        ActionDumpUtil.loadActionDump();
        FULL_FILE_WRITER = new BufferedWriter(new FileWriter(GEN_DIR + "/full_code.df"));
        RAW_DATA_WRITER = new BufferedWriter(new FileWriter(GEN_DIR + "/raw_code.json"));

        List<JSONObject> shulkers = getShulkerListFromFile();
        for(var shulker : shulkers) {
            List<JSONArray> shulkerTemplates = getTemplateArrayFromShulker(shulker);
            for(var codeblocks : shulkerTemplates) {
                writeCode(codeblocks);
                writeRawData(codeblocks);
            }
        }

        FULL_FILE_WRITER.close();
        RAW_DATA_WRITER.close();


    }


    public static List<JSONObject> getShulkerListFromFile() {

        List<JSONObject> lines = new ArrayList<>();

        try (var reader = new BufferedReader(new FileReader("templatedata.txt"))) {

            String line;
            int lineNumber = 0;
            while((line = reader.readLine()) != null) {
                lineNumber++;
                try {
                    lines.add(new JSONObject(line));
                } catch (JSONException exception) {
                    System.out.println("Could not parse json at line " + lineNumber + ".");
                }

            }

        } catch (Exception ignored) {
            System.out.println("Could not set up reader");
        }

        return lines;
    }

    public static List<JSONArray> getTemplateArrayFromShulker(JSONObject shulkerData) {
        JSONArray shulkerContents = getShulkerContents(shulkerData);
        JSONArray templateArray;

        if (shulkerContents.getJSONObject(0).getString("id").equals("minecraft:white_shulker_box")) {
            templateArray = new JSONArray();
            for (int i = 0; i < shulkerContents.length(); i++) {
                JSONObject subShulker = shulkerContents.getJSONObject(i);
                templateArray.putAll(getShulkerContents(subShulker));
            }
        } else {
            templateArray = shulkerContents;
        }

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

    public static JSONArray getShulkerContents(JSONObject shulker) {
        return shulker.getJSONObject("tag").getJSONObject("BlockEntityTag").getJSONArray("Items");
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
            String toWrite = codeLine.toString().indent(4 * indentLevel).stripTrailing() + "\n";
            fileWriter.write(toWrite);
            FULL_FILE_WRITER.write(toWrite);
            if (codeLine.isIncreasingIndent()) {
                indentLevel++;
            }
        }

        fileWriter.close();
        FULL_FILE_WRITER.write("\n--------------------------\n\n");
    }

    public static void writeRawData(JSONArray codeblocks) throws IOException {
        RAW_DATA_WRITER.write(codeblocks.toString(4));
        RAW_DATA_WRITER.write("\n--------------------------\n\n");
    }


}