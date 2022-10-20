package ast;

import symbolTable.Attributes;
import visitor.IVisitor;


public class NodeId extends NodeAST{

    private final String name;
    private Attributes definition;

    public NodeId(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public Attributes getDefinition() {
        return definition;
    }

    public void setDefinition(Attributes definition) {
        this.definition = definition;
    }

    @Override
    public void accept(IVisitor visitor) { visitor.visit(this); }

}
