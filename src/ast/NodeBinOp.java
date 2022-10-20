package ast;

import visitor.IVisitor;

public class NodeBinOp extends NodeExp {

    private NodeExp left;
    private final LangOper op;
    private NodeExp right;

    public NodeBinOp(NodeExp left, LangOper op, NodeExp right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }

    @Override
    public String toString() {
        return "NodeBinOp [left = " + left + ", op = " + op + ", right = " + right + "]";
    }

    public NodeExp getLeft() {
        return left;
    }

    public LangOper getOp() {
        return op;
    }

    public NodeExp getRight() {
        return right;
    }

    public void setLeft(NodeExp left) {
        this.left = left;
    }

    public void setRight(NodeExp right) {
        this.right = right;
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

}
