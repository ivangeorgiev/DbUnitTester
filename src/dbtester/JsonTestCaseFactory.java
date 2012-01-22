/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dbtester;

import com.bul7.exception.DefinitionException;
import com.bul7.factory.json.JsonObjectFactory;
import com.bul7.sql.DataSourceDescriptor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author ivan
 */
public class JsonTestCaseFactory {

    private static final String FLD_SOURCES = "sources";
    private static final String FLD_VALIDATORS = "validators";
    private static final String FLD_TESTCASES = "testcases";
    private static JsonTestCaseFactory inst = new JsonTestCaseFactory();

    public static JsonTestCaseFactory instance() {
        return inst;
    }

    public TestCase getTestCase(JSONObject json) throws DefinitionException {
        TestCase tc = (TestCase) JsonObjectFactory.instance().getObject(TestCase.class, json);
        try {
            setupSources(tc, json);
            setupTestCases(tc, json);
            setupValidators(tc, json);
        } catch (Exception ex) {
            throw new DefinitionException(ex.getMessage(), ex);
        }
        return tc;
    }

    private void setupSources(TestCase tc, JSONObject json) throws JSONException, DefinitionException {
        if (!json.has(FLD_SOURCES)) {
            return;
        }
        
        JSONArray a = json.getJSONArray(FLD_SOURCES);
        
        for(int i = 0; i < a.length(); i++) {
            DataSourceDescriptor cd = (DataSourceDescriptor) JsonObjectFactory.instance().getObject(DataSourceDescriptor.class, a.getJSONObject(i));
            tc.addSourceDescriptor(cd);
        }

    }

    private void setupValidators(TestCase tc, JSONObject json) throws JSONException, DefinitionException {
        if (!json.has(FLD_VALIDATORS)) {
            return;
        }
        JSONArray cj = json.getJSONArray(FLD_VALIDATORS);

        for (int i = 0; i < cj.length(); i++) {
            JSONObject vj = cj.getJSONObject(i);
            String validatorType = vj.getString("type");

            try {
                Class c = Class.forName("dbtester." + validatorType + "Validator");
                Validator v = (Validator) JsonObjectFactory.instance().getObject(c, vj);

                tc.addValidator(v);
            } catch (ClassNotFoundException ex) {
                throw new DefinitionException("Unknown validtor '" + validatorType + "'");
            }

        }
    }

    private void setupTestCases(TestCase tc, JSONObject json) throws JSONException, DefinitionException {
        if (!json.has(FLD_TESTCASES)) {
            return;
        }

        JSONArray cj = json.getJSONArray(FLD_TESTCASES);
        for (int i = 0; i < cj.length(); i++) {
            tc.addTestCase(getTestCase(cj.getJSONObject(i)));
        }

    }
}
