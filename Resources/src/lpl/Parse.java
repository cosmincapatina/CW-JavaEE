package lpl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import lpl.ast.Program;
import lpl.parser.LPLParser;
import lpl.parser.ParseException;
import lpl.util.TreePrint;

public class Parse {

    public static void main(String[] args) throws FileNotFoundException, ParseException, IOException {
        String sourceFileName = args[0];
        parse(sourceFileName);
        System.out.println("done.");
    }
    
    public static lpl.ast.Program parse(String sourceFileName) throws FileNotFoundException, ParseException, IOException {
        progressMessage("Parsing " + sourceFileName);
        try (InputStream in = new FileInputStream(sourceFileName)) {
            Program lplProgram = new LPLParser(in, "UTF-8").Program();
            String htmlFileName = basePath(sourceFileName) + ".html";
            progressMessage("\nRendering AST as html in file " + htmlFileName);
            TreePrint.makeHtml(htmlFileName, lplProgram);
            return lplProgram;
        }
    }

    /**
     * String representation of a given path string minus its extension.
     *
     * @param pathString the original path
     * @return the path minus its final file name extension (or the original
     * path if it doesn't end in a file name extension)
     */
    public static String basePath(String pathString) {
        Path path = Paths.get(pathString);
        String f = path.getFileName().toString();
        f = f.split("\\.")[0];
        Path parent = path.getParent();
        if (parent != null) {
            String p = parent.toString();
            return (Paths.get(p, f)).toString();
        } else {
            return f;
        }
    }
    
    public static void progressMessage(String msg) {
        System.out.print(msg + " ...");
        System.out.flush();
    }

}
