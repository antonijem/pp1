// generated with ast extension for cup
// version 0.8
// 24/7/2023 15:32:8


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclarationList extends ConstDeclList {

    private ConstDeclList ConstDeclList;
    private ConstDeclListName ConstDeclListName;

    public ConstDeclarationList (ConstDeclList ConstDeclList, ConstDeclListName ConstDeclListName) {
        this.ConstDeclList=ConstDeclList;
        if(ConstDeclList!=null) ConstDeclList.setParent(this);
        this.ConstDeclListName=ConstDeclListName;
        if(ConstDeclListName!=null) ConstDeclListName.setParent(this);
    }

    public ConstDeclList getConstDeclList() {
        return ConstDeclList;
    }

    public void setConstDeclList(ConstDeclList ConstDeclList) {
        this.ConstDeclList=ConstDeclList;
    }

    public ConstDeclListName getConstDeclListName() {
        return ConstDeclListName;
    }

    public void setConstDeclListName(ConstDeclListName ConstDeclListName) {
        this.ConstDeclListName=ConstDeclListName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstDeclList!=null) ConstDeclList.accept(visitor);
        if(ConstDeclListName!=null) ConstDeclListName.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstDeclList!=null) ConstDeclList.traverseTopDown(visitor);
        if(ConstDeclListName!=null) ConstDeclListName.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstDeclList!=null) ConstDeclList.traverseBottomUp(visitor);
        if(ConstDeclListName!=null) ConstDeclListName.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclarationList(\n");

        if(ConstDeclList!=null)
            buffer.append(ConstDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstDeclListName!=null)
            buffer.append(ConstDeclListName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclarationList]");
        return buffer.toString();
    }
}
