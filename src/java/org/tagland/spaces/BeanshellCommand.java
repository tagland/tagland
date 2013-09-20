/* Copyright 2010 Laramie Crocker and tagland.org. This file is part of the software tagland.org.
   It is licensed under the GNU Affero General Public License.  You may read the software license in the project distribution called LICENSE.txt or here: http://www.gnu.org/licenses/
   Source files may be downloaded from https://sourceforge.net/projects/tagland/  */
package org.tagland.spaces;

/**
 */
public class BeanshellCommand extends Command implements ICommand {
    public Object execute(Sandbox sandbox) throws Exception {
        String om = getOtherMenu();
        if (om!=null&&om.trim().length()>0){
            return sandbox.showMenu(om, this);
        }


        String exp = getCmdLine();
        if (exp!=null&&exp.trim().length()>0){
            return sandbox.evalJexl(exp, this);
        }


        return sandbox.evalBeanshell(getSource(), getInputObject(), getOutputObject());
    }
}
