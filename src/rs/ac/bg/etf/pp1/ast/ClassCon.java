// generated with ast extension for cup
// version 0.8
// 24/7/2023 15:32:8


package rs.ac.bg.etf.pp1.ast;

public class ClassCon extends ClassMember {

    private ClassConstructor ClassConstructor;

    public ClassCon (ClassConstructor ClassConstructor) {
        this.ClassConstructor=ClassConstructor;
        if(ClassConstructor!=null) ClassConstructor.setParent(this);
    }

    public ClassConstructor getClassConstructor() {
        return ClassConstructor;
    }

    public void setClassConstructor(ClassConstructor ClassConstructor) {
        this.ClassConstructor=ClassConstructor;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassConstructor!=null) ClassConstructor.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassConstructor!=null) ClassConstructor.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassConstructor!=null) ClassConstructor.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassCon(\n");

        if(ClassConstructor!=null)
            buffer.append(ClassConstructor.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassCon]");
        return buffer.toString();
    }
}
