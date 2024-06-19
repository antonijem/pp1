// generated with ast extension for cup
// version 0.8
// 24/7/2023 15:32:8


package rs.ac.bg.etf.pp1.ast;

public class FactorDesFindAny extends Factor {

    private FindAnyHelper FindAnyHelper;
    private Expr Expr;

    public FactorDesFindAny (FindAnyHelper FindAnyHelper, Expr Expr) {
        this.FindAnyHelper=FindAnyHelper;
        if(FindAnyHelper!=null) FindAnyHelper.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public FindAnyHelper getFindAnyHelper() {
        return FindAnyHelper;
    }

    public void setFindAnyHelper(FindAnyHelper FindAnyHelper) {
        this.FindAnyHelper=FindAnyHelper;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FindAnyHelper!=null) FindAnyHelper.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FindAnyHelper!=null) FindAnyHelper.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FindAnyHelper!=null) FindAnyHelper.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactorDesFindAny(\n");

        if(FindAnyHelper!=null)
            buffer.append(FindAnyHelper.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorDesFindAny]");
        return buffer.toString();
    }
}
