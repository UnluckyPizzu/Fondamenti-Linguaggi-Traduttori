package ast;

import visitor.IVisitor;

public class NodeConvert extends NodeExp {

    private final NodeExp expr;
    public NodeExp getNodeExpr() { return expr; }
    public NodeConvert(NodeExp expr) {
        this.expr = expr;
    }



    @Override
    public String toString() {
        return "NodeConvert [expr = " + expr + "]";
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

}
