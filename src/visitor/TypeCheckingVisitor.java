package visitor;

import ast.*;
import symbolTable.Attributes;
import symbolTable.SymbolTable;
import symbolTable.TypeDescriptor;

public class TypeCheckingVisitor implements IVisitor {

    private StringBuilder log;

    public TypeCheckingVisitor() {
        log = new StringBuilder();
    }

    @Override
    public void visit(NodeDecl node) {
        NodeId id = node.getId();
        if (SymbolTable.lookup(id.getName()) != null) {
            node.setResType(TypeDescriptor.ERROR);	// ERRORE, già dichiarato
            log.append("Variabile " + id.getName() + " già dichiarata\n");
        }
        else {
                node.setResType(TypeDescriptor.VOID);  // SIAMO SICURI DI STO VOID?
                Attributes attr = new Attributes(node.getType());
                id.setDefinition(attr);
                SymbolTable.enter(id.getName(), attr);
        }
    }
    @Override
    public void visit(NodeId node) {
        String nome = node.getName();

        if(SymbolTable.lookup(nome) == null) {
            node.setResType(TypeDescriptor.ERROR);
            log.append("Variabile " + nome + " non dichiarata\n");
        }
        else{
            Attributes type = SymbolTable.lookup(nome);
            if(type.getType().equals(LangType.INTy)) {
                node.setResType(TypeDescriptor.INTD);
            }
            else if(type.getType().equals(LangType.FLOATy)) {
                node.setResType(TypeDescriptor.FLOATD);
            }
            node.setDefinition(type);
        }
    }
    @Override
    public void visit(NodePrint node) {
        node.getId().accept(this);
        String nome = node.getId().getName(); //node.getId().accept(this);
        if(SymbolTable.lookup(nome) == null) {
            node.setResType(TypeDescriptor.ERROR);
            log.append("Stampa su variabile " + nome + " non dichiarata\n");
        }
        else {
            node.setResType(TypeDescriptor.VOID);
        }


    }
    @Override
    public void visit(NodeAssign node) {

        node.getId().accept(this);
        node.getExpr().accept(this);

        // se uno dei 2 NODI E' ERROR settiamo resType di node a ERROR
/*
        if (node.getId().getResType() == TypeDescriptor.ERROR || node.getExpr().getResType() == TypeDescriptor.ERROR) {
            node.setResType(TypeDescriptor.ERROR);
            return;
        }
        else if(!compatible(node.getId().getResType(), node.getExpr().getResType())){   //node.getId().getResType() == TypeDescriptor.INTD && node.getExpr().getResType() == TypeDescriptor.FLOATD
            node.setResType(TypeDescriptor.ERROR);  // se node.getId() e' INT e node.getExpr() e' FLOAT il resType di node e' ERROR
            return;
        }
        else if (compatible(node.getId().getResType(), node.getExpr().getResType())){   //node.getId().getResType() == TypeDescriptor.FLOATD && node.getExpr().getResType() == TypeDescriptor.INTD
            this.convert(node.getExpr());
            node.setResType(TypeDescriptor.VOID); // converto e assegno, ma perchè assegno proprio void?
            return;
        }
        else{
            node.setResType(TypeDescriptor.VOID); // se tutto va bene si va di void
            return;
        }

 */

    if (!compatible(node.getId().getResType(), node.getExpr().getResType())) {
        node.setResType(TypeDescriptor.ERROR);
        if (node.getId().getResType() == TypeDescriptor.INTD && node.getExpr().getResType() == TypeDescriptor.FLOATD)
            log.append("Impossibile effettuare il cast della variabile " + node.getId().getName() + " con l'espressione " + node.getExpr().toString() +"\n");
        return;
    }
    else if (node.getId().getResType() == TypeDescriptor.INTD){
        node.setResType(TypeDescriptor.VOID); // se tutto va bene si va di void t1 = INT e t2 = INT
        return;
    }
    else {
       // this.convert(node.getExpr());
        node.setExpr(this.convert(node.getExpr()));
        //NodeExp exp = this.convert(node.getExpr());
        //node.setExpr(exp);
        node.setResType(TypeDescriptor.VOID); // converto e assegno, ma perchè assegno proprio void?   sono tutti e due compatibili ma ID è float
        return;
    }

    }

    @Override
    public void visit(NodeBinOp node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);

        // se uno dei 2 e' ERROR resType di node a ERROR
        if (node.getLeft().getResType() == TypeDescriptor.ERROR || node.getRight().getResType() == TypeDescriptor.ERROR){
            node.setResType(TypeDescriptor.ERROR);
            return;
        }
        else if (node.getLeft().getResType() == node.getRight().getResType()){
            node.setResType(node.getLeft().getResType());
            return;
        }
        else if (node.getLeft().getResType() == TypeDescriptor.INTD){   // se t1 è INT allora t2 è float
            //convert(node.getLeft());
            //NodeExp conv = convert(node.getLeft());
            //node.setLeft(conv);
            node.setLeft(this.convert(node.getLeft()));
            node.setResType(TypeDescriptor.FLOATD);
            return;
        }
        else if (node.getLeft().getResType() == TypeDescriptor.FLOATD){
            //NodeExp conv = convert(node.getRight());
            //node.setRight(conv);
            node.setRight(convert(node.getRight()));
            //convert(node.getRight());
            node.setResType(TypeDescriptor.FLOATD);
        }

    }


    @Override
    public void visit(NodeProgram node) {
        for(NodeDecSt n : node) {
            n.accept(this);
        }
        node.setResType(TypeDescriptor.VOID);
    }




    @Override
    public void visit(NodeCost node) {
        if (node.getType() == LangType.INTy)
            node.setResType(TypeDescriptor.INTD);
        else if (node.getType() == LangType.FLOATy)
            node.setResType(TypeDescriptor.FLOATD);
    }

    @Override
    public void visit(NodeDeref node) {
        node.getId().accept(this);
        if (node.getId().getResType() == TypeDescriptor.ERROR)
            node.setResType(TypeDescriptor.ERROR);
        else
            node.setResType(node.getId().getResType());
    }

    @Override
    public void visit(NodeConvert node) {
        node.getNodeExpr().accept(this);
        if (node.getNodeExpr().getResType() == TypeDescriptor.ERROR)
            node.setResType(TypeDescriptor.ERROR);
        /*
        else
            node.setResType(TypeDescriptor.VOID);

         */

    }

    private boolean compatible(TypeDescriptor t1, TypeDescriptor t2) {
        if (t1 == t2 && t1 != TypeDescriptor.ERROR && t2 != TypeDescriptor.ERROR)
            return true; // t1 = INT e t2 = INT   && t2 = FLOAT e t2 = FLOAT
        else if (t1 == TypeDescriptor.FLOATD && t2 == TypeDescriptor.INTD){
            return true; // t1 = FLOAT e t2 = INT
        }
        else
            return false; // t1 = ERROR || t2 == ERROR || t1 = INT e t2 = FLOAT
    }

    private NodeExp convert(NodeExp node) {
        if (node.getResType() == (TypeDescriptor.FLOATD))
            return node;
        else {
            NodeConvert nodeConvert = new NodeConvert(node);
            nodeConvert.setResType(TypeDescriptor.FLOATD);
            return nodeConvert;
        }
    }

    public String getLogString() {
        return log.toString();
    }
}
