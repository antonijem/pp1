// generated with ast extension for cup
// version 0.8
// 24/7/2023 15:32:8


package rs.ac.bg.etf.pp1.ast;

public class ActualParsList extends ActParsList {

    private ActParsListExpr ActParsListExpr;
    private ActParsList ActParsList;

    public ActualParsList (ActParsListExpr ActParsListExpr, ActParsList ActParsList) {
        this.ActParsListExpr=ActParsListExpr;
        if(ActParsListExpr!=null) ActParsListExpr.setParent(this);
        this.ActParsList=ActParsList;
        if(ActParsList!=null) ActParsList.setParent(this);
    }

    public ActParsListExpr getActParsListExpr() {
        return ActParsListExpr;
    }

    public void setActParsListExpr(ActParsListExpr ActParsListExpr) {
        this.ActParsListExpr=ActParsListExpr;
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
        if(ActParsListExpr!=null) ActParsListExpr.accept(visitor);
        if(ActParsList!=null) ActParsList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ActParsListExpr!=null) ActParsListExpr.traverseTopDown(visitor);
        if(ActParsList!=null) ActParsList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ActParsListExpr!=null) ActParsListExpr.traverseBottomUp(visitor);
        if(ActParsList!=null) ActParsList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ActualParsList(\n");

        if(ActParsListExpr!=null)
            buffer.append(ActParsListExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActParsList!=null)
            buffer.append(ActParsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ActualParsList]");
        return buffer.toString();
    }
}
