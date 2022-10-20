package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import ast.LangType;
import ast.NodeProgram;
import eccezioni.LexicalException;
import eccezioni.SyntacticException;
import parser.Parser;
import scanner.Scanner;
import symbolTable.SymbolTable;
import visitor.CodeGeneratorVisitor;
import visitor.TypeCheckingVisitor;

class TestAnalisiSemantica {

    public static NodeProgram runParser(String filePath ) throws IOException, SyntacticException, LexicalException  {
        return new Parser( new Scanner( filePath ) ).parse();
    }

    @Test
    public void testAnalisiSemanticaTrue() throws IOException, SyntacticException, LexicalException {
        SymbolTable.init();
        String path = "src/test/typeCheckingVisitor/Analisi_semantica_true.txt";
        NodeProgram prg = runParser(path);
        TypeCheckingVisitor visitor = new TypeCheckingVisitor();
        visitor.visit(prg);
        assertEquals(SymbolTable.lookup("a").getType(), LangType.FLOATy);
        assertEquals(SymbolTable.lookup("b").getType(), LangType.INTy);
        assertEquals(SymbolTable.lookup("c").getType(), LangType.INTy);
        assertTrue(visitor.getLogString().isEmpty());
    }

    @Test
    public void testVariabileNonDichiarata() throws IOException, SyntacticException, LexicalException {
        SymbolTable.init();
        String path = "src/test/typeCheckingVisitor/Variabile_non_dichiarata.txt";
        NodeProgram prg = runParser(path);
        TypeCheckingVisitor visitor = new TypeCheckingVisitor();
        visitor.visit(prg);
        assertEquals("Variabile b non dichiarata\nVariabile c non dichiarata\n", visitor.getLogString());
    }

    @Test
    public void testVariabileDoubleDeclare() throws IOException, SyntacticException, LexicalException {
        SymbolTable.init();
        String path = "src/test/typeCheckingVisitor/Test_Double_Declare.txt";
        NodeProgram prg = runParser(path);
        TypeCheckingVisitor visitor = new TypeCheckingVisitor();
        visitor.visit(prg);
        assertEquals("Variabile b già dichiarata\nVariabile c già dichiarata\n", visitor.getLogString());
    }

    @Test
    public void testErroreConversioneTipo() throws IOException, SyntacticException, LexicalException {
        SymbolTable.init();
        String path = "src/test/typeCheckingVisitor/Errore_conversione_tipo.txt";
        NodeProgram prg = runParser(path);
        TypeCheckingVisitor visitor = new TypeCheckingVisitor();
        visitor.visit(prg);
        assertEquals("Impossibile effettuare il cast della variabile a con l'espressione NodeDeref [id = c]\n", visitor.getLogString());
    }

}