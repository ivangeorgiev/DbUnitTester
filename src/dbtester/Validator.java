/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dbtester;

import com.bul7.sql.DataSourceDescriptor;

/**
 *
 * @author ivan
 */
public abstract class Validator {
    private static int instanceCount = 0;
    private String name = this.getClass().getSimpleName() + (++instanceCount);
    private TestCase testCase;

    public TestCase getTestCase() {
        return testCase;
    }

    public void setTestCase(TestCase testCase) {
        this.testCase = testCase;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Validator{" + "name=" + name + '}';
    }
    
    public String getSql() {
        return doGetSql();
    }
    
    
    protected String makeSource(String dataSourceName) {
        DataSourceDescriptor desc = getTestCase().sourceDescriptors().get(dataSourceName);
        String source = desc.getSource();
        String filter = desc.getFilter();
        
        if (filter != null && filter.trim().length() > 0) {
            source += " WHERE " + filter;
        }        
        return source;
    }
    
    protected abstract String doGetSql();
    
}
