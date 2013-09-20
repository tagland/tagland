/*
 * Copyright 2010 Laramie Crocker and tagland.org. This file is part of the software tagland.org.
 *    It is licensed under the GNU Affero General Public License.  You may read the software license in the project distribution called LICENSE.txt or here: http://www.gnu.org/licenses/
 *    Source files may be downloaded from https://sourceforge.net/projects/tagland
 */
package org.tagland.spaces;

import java.util.HashMap;
import java.util.Map;

/** abstract because it doesn't implement execute().  BeanshellCommand, for example, implements it as a subclass.
 */
public abstract class Command implements ICommand, IPersist, IWire {
    //public Object execute()  -- don't implement, leave abstract so subclasses must impl.

    private transient InputObject inputObject = new InputObject();
    private transient OutputObject outputObject = new OutputObject();

    public InputObject getInputObject() {
        return inputObject;
    }

    /** By using this method, you are removing the previous, or default InputObject,
     *  which is a Map by String of Objects.  You are welcome to install your own
     *  Input Object, or use the default Map implemented here.
     *
     * @param namedObjects You may set any Object instances, keyed by a String, on the
     * namedObjects Map, and these will be made available to the command's
     * evaluation environment, e.g. Beanshell, as global variables,
     * or webmacro, as macros in the current template.
     */
    public void setInputObject(InputObject namedObjects){
        this.inputObject = namedObjects;
    }

    public OutputObject getOutputObject() {
        return outputObject;
    }

    /** @see  #setInputObject(InputObject)
     */
    public void setOutputObject(OutputObject object){
        this.outputObject = object;
    }

    public String toString(){
        return "Command. bsh source: {{"+getSource()+"}}";
    }

    //The one-and-only source gets stored here.
    private CDataSource cdataSource;

    public CDataSource getCDataSource() {
        return cdataSource;
    }

    /** The underlying storage location for setSource as well. Ensures that
     *  the string is preserved in CDATA tags when streaming to XML.
     */
    public void setCDataSource(CDataSource cdataSource) {
        this.cdataSource = cdataSource;
    }

    /** At runtime, a java.lang.String,
     *  but at serialization time, it is a CDATA section in XML.  */
    public String getSource() {
        if (cdataSource == null)
            return "";
        return cdataSource.getSource();
    }

    /** At runtime, a java.lang.String,
     *  but at serialization time, it is a CDATA section in XML.  */
    public void setSource(String source) {
        if (cdataSource == null){
            cdataSource = new CDataSource();
        }
        cdataSource.setSource(source);
    }

    private String otherMenu = "";
    public String getOtherMenu() {
        return otherMenu;
    }

    public void setOtherMenu(String otherMenu) {
        this.otherMenu = otherMenu;
    }

    private String cmdLine;
    public String getCmdLine() {
        return cmdLine;
    }
    /** @param expression is a Jexl expression. */
    public void setCmdLine(String expression) {
        this.cmdLine = expression;
    }

}
