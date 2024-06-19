// generated with ast extension for cup
// version 0.8
// 24/7/2023 15:32:8


package rs.ac.bg.etf.pp1.ast;

public class ActualPars extends ActPars {

    private ActParsExpr ActParsExpr;
    private ActParsList ActParsList;

    public ActualPars (ActParsExpr ActParsExpr, ActParsList ActParsList) {
        this.ActParsExpr=ActParsExpr;
        if(ActParsExpr!=null) ActParsExpr.setParent(this);
        this.ActParsList=ActParsList;
        if(ActParsList!=null) ActParsList.setParent(this);
    }

    public ActParsExpr getActParsExpr() {
        return ActParsExpr;
    }

    public void setActParsExpr(ActParsExpr ActParsExpr) {
        this.ActParsExpr=ActParsExpr;
    }

    public ActParsList getActParsList() {
        return ActParsList;
    }

    public void setActParsList(ActParsList ActParsList) {
        this.ActParsList=ActParsList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ActParsExpr!=null) ActParsExpr.accept(visitor);
        if(ActParsList!=null) ActParsList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ActParsExpr!=null) ActParsExpr.traverseTopDown(visitor);
        if(ActParsList!=null) ActParsList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ActParsExpr!=null) ActParsExpr.traverseBottomUp(visitor);
        if(ActParsList!=null) ActParsList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ActualPars(\n");

        if(ActParsExpr!=null)
            buffer.append(ActParsExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActParsList!=null)
            buffer.append(ActParsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ActualPars]");
        return buffer.toString();
    }
}
