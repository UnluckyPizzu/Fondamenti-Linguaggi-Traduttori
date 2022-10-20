package parser;

import java.io.IOException;
import java.util.ArrayList;

import eccezioni.LexicalException;
import eccezioni.SyntacticException;
import scanner.Scanner;
import token.*;
import ast.*;


public class Parser {

    private Scanner scanner;
    private StringBuilder log;


    public Parser (Scanner scanner) {
        this.scanner = scanner;
        this.log = new StringBuilder();
    }

    public String getLog() {
        return log.toString();
    }

    Token match (TokenType type) throws SyntacticException, IOException, LexicalException{
        Token tk;
        try {
            tk = scanner.peekToken();
            if(type.equals(tk.getTipo()))
                return scanner.nextToken();
            else {
                //log.append("Aspettato " + type + " token invece di "+ tk.getTipo() + " alla riga " + tk.getRiga() + "\n");
                throw new SyntacticException("Aspettato " + type + " token invece di "+ tk.getTipo() + " alla riga " + tk.getRiga() + "\n");
            }
        }
        catch(LexicalException | IOException ex) {
            //log.append("Eccezione sintattica");
            throw new SyntacticException("Eccezione sintattica", ex);
        }
    }


    public NodeProgram parse() throws SyntacticException, IOException, LexicalException {
        try {
            return parsePrg();
        }
        catch(LexicalException | IOException ex) {
            //log.append("Eccezione sintattica");
            throw new SyntacticException("Eccezione sintattica", ex);
        }
    }


    private NodeProgram parsePrg() throws SyntacticException, IOException, LexicalException{
        Token tk;
        try {
            tk = scanner.peekToken();
        }
        catch(LexicalException | IOException ex) {
            //log.append("Eccezione sintattica");
            throw new SyntacticException("Eccezione sintattica", ex);
        }

        switch (tk.getTipo()) {
            case TYINT:
            case TYFLOAT:
            case ID:
            case PRINT:
            case EOF:
                ArrayList<NodeDecSt> retNodeDecSt = parseDSs();
                match(TokenType.EOF);
                return new NodeProgram(retNodeDecSt);
            default:
                throw new SyntacticException("Il token alla riga " + tk.getRiga() + " non e' l'inizio di un programma");
        }
    }

    private ArrayList<NodeDecSt> parseDSs() throws SyntacticException, IOException, LexicalException{
        Token tk;
        try {
            tk = scanner.peekToken();
        }
        catch(LexicalException | IOException ex) {
            throw new SyntacticException("Eccezione sintattica", ex);
        }

        switch (tk.getTipo()) {
            case TYINT:
            case TYFLOAT:
                NodeDecl dec = parseDcl();
                ArrayList<NodeDecSt> retList1 = parseDSs();
                retList1.add(0, dec);
                return retList1;
            case ID:
            case PRINT:
                NodeStm stm = parseStm();
                ArrayList<NodeDecSt> retList2 = parseDSs();
                retList2.add(0, stm);
                return retList2;
            case EOF:
                return new ArrayList<NodeDecSt>();
            default:
        }		throw new SyntacticException("Il token alla riga " + tk.getRiga() + " non e' l'inizio di un programma");
    }

    private NodeDecl parseDcl() throws SyntacticException, IOException, LexicalException{
        Token tk;
        try {
            tk = scanner.peekToken();
        }
        catch(LexicalException | IOException ex) {
            throw new SyntacticException("Eccezione sintattica", ex);
        }

        switch (tk.getTipo()) {
            case TYFLOAT:
                match(TokenType.TYFLOAT);
                tk = scanner.peekToken();
                match(TokenType.ID);
                match(TokenType.SEMI);
                return new NodeDecl(new NodeId(tk.getVal()), LangType.FLOATy);
            case TYINT:
                match(TokenType.TYINT);
                tk = scanner.peekToken();
                match(TokenType.ID);
                match(TokenType.SEMI);
                return new NodeDecl(new NodeId(tk.getVal()), LangType.INTy);
            default:
                throw new SyntacticException("Il token della riga " + tk.getRiga() + " e' errato");
        }
    }

