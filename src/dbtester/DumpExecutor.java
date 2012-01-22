/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dbtester;


/**
 *
 * @author ivan
 */
public class DumpExecutor extends Executor {
    private static final String TAB = "   ";
    
    @Override
    protected void beforeExecute(TestCase tc) {
        String prefix = getPrefix();
        System.out.println(prefix + "Test Case: " + tc);
    }

    @Override
    protected void doExecute(Validator v) {
        String prefix = getPrefix();
        System.out.println(prefix + "Validator: " + v);
    }
    
    private String getPrefix() {
        String prefix = "";
        for(int i = 1; i < getLevel(); i++) {
            prefix += TAB;
        }
        return prefix;        
    }
    
}
