/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dbtester;

import com.bul7.exception.DefinitionException;
import com.bul7.factory.json.JsonObjectFactory;
import org.json.JSONObject;

/**
 *
 * @author ivan
 */
public class JsonExecutorFactory {
    private static JsonExecutorFactory inst = new JsonExecutorFactory();

    public static JsonExecutorFactory instance() {
        return inst;
    }

    public Executor getExecutor(JSONObject json) throws DefinitionException {
        String executorType = null;
        try {
            executorType = json.getString("type");
            Class ec = Class.forName("dbtester." + executorType + "Executor");
            
            Executor tc = (Executor) JsonObjectFactory.instance().getObject(ec, json);
            return tc;
        } catch (ClassNotFoundException ex) {
            throw new DefinitionException(String.format("Unknown executor %s", executorType), ex);
        } catch (Exception ex) {
            throw new DefinitionException(ex.getMessage(), ex);
        }
    }

}
