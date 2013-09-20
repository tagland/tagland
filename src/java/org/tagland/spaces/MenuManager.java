/*
 * Copyright 2010 Laramie Crocker and tagland.org. This file is part of the software tagland.org.
 *    It is licensed under the GNU Affero General Public License.  You may read the software license in the project distribution called LICENSE.txt or here: http://www.gnu.org/licenses/
 *    Source files may be downloaded from https://sourceforge.net/projects/tagland
 */
package org.tagland.spaces;

import org.tagland.Base;

import java.util.Map;

/** This class is a utility, not designed to be persisted.
 *   MenuItem is a persistent class.
 *   Build a MenuManager to deal with MenuItem at runtime.
 */
public class MenuManager extends Base {

    public MenuManager(MenuItem root){
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

    // TODO: make setter private and only setable via constructor.
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

    public Object fireMenuKey(String key, InputObject inputObject)  throws Exception{
        if (null==sandbox){
            throw new Exception("sandbox is null");
        }
        for (MenuItem mi: root.getMenuItems()){
            if (key.equals(mi.key)){
                ICommand cmd = mi.getCommand();
                if (cmd != null){
                    cmd.setInputObject(inputObject);
                    return cmd.execute(sandbox);
                } else {
                    return "ICommand not found in MenuItem: "+mi;
                }
            }
        }
        return "MenuItem not found for key: "+key;

    }

    /** For use by a bsh command, to call back and have the MenuManager
     *  select a new submenu.  The MenuManager can then manage the menu stack.
     *
     * @param child the MenuItem to dive into if the Sandbox allows it.
     */
    public void enter(MenuItem child){
         //TODO:  sandbox.enterMenuItem(child);
    }

        /* TODO: is there then a View of the Sandbox, so menus can manipulate
                some presentation model, e.g. the sandbox, or a presentation
                object put in the sanbox??
        sandbox
            .presentation
                .showDialog
                .askQuestion
                .setPrompt
                .setHint
                .setValidator

        */

    


}

