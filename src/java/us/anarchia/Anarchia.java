/*
 * Copyright 2010 Laramie Crocker and tagland.org. This file is part of the software tagland.org.
 *    It is licensed under the GNU Affero General Public License.  You may read the software license in the project distribution called LICENSE.txt or here: http://www.gnu.org/licenses/
 *    Source files may be downloaded from https://sourceforge.net/projects/tagland
 */
package us.anarchia;

import us.anarchia.obj.Author;
import us.anarchia.obj.Copyright;
import us.anarchia.obj.Frame;
import us.anarchia.obj.Panel;
import org.tagland.spaces.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class Anarchia {
		public String ID = "AnarchiaROOT";
		public Anarchia(){
			super();
			System.out.println("--------creating frames...");
			authors = new ArrayList<Author>();
			panels = new ArrayList<Panel>();
			frames = new ArrayList<Frame>();
		}
		public List<Author> authors;
		public void addAuthor(Author a){
			authors.add(a);
		}
        //todo: single author and single copyright are for gui testing only
       private Author author;
       public Author getAuthor() {
          return author;
       }
       public void setAuthor(Author author) {
           this.author = author;
       }
        private Copyright copyright;
        public Copyright getCopyright() {
            return copyright;
        }
        public void setCopyright(Copyright copyright) {
            this.copyright = copyright;
        }




		private List<Panel> panels;
		public List<Panel> getPanels() {
			return panels;
		}
		public void addPanel(Panel value) {
			panels.add(value);
		}

		private List<Frame> frames;
		public List<Frame> getFrames() {
			return frames;
		}
		public void addFrame(Frame value) {
			frames.add(value);
		}


        private MenuItem menu;
        public MenuItem getMenu() {
            return menu;
        }

        public void setMenu(MenuItem menu) {
            this.menu = menu;
        }



		public String toString(){
			StringBuffer res = new StringBuffer("Anarchia["+ID+"]");
			res.append("\r\n\tAuthors:");
			for (Author a: authors){
			    res.append("\r\n\t\t")
			       .append(a);
			}
			res.append("\r\n\tPanels:");

			for (Panel p: panels){
			    res.append("\r\n\t\t")
			       .append(p);
			}
			res.append("\r\n\tFrames:");

			for (Frame f: frames){
			    res.append("\r\n\t\t")
			       .append(f);
			}

            res.append("\r\n\tMenuManager:"+getMenu());

			return res.toString();
		}
	}