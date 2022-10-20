package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import ast.NodeProgram;
import eccezioni.LexicalException;
import eccezioni.SyntacticException;
import parser.Parser;
import scanner.Scanner;
import symbolTable.SymbolTable;
import visitor.CodeGeneratorVisitor;
import visitor.TypeCheckingVisitor;

class TestCodeGenerator {

    public static NodeProgram runParser(String filePath ) throws IOException, SyntacticException, LexicalException  {
        return new Parser(new Scanner(filePath)).parse();
    }

    @Test
    public void testCodeGenerator() throws IOException, SyntacticException, LexicalException {
        SymbolTable.init();
        String path = "src/test/codeGeneratorVisitor/Programma_Test.txt";
        String pathCorretto = "src/test/codeGeneratorVisitor/Programma_Testing_Corretto.txt";
        NodeProgram prg = runParser(path);
        TypeCheckingVisitor tcv = new TypeCheckingVisitor();
        CodeGeneratorVisitor cgv = new CodeGeneratorVisitor();
        tcv.visit(prg);
        assertTrue(tcv.getLogString().isEmpty());
        cgv.visit(prg);
        File output = new File (pathCorretto);
        FileWriter fw = new FileWriter(output, false);
        fw.write(cgv.getCodice().toString());
        fw.flush();
        fw.close();
    }

    @Test
    public void testCodeGeneratorProf() throws IOException, SyntacticException, LexicalException {
        SymbolTable.init();
        String path = "src/test/codeGeneratorVisitor/Programma_prof.txt";
        String pathCorretto = "src/test/codeGeneratorVisitor/Programma_CodeGenerator_prof.txt";
        NodeProgram prg = runParser(path);
        TypeCheckingVisitor tcv = new TypeCheckingVisitor();
        CodeGeneratorVisitor cgv = new CodeGeneratorVisitor();
        tcv.visit(prg);

        assertTrue(tcv.getLogString().isEmpty());
        cgv.visit(prg);
        File output = new File (pathCorretto);
        FileWriter fw = new FileWriter(output, false);

        fw.write(cgv.getCodice().toString());
        fw.flush();
        fw.close();
    }

    @Test
    public void testProgramTesting() throws IOException, SyntacticException, LexicalException {
        SymbolTable.init();
        String path = "src/test/codeGeneratorVisitor/Programma_Testing_Out.txt";
        String pathCorretto = "src/test/codeGeneratorVisitor/Programma_Testato_Corretto.txt";
        NodeProgram prg = runParser(path);
        TypeCheckingVisitor tcv = new TypeCheckingVisitor();
        CodeGeneratorVisitor cgv = new CodeGeneratorVisitor();
        tcv.visit(prg);

        assertTrue(tcv.getLogString().isEmpty());

        cgv.visit(prg);
        File output = new File (pathCorretto);
        FileWriter fw = new FileWriter(output, false);

        fw.write(cgv.getCodice().toString());
        fw.flush();
        fw.close();
        assertEquals(" 3 4 * sA 0 k lA p P lA 3.5 5 k + sB 0 k lB p P lC p P", cgv.getCodice().toString());
    }

}