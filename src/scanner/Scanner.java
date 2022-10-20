package scanner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import eccezioni.LexicalException;
import token.*;


public class Scanner {
    final char EOF = (char) -1; 	// int 65535
    final int MIN_DIGIT = 1;
    final int MAX_DIGIT = 5;
    private int riga;
    private PushbackReader buffer;
    private String log;
    private List<Character> letters; // 'a',...'z'
    private List<Character> skipChars; // ' ', '\n', '\t', '\r', EOF
    private List<Character> numbers; // '0',...'9'

    private HashMap<String, TokenType> keyWordsMap;  	 //"print", "float", "int"
    private HashMap<Character, TokenType> operatorsMap;  //'+', '-', '*', '/', '=', ';'


    public Token op_t = null;
    private Token nextTk = null;


    public Scanner(String fileName) throws FileNotFoundException {
        this.buffer = new PushbackReader(new FileReader(fileName));
        if(this.buffer == null)
            throw new FileNotFoundException("File non trovato!");
        riga = 1;

        letters = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');
        skipChars = Arrays.asList(' ', EOF, '\n', '\r', '\t');
        numbers = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
        inizializza0pMap();
        inizializzaKwMap();
    }

    private void inizializzaKwMap() {
        keyWordsMap = new HashMap<String, TokenType>();
        keyWordsMap.put("int", TokenType.TYINT);
        keyWordsMap.put("float", TokenType.TYFLOAT);
        keyWordsMap.put("print", TokenType.PRINT);	// ScanID
    }

    private void inizializza0pMap() {
        operatorsMap = new HashMap<Character, TokenType>();
        operatorsMap.put('+', TokenType.PLUS);
        operatorsMap.put('-', TokenType.MINUS);
        operatorsMap.put('*', TokenType.TIMES);
        operatorsMap.put('/', TokenType.DIV);
        operatorsMap.put('=', TokenType.ASSIGN);
        operatorsMap.put(';', TokenType.SEMI);
    }




    public Token peekToken() throws IOException, LexicalException{
        this.nextTk = nextToken();
        return this.nextTk;
    }


    public Token nextToken() throws LexicalException {

        if(nextTk != null)
        {
            Token next = nextTk;
            nextTk = null;
            return next;
        }

        char nextChar;

        try {

            nextChar = peekChar();

            char skip;

            while(this.skipChars.contains(nextChar))
            {
                skip = readChar();

                if(skip == '\n')
                {
                    nextChar = peekChar();
                    riga++;
                }
                else {
                    if(skip == EOF)
                    {
                        Token eof_t = new Token(TokenType.EOF, riga);
                        return eof_t;
                    }

                    else
                    {
                        nextChar = peekChar();
                    }
                }
            }


            if(this.numbers.contains(nextChar))
                return scanNumber();


            if(this.letters.contains(nextChar))
                return scanId();


            if(this.operatorsMap.containsKey(nextChar))
            {
                op_t = new Token(this.operatorsMap.get(readChar()), riga);
                return op_t;
            }
        }

        catch(IOException ex)
        {
            throw new LexicalException("Errata lettura della riga " + riga + "\n", ex);
        }

        return null ;

    }

    private Token scanId() throws IOException, LexicalException {
        try {
            StringBuffer res = new StringBuffer("");
            char let = readChar();
            res.append(let);

            while(letters.contains(peekChar()))
            {
                res.append(readChar());
            }

            Token letter_t = null;

            if(this.keyWordsMap.containsKey(res.toString())) {
                return letter_t = new Token(this.keyWordsMap.get(res.toString()), riga, res.toString());
            }
            else
                return letter_t = new Token(TokenType.ID, riga, res.toString());
        }
        catch(IOException ex)
        {
            throw new LexicalException("Carattere " + peekChar() + " alla riga " + riga + "\n", ex);
        }
    }

    private Token scanNumber() throws IOException, LexicalException {
        try {
            StringBuffer res = new StringBuffer("");
            char num = readChar();
            res.append(num);

            Token number_t;

            while(this.numbers.contains(peekChar()))
                res.append(readChar());

            if(peekChar() != '.')
            {
                number_t = new Token(TokenType.INT, riga, res.toString());
                return number_t;
            }
            else
            {
                res.append(readChar());
                int index = res.length()-1;
                int count = 0;

                while(this.numbers.contains(peekChar()))
                {
                    res.append(readChar());
                    count++;
                }

                if((count <= MAX_DIGIT) && (count >= MIN_DIGIT))
                {
                    number_t = new Token(TokenType.FLOAT, riga, res.toString());
                    return number_t;
                }
                else
                {
                    if(count == 0)
                    {

                        String str_int = res.substring(0, index);
                        number_t = new Token(TokenType.INT, riga, str_int);
                        return number_t;
                    }
                    else //  > MAX_DIGIT
                    {

                        String str_float = res.substring(0, index + MAX_DIGIT + 1);
                        number_t = new Token(TokenType.FLOAT, riga, str_float);
                        return number_t;
                    }
                }
            }
        }
        catch(IOException ex) {
            throw new LexicalException("Carattere " + peekChar() + " alla riga " + riga + "\n", ex);
        }
    }

    private char readChar() throws IOException {
        try {
            return ((char) this.buffer.read());
        }
        catch(IOException ex) {
            throw new IOException("Eccezione di I/O\n", ex);
        }
    }

    private char peekChar() throws IOException {
        try {
            char c = (char) buffer.read();
            buffer.unread(c);
            return c;
        }
        catch(IOException ex) {
            throw new IOException("Eccezione di I/O\n", ex);
        }
    }
}
