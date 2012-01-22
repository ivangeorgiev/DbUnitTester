/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dbtester;

import com.bul7.exception.ValidationException;
import com.bul7.sql.DataSourceDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ivan
 */
public class TestCase {
    private static int count;
    private String name = "TestCase" + (++count);
    private Map<String, DataSourceDescriptor> sourceDescriptors = new HashMap();
    private List<TestCase> testCases = new ArrayList();
    private List<Validator> validators = new ArrayList();
    private TestCase parent;

    public TestCase getParent() {
        return parent;
    }

    public void setParent(TestCase parent) {
        this.parent = parent;
    }

    public static int getCount() {
        return count;
    }

    public Map<String, DataSourceDescriptor> sourceDescriptors() {
        return sourceDescriptors;
    }

    public List<Validator> validators() {
        return validators;
    }

    public List<TestCase> testCases() {
        return testCases;
    }

    public static void setCount(int count) {
        TestCase.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void addTestCase(TestCase tc) {
        testCases.add(tc);
        tc.setParent(this);
    }
    
    public void addValidator(Validator v) {
        validators.add(v);
        v.setTestCase(this);
    }
        
    public void addSourceDescriptor(DataSourceDescriptor sd) {
        sourceDescriptors.put(sd.getName(), sd);
    }
    
    public void validate() throws ValidationException {
        if (name == null || name.trim().length() == 0) {
            throw new ValidationException("'name' is not set or is empty.");
        }
    }

    @Override
    public String toString() {
        return "TestCase{" + "name=" + name + ", parent=" + parent + '}';
    }
    
}
