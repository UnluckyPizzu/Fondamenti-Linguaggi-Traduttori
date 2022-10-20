package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.Test;
import eccezioni.LexicalException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

public class TestScanner {

    @Test
    public void testScanId() throws IOException, LexicalException {
        String path ="src/test/data/testIdKw.txt";
        Scanner scanner = new Scanner(path);
        Token t = scanner.nextToken();
        assertEquals("int", t.getVal());
        assertEquals(1, t.getRiga());
        assertEquals(TokenType.TYINT, t.getTipo());
        t = scanner.nextToken();
        assertEquals("float", t.getVal());
        assertEquals(2, t.getRiga());
        assertEquals(TokenType.TYFLOAT, t.getTipo());
        t = scanner.nextToken();
        assertEquals("floata", t.getVal());
        assertEquals(2, t.getRiga());
        assertEquals(TokenType.ID, t.getTipo());
        t = scanner.nextToken();
        assertEquals("print", t.getVal());
        assertEquals(3, t.getRiga());
        assertEquals(TokenType.PRINT, t.getTipo());
        t = scanner.nextToken();
        assertEquals("aprintf", t.getVal());
        assertEquals(3, t.getRiga());
        assertEquals(TokenType.ID, t.getTipo());
        t = scanner.nextToken();
        assertEquals("nome", t.getVal());
        assertEquals(4, t.getRiga());
        assertEquals(TokenType.ID, t.getTipo());
        t = scanner.nextToken();
        assertEquals("intnome", t.getVal());
        assertEquals(5, t.getRiga());
        assertEquals(TokenType.ID, t.getTipo());
        t = scanner.nextToken();
        assertEquals("int", t.getVal());
        assertEquals(6, t.getRiga());
        assertEquals(TokenType.TYINT, t.getTipo());
        t = scanner.nextToken();
        assertEquals("nome", t.getVal());
        assertEquals(6, t.getRiga());
        assertEquals(TokenType.ID, t.getTipo());
        t = scanner.nextToken();
        assertEquals(6, t.getRiga());
        assertEquals(TokenType.EOF, t.getTipo());
    }

    @Test
    public void testScanNumber() throws IOException, LexicalException {
        String path ="src/test/data/testNumbers.txt";
        Scanner scanner = new Scanner(path);
        Token t = scanner.nextToken();
        assertEquals("30000", t.getVal());
        assertEquals(1, t.getRiga());
        assertEquals(TokenType.INT, t.getTipo());
        t = scanner.nextToken();
        assertEquals("698", t.getVal());
        assertEquals(3, t.getRiga());
        assertEquals(TokenType.INT, t.getTipo());
        t = scanner.nextToken();
        assertEquals("13.454", t.getVal());
        assertEquals(4, t.getRiga());
        assertEquals(TokenType.FLOAT, t.getTipo());
        t = scanner.nextToken();
        assertEquals("098.895", t.getVal());
        assertEquals(4, t.getRiga());
        assertEquals(TokenType.FLOAT, t.getTipo());
        t = scanner.nextToken();
        assertEquals("45668", t.getVal());
        assertEquals(5, t.getRiga());
        assertEquals(TokenType.INT, t.getTipo());
        t = scanner.nextToken();
        assertEquals("98", t.getVal());
        assertEquals(6, t.getRiga());
        assertEquals(TokenType.INT, t.getTipo());
        t = scanner.nextToken();
        assertEquals("89.99999", t.getVal());
        assertEquals(8, t.getRiga());
        assertEquals(TokenType.FLOAT, t.getTipo());
        t = scanner.nextToken();
        assertEquals("28", t.getVal());
        assertEquals(9, t.getRiga());
        assertEquals(TokenType.INT, t.getTipo());
        t = scanner.nextToken();
        t = scanner.nextToken();
        t = scanner.nextToken();
        assertEquals("30", t.getVal());
        assertEquals(10, t.getRiga());
        assertEquals(TokenType.INT, t.getTipo());
        t = scanner.nextToken();
        assertEquals("y", t.getVal());
        assertEquals(10, t.getRiga());
        assertEquals(TokenType.ID, t.getTipo());
    }

