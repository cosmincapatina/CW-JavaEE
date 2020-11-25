package lpl;

import ir.ast.IRProgram;
import ir.ast.util.IRPrettyPrinter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import lpl.ast.Program;
import lpl.parser.ParseException;

public class Compile {

    public static void main(String[] args) throws FileNotFoundException, ParseException, IOException {
        String sourceFileName = args[0];
        Program lplProgram = Parse.parse(sourceFileName);
        Parse.progressMessage("\nCompiling");
        IRProgram irProgram = lplProgram.compile();
        String targetFileName = Parse.basePath(sourceFileName) + ".ir";
        Parse.progressMessage("\nWriting IR code to " + targetFileName);
        try (PrintStream out = new PrintStream(new FileOutputStream(targetFileName))) {
            IRPrettyPrinter.prettyPrint(out, irProgram);
        }
        System.out.println("done.");
    }

}
