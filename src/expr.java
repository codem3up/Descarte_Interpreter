/**
 * Created by Andrew Becker and Costadinos Argiris
 * Class: elsePart
 * This class is a node used to build out and execute the grammar for all expressions.
 * Note: This statement can either return a boolean type, or a numeric value
 */
public class expr implements Expression{
    boolTerm boolTerm;
    boolTermTail boolTermTail;

    public expr(TokenList tokens){
        System.out.print("EXPR: ");
        tokens.printList();

        int firstOr = tokens.indexOf("OR");
        if(firstOr == -1){
            boolTerm = new boolTerm(tokens);
            boolTermTail = new boolTermTail();
        }
        else{
            boolTerm = new boolTerm(tokens.between(0, firstOr));
            boolTermTail = new boolTermTail(tokens.between(firstOr, tokens.size()));
        }
    }

    @Override
    public boolean isValid() {
        System.out.println("CHECKING EXPR ISVALID()");
        if(boolTerm.isValid() && boolTermTail.isValid()){
            return true;
        }
        System.out.println("EXPR NOT VALID");
        return false;
    }

    public double executeDouble(Variables variables){
        return boolTerm.executeDouble(variables);
    }

    public boolean executeBoolean(Variables variables){
        if(boolTermTail.executable){
            if(boolTerm.executeBoolean(variables) || boolTermTail.executeBoolean(variables)){
                return true;
            } else{
                return false;
            }
        }
        return boolTerm.executeBoolean(variables);
    }
}
