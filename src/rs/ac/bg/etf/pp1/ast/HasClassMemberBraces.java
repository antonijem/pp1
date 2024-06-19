// generated with ast extension for cup
// version 0.8
// 24/7/2023 15:32:8


package rs.ac.bg.etf.pp1.ast;

public class HasClassMemberBraces extends ClassMemberBraces {

    private ClassMembers ClassMembers;

    public HasClassMemberBraces (ClassMembers ClassMembers) {
        this.ClassMembers=ClassMembers;
        if(ClassMembers!=null) ClassMembers.setParent(this);
    }

    public ClassMembers getClassMembers() {
        return ClassMembers;
    }

    public void setClassMembers(ClassMembers ClassMembers) {
        this.ClassMembers=ClassMembers;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassMembers!=null) ClassMembers.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassMembers!=null) ClassMembers.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassMembers!=null) ClassMembers.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("HasClassMemberBraces(\n");

        if(ClassMembers!=null)
            buffer.append(ClassMembers.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [HasClassMemberBraces]");
        return buffer.toString();
    }
}
