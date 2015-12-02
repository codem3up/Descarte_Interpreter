import java.util.ArrayList;

/**
 * Created by andy on 11/22/2015.
 */
public class stmtList implements Expression {
    stmt statement = null;
    stmtTail statementTail = null;
    boolean valid = false;
    boolean executable = true;

    public stmtList(TokenList tokens){
        System.out.println("StmtLIST-----");
        tokens.printList();
        System.out.println();

        String newStatement = tokens.get(0);
        int beginTail = -1;

        switch(newStatement){
            case "IF":
                beginTail = tokens.indexOf("FI");
                boolean complete = false;
                int count = 0;
                int index = 0;
                int location = -1;
                for(String s : tokens){
                    if(s.equals("IF")){
                        count++;
                    } else if(s.equals("FI")){
                        count--;
                    }
                    if(count == 0 && s.equals("FI")){
                        if(!complete) {
                            location = index;
                            complete = true;
                        }
                    }
                    index++;
                }
                if(location != -1){
                    statement = new stmt(tokens.between(0, location + 1));
                    statementTail = new stmtTail(tokens.between(location +1, tokens.size()));
                }
                break;
            case "LOOP":
                beginTail = tokens.indexOf("REPEAT");
                statement = new stmt(tokens);
                statementTail = new stmtTail();
                break;
            default:
                beginTail = tokens.indexOf(";");
                if(beginTail == -1){
                    statement = new stmt(tokens);
                    statementTail = new stmtTail();
                }
                else{
                    statement = new stmt(tokens.between(0, beginTail));
                    statementTail = new stmtTail(tokens.between(beginTail, tokens.size()));
                }
                break;
        }
    }

    public stmtList(){
        valid = true;
        executable = false;

    }

    public void execute(Variables variables){
        statement.execute(variables);
        statementTail.execute(variables);
    }


    public void executeLoop(Variables variables){
        boolean complete = false;
        while(!complete){
            if(statement.executeLoop(variables)){
                complete = true;
            }
            if(statementTail.executeLoop(variables)){
                complete = true;
            }
        }
    }

    public boolean executeStmtListLoop(Variables variables){
        return statement.executeLoop(variables) || statementTail.executeLoop(variables);
    }

    @Override
    public boolean isValid() {
        System.out.println("CHECKING STMTLIST ISVALID()");
        if(statement == null || statementTail == null){
            if(valid){
                return true;
            }
            return false;
        }
        else{
            if(statement.isValid() && statementTail.isValid()){
                return true;
            }
        }
        System.out.println("STMTLIST NOT VALID");
        return false;
    }
}