    private NodeStm parseStm() throws SyntacticException, IOException, LexicalException{
        Token tk;
        try {
            tk = scanner.peekToken();
        }
        catch(LexicalException | IOException ex) {
            throw new SyntacticException("Eccezione sintattica", ex);
        }

        switch (tk.getTipo()) {
            case ID:
                match(TokenType.ID);
                match(TokenType.ASSIGN);
                NodeExp expr = parseExp();
                match(TokenType.SEMI);
                return new NodeAssign(new NodeId(tk.getVal()), expr);
            case PRINT:
                match(TokenType.PRINT);
                tk = scanner.peekToken();
                match(TokenType.ID);
                match(TokenType.SEMI);
                return new NodePrint(new NodeId(tk.getVal()));
            default:
                throw new SyntacticException("Il token della riga " + tk.getRiga() + " e' errato");
        }
    }

    private NodeExp parseExp() throws SyntacticException, IOException, LexicalException{
        Token tk;
        try {
            tk = scanner.peekToken();
        }
        catch(LexicalException | IOException ex) {
            throw new SyntacticException("Eccezione sintattica", ex);
        }

        switch (tk.getTipo()) {
            case INT:
            case FLOAT:
            case ID:
                NodeExp leftExpr = parseTr();
                NodeExp expr = parseExpP(leftExpr);
                return expr;
            default:
                throw new SyntacticException("Il token della riga " + tk.getRiga() + " e' errato");
        }
    }


    private NodeExp parseExpP(NodeExp left) throws SyntacticException, IOException, LexicalException{
        Token tk;
        try {
            tk = scanner.peekToken();
        }
        catch(LexicalException | IOException ex) {
            throw new SyntacticException("Eccezione sintattica", ex);
        }

        switch (tk.getTipo()) {
            case PLUS:
                match(TokenType.PLUS);
                NodeExp exprPlus = parseTr();
                return parseExpP(new NodeBinOp(left,LangOper.PLUS,exprPlus));
            case MINUS:
                match(TokenType.MINUS);
                NodeExp exprMinus = parseTr();
                return parseExpP(new NodeBinOp(left,LangOper.MINUS,exprMinus));
            case SEMI:
                return left;
            default:
                throw new SyntacticException("Il token della riga " + tk.getRiga() + " e' errato");
        }
    }

    private NodeExp parseTr() throws SyntacticException, IOException, LexicalException{
        Token tk;
        try {
            tk = scanner.peekToken();
        }
        catch(LexicalException | IOException ex) {
            throw new SyntacticException("Eccezione sintattica", ex);
        }

        switch (tk.getTipo()) {
            case INT:
            case FLOAT:
            case ID:
                NodeExp exprLeft = parseVal();
                NodeExp expr = parseTrP(exprLeft);
                return expr;
            default:
                throw new SyntacticException("Il token della riga " + tk.getRiga() + " e' errato");
        }
    }

    private NodeExp parseTrP(NodeExp left) throws SyntacticException, IOException, LexicalException{
        Token tk;
        try {
            tk = scanner.peekToken();
        }
        catch(LexicalException | IOException ex) {
            throw new SyntacticException("Eccezione sintattica", ex);
        }

        switch (tk.getTipo()) {
            case TIMES:
                match(TokenType.TIMES);
                NodeExp exprTimes = parseVal();
                return parseTrP(new NodeBinOp(left, LangOper.TIMES , exprTimes));
            case DIV:
                match(TokenType.DIV);
                NodeExp exprDiv = parseVal();
                return  parseTrP(new NodeBinOp(left,LangOper.DIV, exprDiv));
            case PLUS:
            case MINUS:
            case SEMI:
                return left;
            default:
                throw new SyntacticException("Il token della riga " + tk.getRiga() + " e' errato");
        }
    }

    private NodeExp parseVal() throws SyntacticException, IOException, LexicalException{
        Token tk;
        try {
            tk = scanner.peekToken();
        }
        catch(LexicalException | IOException ex) {
            throw new SyntacticException("Eccezione sintattica", ex);
        }

        switch (tk.getTipo()) {
            case INT:
                match(TokenType.INT);
                return new NodeCost(tk.getVal(),LangType.INTy);
            case FLOAT:
                match(TokenType.FLOAT);
                return new NodeCost(tk.getVal(), LangType.FLOATy);
            case ID:
                match(TokenType.ID);
                return new NodeDeref(new NodeId(tk.getVal()));
            default:
                throw new SyntacticException("Il token della riga " + tk.getRiga() + " e' errato");
        }
    }

}
