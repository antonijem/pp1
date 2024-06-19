// generated with ast extension for cup
// version 0.8
// 24/7/2023 15:32:8


package rs.ac.bg.etf.pp1.ast;

public class DesArray extends DesignatorOptions {

    private DesignatorArrayHelper DesignatorArrayHelper;
    private Expr Expr;
    private DesignatorOptions DesignatorOptions;

    public DesArray (DesignatorArrayHelper DesignatorArrayHelper, Expr Expr, DesignatorOptions DesignatorOptions) {
        this.DesignatorArrayHelper=DesignatorArrayHelper;
        if(DesignatorArrayHelper!=null) DesignatorArrayHelper.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.DesignatorOptions=DesignatorOptions;
        if(DesignatorOptions!=null) DesignatorOptions.setParent(this);
    }

    public DesignatorArrayHelper getDesignatorArrayHelper() {
        return DesignatorArrayHelper;
    }

    public void setDesignatorArrayHelper(DesignatorArrayHelper DesignatorArrayHelper) {
        this.DesignatorArrayHelper=DesignatorArrayHelper;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public DesignatorOptions getDesignatorOptions() {
        return DesignatorOptions;
    }

    public void setDesignatorOptions(DesignatorOptions DesignatorOptions) {
        this.DesignatorOptions=DesignatorOptions;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorArrayHelper!=null) DesignatorArrayHelper.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
        if(DesignatorOptions!=null) DesignatorOptions.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorArrayHelper!=null) DesignatorArrayHelper.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(DesignatorOptions!=null) DesignatorOptions.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorArrayHelper!=null) DesignatorArrayHelper.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(DesignatorOptions!=null) DesignatorOptions.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesArray(\n");

        if(DesignatorArrayHelper!=null)
            buffer.append(DesignatorArrayHelper.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorOptions!=null)
            buffer.append(DesignatorOptions.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesArray]");
        return buffer.toString();
    }
}
