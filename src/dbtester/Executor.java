/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dbtester;

/**
 *
 * @author ivan
 */
public abstract class Executor {
    private TestCase testCase;
    private int level = 0;

    public int getLevel() {
        return level;
    }

    public TestCase getTestCase() {
        return testCase;
    }
    
    public void execute(TestCase tc) {
        TestCase prevTestCase = testCase;
        int prevLevel = level++;
        try {
            int valIndex = 0;
            beforeExecute(tc);
            level++;
            for(Validator validator: tc.validators()) {
                doExecute(validator);
            }
            level--;
            for(TestCase childTestCase: tc.testCases()) {
                execute(childTestCase);
            }
            afterExecute(tc);
        } finally {
            level = prevLevel;
            testCase = prevTestCase;
        }
    }
    
    
    protected void beforeExecute(TestCase tc) {
        
    }
    
    protected void afterExecute(TestCase tc) {
        
    }
    
    protected abstract void doExecute(Validator v);
}
