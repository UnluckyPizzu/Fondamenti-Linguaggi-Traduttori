package visitor;

import ast.*;
import symbolTable.SymbolTable;

import java.util.HashMap;
import java.util.LinkedList;

public class CodeGeneratorVisitor implements IVisitor {

    private StringBuffer codice = new StringBuffer();
    static private LinkedList<Character> nomiRegistri;
    private HashMap<LangOper, Character> caratteriOperatori;

    public CodeGeneratorVisitor() {
        // TODO Auto-generated constructor stub
        nomiRegistri = new LinkedList<>();
        for (int i = 65; i <= 122 ; i++){
            nomiRegistri.add((char) i);
        }
        inizializzacaratteriOperatoriMap();
        codice = new StringBuffer();
    }

    private void inizializzacaratteriOperatoriMap() {
        caratteriOperatori = new HashMap<LangOper, Character>();
        caratteriOperatori.put(LangOper.PLUS, '+');
        caratteriOperatori.put(LangOper.MINUS, '-');
        caratteriOperatori.put(LangOper.TIMES, '*');
        caratteriOperatori.put(LangOper.DIV, '/');
    }

    static private char newRegister() {
        return nomiRegistri.pop();
    }


    @Override
    public void visit(NodeProgram node) {
        // TODO Auto-generated method stub
        for(NodeDecSt n : node) {
            n.accept(this);
        }
    }

    @Override
    public void visit(NodeId node) {
        // TODO Auto-generated method stub
    //nothing to do here
    }

    @Override
    public void visit(NodeDecl node) {
        node.getId().getDefinition().setRegistro(newRegister());
        SymbolTable.enter(node.getId().getName(), node.getId().getDefinition());
    }

    @Override
    public void visit(NodeAssign node) {
        node.getExpr().accept(this);
        codice.append(" s" + SymbolTable.lookup(node.getId().getName()).getRegistro() + " 0 k");
    }


    @Override
    public void visit(NodePrint node) {
        codice.append(" l" + SymbolTable.lookup(node.getId().getName()).getRegistro() + " p P");
    }

    @Override
    public void visit(NodeBinOp node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
        if (node.getLeft() instanceof NodeConvert || node.getRight() instanceof  NodeConvert)
            codice.append(" 5 k");
        codice.append(" " + caratteriOperatori.get(node.getOp()));
    }

    @Override
    public void visit(NodeDeref node) {
        codice.append(" l" + node.getId().getDefinition().getRegistro());
    }



    @Override
    public void visit(NodeCost node) {
        codice.append(" "+ node.getValue());
    }


    @Override
    public void visit(NodeConvert node) {
        // Genera il codice per cambiare la precisione a 5 cifre decimali.
        node.getNodeExpr().accept(this);
    }

    public StringBuffer getCodice() {
        return codice;
    }
}
