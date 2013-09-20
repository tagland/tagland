/*
 * Copyright 2010 Laramie Crocker and tagland.org. This file is part of the software tagland.org.
 *    It is licensed under the GNU Affero General Public License.  You may read the software license in the project distribution called LICENSE.txt or here: http://www.gnu.org/licenses/
 *    Source files may be downloaded from https://sourceforge.net/projects/tagland
 */
package us.anarchia.obj;

import java.util.ArrayList;
import java.util.List;

public class Frame extends StorableComposite {
	private ArrayList<Panel> panels = new ArrayList<Panel>();
	public List<Panel> getPanel(String key) {
		return panels;
	}
	public List<Panel> getPanels() {
		return (List<Panel>)(panels.clone());
	}
	public void setPanels(List<Panel> newList) {
		panels.clear();
		panels.addAll(newList);
		return;
	}
	public void addPanel(Panel value) {
		panels.add(value);
	}
	public String toString(){
		StringBuffer res = new StringBuffer("Frame["+getID()+"]");
		res.append("\r\n\tPanels:");
		for (Panel p: panels){
		    res.append("\r\n\t\t")  
		       .append(p);
		}
		return res.toString();
	}

	

}
