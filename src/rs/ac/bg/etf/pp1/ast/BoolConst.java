// generated with ast extension for cup
// version 0.8
// 24/7/2023 15:32:8


package rs.ac.bg.etf.pp1.ast;

public class BoolConst extends Const {

    private String b;

    public BoolConst (String b) {
        this.b=b;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b=b;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("BoolConst(\n");

        buffer.append(" "+tab+b);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [BoolConst]");
        return buffer.toString();
    }
}
