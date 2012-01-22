/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dbtester;

import java.io.FileReader;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author ivan
 */
public class DbTester {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            printUsage();
            return;
        }
        JSONObject json = new JSONObject(new JSONTokener(new FileReader(args[0])));
        
        Executor ex = JsonExecutorFactory.instance().getExecutor(json.getJSONObject("executor"));
        TestCase tc = JsonTestCaseFactory.instance().getTestCase(json.getJSONObject("testcase"));
        
        ex.execute(tc);
    }
    
    private static void printUsage() {
        System.out.println();
        System.out.println("DB Unit Tester");
        System.out.println("   (C) 2012 Ivan Georgiev<ivan.georgiev@gmail.com>");
        System.out.println();
        System.out.println("usage:");
        System.out.println("   DbTester <filename>");
        System.out.println();
    }
}