    @Test
    public void testScanOperators() throws IOException, LexicalException {
        String path ="src/test/data/testOperators.txt";
        Scanner scanner = new Scanner(path);
        Token t = scanner.nextToken();
        assertNull(t.getVal());
        assertEquals(1, t.getRiga());
        assertEquals(TokenType.PLUS, t.getTipo());
        t = scanner.nextToken();
        assertNull(t.getVal());
        assertEquals(2, t.getRiga());
        assertEquals(TokenType.MINUS, t.getTipo());
        t = scanner.nextToken();
        assertNull(t.getVal());
        assertEquals(2, t.getRiga());
        assertEquals(TokenType.TIMES, t.getTipo());
        t = scanner.nextToken();
        assertNull(t.getVal());
        assertEquals(3, t.getRiga());
        assertEquals(TokenType.DIV, t.getTipo());
        t = scanner.nextToken();
        assertNull(t.getVal());
        assertEquals(8, t.getRiga());
        assertEquals(TokenType.ASSIGN, t.getTipo());
        t = scanner.nextToken();
        assertNull(t.getVal());
        assertEquals(10, t.getRiga());
        assertEquals(TokenType.SEMI, t.getTipo());
        t = scanner.nextToken();
        assertEquals(10, t.getRiga());
        assertEquals(TokenType.EOF, t.getTipo());
    }

    @Test
    public void testScanEOF() throws IOException, LexicalException {
        String path ="src/test/data/testEOF.txt";
        Scanner scanner = new Scanner(path);
        Token t = scanner.nextToken();
        assertEquals(3, t.getRiga());
        assertEquals(TokenType.EOF, t.getTipo());
    }

    @Test
    public void testScanGenerale() throws IOException, LexicalException {
        String path ="src/test/data/testGenerale.txt";
        Scanner scanner = new Scanner(path);
        Token t = scanner.nextToken();
        assertEquals("int", t.getVal());
        assertEquals(2, t.getRiga());
        assertEquals(TokenType.TYINT, t.getTipo());
        t = scanner.nextToken();
        assertEquals("tempa", t.getVal());
        assertEquals(2, t.getRiga());
        assertEquals(TokenType.ID, t.getTipo());
        t = scanner.nextToken();
        assertEquals(2, t.getRiga());
        assertEquals(TokenType.SEMI, t.getTipo());
        t = scanner.nextToken();
        assertEquals("tempa", t.getVal());
        assertEquals(3, t.getRiga());
        assertEquals(TokenType.ID, t.getTipo());
        t = scanner.nextToken();
        assertNull(t.getVal());
        assertEquals(3, t.getRiga());
        assertEquals(TokenType.ASSIGN, t.getTipo());
        t = scanner.nextToken();
        assertEquals("5", t.getVal());
        assertEquals(3, t.getRiga());
        assertEquals(TokenType.INT, t.getTipo());
        t = scanner.nextToken();
        assertEquals(3, t.getRiga());
        assertEquals(TokenType.SEMI, t.getTipo());
        t = scanner.nextToken();
        assertEquals("float", t.getVal());
        assertEquals(5, t.getRiga());
        assertEquals(TokenType.TYFLOAT, t.getTipo());
        t = scanner.nextToken();
        assertEquals("tempb", t.getVal());
        assertEquals(5, t.getRiga());
        assertEquals(TokenType.ID, t.getTipo());
        t = scanner.nextToken();
        assertEquals(5, t.getRiga());
        assertEquals(TokenType.SEMI, t.getTipo());
        t = scanner.nextToken();
        assertEquals("tempb", t.getVal());
        assertEquals(6, t.getRiga());
        assertEquals(TokenType.ID, t.getTipo());
        t = scanner.nextToken();
        assertNull(t.getVal());
        assertEquals(6, t.getRiga());
        assertEquals(TokenType.ASSIGN, t.getTipo());
        t = scanner.nextToken();
        assertEquals("tempa", t.getVal());
        assertEquals(6, t.getRiga());
        assertEquals(TokenType.ID, t.getTipo());
        t = scanner.nextToken();
        assertNull(t.getVal());
        assertEquals(6, t.getRiga());
        assertEquals(TokenType.PLUS, t.getTipo());
        t = scanner.nextToken();
        assertEquals("3.2", t.getVal());
        assertEquals(6, t.getRiga());
        assertEquals(TokenType.FLOAT, t.getTipo());
        t = scanner.nextToken();
        assertNull(t.getVal());
        assertEquals(6, t.getRiga());
        assertEquals(TokenType.SEMI, t.getTipo());
        t = scanner.nextToken();
        assertEquals("print", t.getVal());
        assertEquals(7, t.getRiga());
        assertEquals(TokenType.PRINT, t.getTipo());
        t = scanner.nextToken();
        assertEquals("tempb", t.getVal());
        assertEquals(7, t.getRiga());
        assertEquals(TokenType.ID, t.getTipo());
        t = scanner.nextToken();
        assertNull(t.getVal());
        assertEquals(7, t.getRiga());
        assertEquals(TokenType.SEMI, t.getTipo());
        t = scanner.nextToken();
        assertEquals(7, t.getRiga());
        assertEquals(TokenType.EOF, t.getTipo());
    }

}