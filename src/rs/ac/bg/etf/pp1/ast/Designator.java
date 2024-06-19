// generated with ast extension for cup
// version 0.8
// 24/7/2023 15:32:8


package rs.ac.bg.etf.pp1.ast;

public class Designator implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private String desName;
    private DesignatorOptions DesignatorOptions;

    public Designator (String desName, DesignatorOptions DesignatorOptions) {
        this.desName=desName;
        this.DesignatorOptions=DesignatorOptions;
        if(DesignatorOptions!=null) DesignatorOptions.setParent(this);
    }

    public String getDesName() {
        return desName;
    }

    public void setDesName(String desName) {
        this.desName=desName;
    }

    public DesignatorOptions getDesignatorOptions() {
        return DesignatorOptions;
    }

    public void setDesignatorOptions(DesignatorOptions DesignatorOptions) {
        this.DesignatorOptions=DesignatorOptions;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorOptions!=null) DesignatorOptions.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorOptions!=null) DesignatorOptions.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorOptions!=null) DesignatorOptions.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Designator(\n");

        buffer.append(" "+tab+desName);
        buffer.append("\n");

        if(DesignatorOptions!=null)
            buffer.append(DesignatorOptions.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Designator]");
        return buffer.toString();
    }
}
