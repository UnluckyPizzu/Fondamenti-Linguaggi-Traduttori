package ast;

import symbolTable.TypeDescriptor;
import visitor.IVisitor;

public abstract class NodeAST {

    private TypeDescriptor resType;

    public NodeAST() {
        // TODO Auto-generated constructor stub
    }

    public abstract void accept(IVisitor visitor);

    public TypeDescriptor getResType() {
        return resType;
    }

    public void setResType(TypeDescriptor resType) {
        this.resType = resType;
    }


}
