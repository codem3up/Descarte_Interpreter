/**
 * Created by Andrew Becker and Costadinos Argiris
 * Class: relationOption
 * This class is used as part of a node to build out the grammar for performing boolean statements
 */

public class relationOption implements Expression{
    arithExpr arithmeticExpression;
    boolean executable = true;
    boolean valid;

    public enum Type{
        NONE,
        LESSTHAN,
        LESSTHANEQUALS,
        GREATERTHAN,
        GREATERTHANEQUALS,
        EQUALS,
        LESSTHANGREATERTHAN,
        NOTEQUALTO
    }

    public Type type;

    public relationOption(TokenList tokens){
        System.out.print("RELATIONOPTION: ");
        tokens.printList();

        int lessThan = tokens.indexOf("<");
        int lessThanEquals = tokens.indexOf("<=");
        int equals = tokens.indexOf("=");
        int greaterThanEquals = tokens.indexOf(">=");
        int greaterThan = tokens.indexOf(">");
        int lessThanGreaterThan = tokens.indexOf("<>");
        int notEqualTo = tokens.indexOf("!=");

        if(lessThan == 0 || lessThanEquals == 0 || equals == 0
                || greaterThanEquals == 0 || greaterThan == 0 || lessThanGreaterThan == 0 || notEqualTo == 0){
            String temp = tokens.get(0);
            switch(temp){
                case "<":
                    type = Type.LESSTHAN;
                    break;
                case "<=":
                    type = Type.LESSTHANEQUALS;
                    break;
                case ">":
                    type = Type.GREATERTHAN;
                    break;
                case "=":
                    type = Type.EQUALS;
                    break;
                case ">=":
                    type = Type.GREATERTHANEQUALS;
                    break;
                case "<>":
                    type = Type.LESSTHANGREATERTHAN;
                    break;
                case "!=":
                    type = Type.NOTEQUALTO;
                    break;
            }
            arithmeticExpression = new arithExpr(tokens.between(1, tokens.size()));


        }
    }

    public relationOption(){
        valid = true;
        executable = false;
    }

    @Override
    public boolean isValid()
    {
        if(valid){
            return true;
        }
        if(arithmeticExpression.isValid()){
            return true;
        }
        System.out.println("RELATION OPTION NOT VALID");
        return false;
    }

    public double executeDouble(Variables variables) {
        return arithmeticExpression.executeDouble(variables);
    }
}
