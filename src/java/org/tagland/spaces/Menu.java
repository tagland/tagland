/*
 * Copyright 2010 Laramie Crocker and tagland.org. This file is part of the software tagland.org.
 *    It is licensed under the GNU Affero General Public License.  You may read the software license in the project distribution called LICENSE.txt or here: http://www.gnu.org/licenses/
 *    Source files may be downloaded from https://sourceforge.net/projects/tagland
 */
package org.tagland.spaces;

import com.dynamide.interpreters.IConsole;
import com.dynamide.interpreters.NullConsole;
import org.tagland.Base;
import org.tagland.spaces.MenuItem;

import java.util.ArrayList;
import java.util.List;

/** This class is a utility, not designed to be persisted.
 *   MenuItem is a persistent class.
 *   Build a Menu to deal with MenuItem at runtime.
 *   Deprecated: use MenuManager now.
 */
@Deprecated
public class Menu extends Base {

    public Menu(MenuItem root){
        this.root = root;
    }
    private MenuItem root;

    public MenuItem getRoot() {
        return root;
    }

    public void setRoot(MenuItem root) {
        this.root = root;
    }

    private Sandbox sandbox;
    public Sandbox getSandbox() {
        return sandbox;
    }

    public void setSandbox(Sandbox sandbox) {
        this.sandbox = sandbox;
    }

    public String showMenu(){
        if (root==null) return "";
        StringBuffer res = new StringBuffer();
        for (MenuItem mi: root.getMenuItems()){
            res.append(mi.key+'['+mi.getShortcut()+"]="+mi.description+"\r\n");
        }
        return res.toString();
    }

    public Object fireMenuKey(String key)  throws Exception{
        if (null==sandbox){
            throw new Exception("sandbox is null");
        }
        for (MenuItem mi: root.getMenuItems()){
            if (key.equals(mi.key)){
                ICommand cmd = mi.getCommand();
                if (cmd != null){
                    return cmd.execute(sandbox);
                } else {
                    return "IMenuCommand not found in MenuItem: "+mi;
                }
            }
        }
        return "MenuItem not found for key: "+key;

    }

    


}

