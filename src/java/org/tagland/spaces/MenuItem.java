/*
 * Copyright 2010 Laramie Crocker and tagland.org. This file is part of the software tagland.org.
 *    It is licensed under the GNU Affero General Public License.  You may read the software license in the project distribution called LICENSE.txt or here: http://www.gnu.org/licenses/
 *    Source files may be downloaded from https://sourceforge.net/projects/tagland
 */
package org.tagland.spaces;

import org.tagland.Base;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class MenuItem extends Base implements IPersist, IWire {
    public String key;
    public String shortcut;
    public String description;
    public String htmlHelp;
    public String htmlHelpURL;
    public ICommand command;
    private List<MenuItem> menuItems = new ArrayList<MenuItem>();

    public String toString(){
        return "MenuItem: "+key+','+shortcut+','+description
                +",command:: \r\n========\r\n"+command
                +"\r\n"
                +"items::\r\n"+menuItems;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHtmlHelp() {
        return htmlHelp;
    }

    public void setHtmlHelp(String htmlHelp) {
        this.htmlHelp = htmlHelp;
    }

    public String getHtmlHelpURL() {
        return htmlHelpURL;
    }

    public void setHtmlHelpURL(String htmlHelpURL) {
        this.htmlHelpURL = htmlHelpURL;
    }

    public ICommand getCommand() {
        return command;
    }

    public void setCommand(ICommand command) {
        this.command = command;
    }
}
