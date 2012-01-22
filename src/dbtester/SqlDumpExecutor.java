/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dbtester;

/**
 *
 * @author ivan
 */
public class SqlDumpExecutor extends Executor {
    private int callNumber = 0;
           
    
    @Override
    protected void doExecute(Validator v) {
        if (callNumber++ > 0) {
            System.out.println("\nUNION ALL\n");
        }
        System.out.println(v.getSql());
    }
    
}
