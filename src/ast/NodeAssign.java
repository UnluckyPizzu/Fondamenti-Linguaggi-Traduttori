package ast;

import visitor.IVisitor;

public class NodeAssign extends NodeStm {

    private final NodeId id;
    private NodeExp expr;

    public NodeAssign(NodeId id, NodeExp expr) {
        this.id = id;
        this.expr = expr;
    }

    @Override
    public String toString() {
        return "NodeAssign [id = " + id + ", expr = " + expr + "]";
    }

    public NodeId getId() {
        return id;
    }

    public NodeExp getExpr() {
        return expr;
    }

    public void setExpr(NodeExp expr) {
        this.expr = expr;
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

}
