/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dbtester;

import com.bul7.exception.ValidationException;

/**
 *
 * @author ivan
 */
public class ColByColDataMatchValidator extends Validator {
    private String expect;
    private String actual;
    private String[] expectColumns;
    private String[] actualColumns;

    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }

    public String[] getActualColumns() {
        return actualColumns;
    }

    public void setActualColumns(String string) {
        if (string == null || string.trim().length() == 0)  {
            actualColumns = null;
        } else {
            this.actualColumns = string.split(",");
        }
    }

    public String getExpect() {
        return expect;
    }

    public void setExpect(String expect) {
        this.expect = expect;
    }

    public String[] getExpectColumns() {
        return expectColumns == null ? actualColumns : expectColumns;
    }

    public void setExpectColumns(String string) {
       if (string == null || string.trim().length() == 0)  {
            expectColumns = null;
        } else {
            this.expectColumns = string.split(",");
        }
    }
    
    public void setColumns(String columns) {
        setExpectColumns(columns);
        setActualColumns(columns);
    }
    
    public void validate() throws ValidationException {
        if (expect == null || expect.trim().length() == 0) {
            throw new ValidationException("Empty 'expect' source.");
        }
        if (actual == null || actual.trim().length() == 0) {
            throw new ValidationException("Empty 'actual' source.");
        }
        if (getActualColumns().length != getExpectColumns().length) {
            throw new ValidationException("Expected column list has different size than actual column list.");
        }
    }
    
    @Override
    protected String doGetSql() {
        StringBuilder sql = new StringBuilder(3096);
        for (int i = 0; i < getActualColumns().length; i++) {
            if (i > 0)  {
                sql.append("\nUNION ALL\n\n");
            }
            sql.append(getColumnTest(getExpectColumns()[i], getActualColumns()[i]));
        }
        return sql.toString();
    }
    
    private String getColumnTest(String expectCol, String actualCol) {
        String expectColSrc = String.format("SELECT %s FROM %s",
                expectCol,
                makeSource(expect));
        String actualColSrc = String.format("SELECT %s FROM %s",
                actualCol,
                makeSource(actual));
        return
"SELECT\n" +
"   CAST('Column ''" + actualCol + "'' doesn''t match' AS VARCHAR(255)) AS message\n" +
"FROM \n" +
"   (\n" +
"      (\n" +
"         " + expectColSrc + "\n" +
"         MINUS\n" +
"         " + actualColSrc + "\n" +
"      ) UNION ALL (\n" +
"         " + actualColSrc + "\n" +
"         MINUS\n" +
"         " + expectColSrc + "\n" +
"      )\n" +
"   ) d\n" +
"GROUP BY message\n" +
"HAVING COUNT(*) > 0\n"
        ;
    }

    @Override
    public String toString() {
        return "ColByColDataMatchValidator{" + "expect=" + expect + ", actual=" + actual + ", expectColumns=" + (expectColumns == null ? null : expectColumns.length) + 
                ", actualColumns=" + (actualColumns == null ? null : actualColumns.length) + '}' + super.toString();
    }

}
