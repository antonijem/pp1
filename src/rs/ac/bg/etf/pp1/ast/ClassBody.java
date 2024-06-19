// generated with ast extension for cup
// version 0.8
// 24/7/2023 15:32:8


package rs.ac.bg.etf.pp1.ast;

public class ClassBody implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private VarDeclExist VarDeclExist;
    private ClassMemberBraces ClassMemberBraces;

    public ClassBody (VarDeclExist VarDeclExist, ClassMemberBraces ClassMemberBraces) {
        this.VarDeclExist=VarDeclExist;
        if(VarDeclExist!=null) VarDeclExist.setParent(this);
        this.ClassMemberBraces=ClassMemberBraces;
        if(ClassMemberBraces!=null) ClassMemberBraces.setParent(this);
    }

    public VarDeclExist getVarDeclExist() {
        return VarDeclExist;
    }

    public void setVarDeclExist(VarDeclExist VarDeclExist) {
        this.VarDeclExist=VarDeclExist;
    }

    public ClassMemberBraces getClassMemberBraces() {
        return ClassMemberBraces;
    }

    public void setClassMemberBraces(ClassMemberBraces ClassMemberBraces) {
        this.ClassMemberBraces=ClassMemberBraces;
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
        if(VarDeclExist!=null) VarDeclExist.accept(visitor);
        if(ClassMemberBraces!=null) ClassMemberBraces.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclExist!=null) VarDeclExist.traverseTopDown(visitor);
        if(ClassMemberBraces!=null) ClassMemberBraces.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclExist!=null) VarDeclExist.traverseBottomUp(visitor);
        if(ClassMemberBraces!=null) ClassMemberBraces.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassBody(\n");

        if(VarDeclExist!=null)
            buffer.append(VarDeclExist.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassMemberBraces!=null)
            buffer.append(ClassMemberBraces.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassBody]");
        return buffer.toString();
    }
}
