/*
 * Copyright 2010 Laramie Crocker and tagland.org. This file is part of the software tagland.org.
 *    It is licensed under the GNU Affero General Public License.  You may read the software license in the project distribution called LICENSE.txt or here: http://www.gnu.org/licenses/
 *    Source files may be downloaded from https://sourceforge.net/projects/tagland
 */
package org.tagland.spaces;

import com.dynamide.interpreters.IConsole;
import com.dynamide.interpreters.NullConsole;

import java.util.HashMap;
import java.util.Map;

/** You get one of these based on your login credentials, so this object represents
 *  what you have permission to execute: Beanshell and other interpreters, exec, Filesystem, Database, Sockets, etc.
 */
public class Sandbox {
    private Map<String, Object> properties = new HashMap<String, Object>();

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public Object evalBeanshell(String source) throws Exception {
        return evalBeanshell(source, new InputObject(), new OutputObject());
    }
    public OutputObject evalBeanshell(String source,
                                InputObject input,
                                OutputObject output) throws Exception {
        if (output==null){
            output = new OutputObject();
        }
        bsh.Interpreter interp = getBeanshellInterpreter();
        interp.set("sandbox", this);  //todo: see if this is a security leak.
        interp.set("input", input);
        interp.set("output", output);

        if (properties != null) {
            for (Map.Entry<String, Object> entry : properties.entrySet()) {
                interp.set(entry.getKey(), entry.getValue());
            }
        }
        try {
            Object res = interp.eval(source);
            if (res == null) {
                res = null; // "NO RESULT";
            }
            output.setResult(res);
            return output;
        } finally {
            interp.unset("sandbox");
            if (properties != null) {
                for (Map.Entry<String, Object> entry : properties.entrySet()) {
                    interp.unset(entry.getKey());
                }
            }
        }
    }

    private IConsole m_console;
    private IConsole getConsole(){
        return m_console;
    }

    private bsh.Interpreter m_Interpreter;

    private bsh.NameSpace globalNameSpace = null;

    private static int g_count = 0;

    //for bsh 2.0
    public bsh.Interpreter getBeanshellInterpreter(){
        if (m_Interpreter == null){
            m_console = new NullConsole("null-console");
            //2.0 syntax, I haven't validated this against any sample code, but I just got it to work, on 20060924:
            String sourceFileInfo = "MenuManager-SourceFile";
            m_Interpreter = new bsh.Interpreter(m_console.getIn(),
                                                m_console.getOut(),
                                                m_console.getErr(),
                                                false,                   //interactive
                                                null,//this.globalNameSpace     //namespace
                                                null,                    //parent
                                                sourceFileInfo
                                                );
            this.globalNameSpace = new bsh.NameSpace( m_Interpreter.getClassManager(), "localDynamide"+(g_count++));
            m_Interpreter.setNameSpace(this.globalNameSpace);
            // %% NOt sure if this works, since some params are set in the constructor:   m_Interpreter.setConsole((bsh.ConsoleInterface)m_console);
            m_Interpreter.setClassLoader(getClass().getClassLoader());
            //System.out.println("BshInterpreter ["+toString()+"]new for Session: "+getSession());
        }
        return m_Interpreter;
    }

    public Object evalJexl(String jexlExpression, ICommand caller){
        String result = "EXP EVAL COMING SOON..., but for now: "+jexlExpression;
        System.out.println(result);
        return result; //TODO: implement jexl call.
    }

    public Object showMenu(String otherMenu, ICommand cmd){
        String result = "Showing other menu: "+otherMenu;
        System.out.println(result);
        return result; //TODO: implement.
    }
}
