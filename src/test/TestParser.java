package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import eccezioni.LexicalException;
import eccezioni.SyntacticException;
import parser.Parser;
import scanner.Scanner;

class TestParser {

    @Test
    public void testScannerCorrect1() throws IOException, SyntacticException, LexicalException{
        String path ="src/test/testParser/fileScannerCorrect1.txt";
        Scanner scanner = new Scanner(path);
        Parser parser = new Parser(scanner);
        Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
    }

    @Test
    public void testParserCorrect2() throws IOException, SyntacticException, LexicalException {
        String path ="src/test/testParser/fileParserCorrect2.txt";
        Scanner scanner = new Scanner(path);
        Parser parser = new Parser(scanner);
        Assertions.assertDoesNotThrow(() -> parser.parse());
    }

    @Test
    public void testParserCorrect3() throws IOException, SyntacticException, LexicalException{
        String path ="src/test/testParser/fileParserCorrect3.txt";
        Scanner scanner = new Scanner(path);
        Parser parser = new Parser(scanner);
        Assertions.assertDoesNotThrow(() -> parser.parse());
    }


    @Test
    public void testParserWrong1() throws IOException, SyntacticException, LexicalException{
        String path ="src/test/testParser/fileParserWrong1.txt";
        Scanner scanner = new Scanner(path);
        Parser parser = new Parser(scanner);
        Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
    }


    @Test
    public void testParserWrong2() throws IOException, SyntacticException, LexicalException{
        String path ="src/test/testParser/fileParserWrong2.txt";
        Scanner scanner = new Scanner(path);
        Parser parser = new Parser(scanner);
        Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
    }


    @Test
    public void testDSsDclStm() throws IOException, SyntacticException, LexicalException{
        String path ="src/test/testParser/testDSsDclStm.txt";
        Scanner scanner = new Scanner(path);
        Scanner scanner2 = new Scanner(path);
        Parser parser = new Parser(scanner);
        Parser parser2 = new Parser(scanner2);
        Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
        Assertions.assertThrows(SyntacticException.class, () -> parser2.parse().toString());
    }




    @Test
    public void testDec() throws IOException, SyntacticException, LexicalException{
        String path ="src/test/testParser/testDec.txt";
        Scanner scanner = new Scanner(path);
        Scanner scanner2 = new Scanner(path);
        Parser parser = new Parser(scanner);
        Parser parser2 = new Parser(scanner2);
        Assertions.assertDoesNotThrow(() -> parser.parse());

        assertEquals(parser2.parse().toString(),"NodeProgram [decSts = [NodeDecl [id = a, type = INTy], NodeDecl [id = b, type = FLOATy], NodePrint [id = a]]]");
    }

}
