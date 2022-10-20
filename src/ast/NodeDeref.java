package ast;

import visitor.IVisitor;


public class NodeDeref extends NodeExp {

    private final NodeId id;

    public NodeDeref(NodeId id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "NodeDeref [id = " + id + "]";
    }

    public NodeId getId() {
        return id;
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

}
