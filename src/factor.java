/**
 * Created by Andrew Becker and Costadinos Argiris
 * Class: factor
 * This class is a node used to add the parenthesis to the programming language
 * Note: This class contains only one type of either an expression, atom, or type
 */
public class factor implements Expression{
    public enum Type{
        FACTOR,
        EXPRESSION,
        ATOM
    }

    factor factor;
    expr expression;
    atom atom;
    Type type;

    public factor(TokenList tokens){
        System.out.print("FACTOR: ");
        tokens.printList();

        if(tokens.get(0).equals("-")){
            type = Type.FACTOR;
            tokens.remove(0);
            factor = new factor(tokens);
        } else if((tokens.get(0).equals("(") && tokens.get(tokens.size()-1).equals(")"))){
            type = Type.EXPRESSION;
            expression = new expr(tokens.between(1, tokens.size()-1));
            System.out.println("Test2");
        } else{
            type = Type.ATOM;
            atom = new atom(tokens);
        }
    }


    @Override
    public boolean isValid() {
        System.out.println("CHECKING FACTOR ISVALID()");
        switch(type){
            case FACTOR:
                if(factor.isValid()){
                    return true;
                }
                break;
            case EXPRESSION:
                if(expression.isValid()){
                    return true;
                }
                break;
            case ATOM:
                if(atom.isValid()){
                    return true;
                }
                break;
        }
        System.out.println("FACTOR NOT VALID");
        return false;
    }

    public double executeDouble(Variables variables) {
        switch(type){
            case FACTOR:
                return factor.executeDouble(variables);
            case EXPRESSION:
                return expression.executeDouble(variables);
            case ATOM:
                return atom.executeDouble(variables);
        }
        return 0;
    }
}
