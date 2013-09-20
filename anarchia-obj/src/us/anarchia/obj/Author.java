/*
 * Copyright 2010 Laramie Crocker and tagland.org. This file is part of the software tagland.org.
 *    It is licensed under the GNU Affero General Public License.  You may read the software license in the project distribution called LICENSE.txt or here: http://www.gnu.org/licenses/
 *    Source files may be downloaded from https://sourceforge.net/projects/tagland
 */
package us.anarchia.obj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*import org.tagland.retro.gui.output.HtmlStrategy;
import org.tagland.retro.gui.output.Output;
import org.tagland.retro.gui.output.RetroDump;
import org.tagland.retro.gui.output.StdioStrategy;
import org.tagland.retro.gui.output.Strategy;
import org.tagland.retro.gui.output.StringBufferOutput;
*/

public class Author extends StorableComposite {
    //implements IDesignable {
	public Author(){
		super();
	}
	//ID comes from Storable
	private String familiarName;
	public String getFamiliarName() { return familiarName; }
	public void setFamiliarName (String value){ familiarName = value; }

	private String fullName;
	public String getFullName() {return fullName;}
	public void setFullName(String value) {fullName = value;}

    private Copyright copyright;
    public Copyright getCopyright() {
        return copyright;
    }
    public void setCopyright(Copyright copyright) {
        this.copyright = copyright;
    }



	public String toString(){
		return "Author["+getID()+"];"+fullName+':'+familiarName+";websites:{"+websites+'}';
	}
	
	public String dump(){
		/*
		 StringBuffer sb = new StringBuffer();
		 
		StringBufferOutput out = new StringBufferOutput(sb);
		Strategy strategy = new HtmlStrategy();
		strategy.indentString = "    ";
		//Strategy strategy = dump.getStrategy();
		RetroDump dump = new RetroDump(strategy, out); 		
		
		dump.print(strategy.blockBegin);
		dump.println("Author["+getID()+"];"+fullName+':'+familiarName+';');
		dump.print(strategy.linestart);
			dump.print("websites:{");
			dump.print(strategy.newline);
			
			strategy.incIndent();
			try {
				dump.print(strategy.getIndent());
				dump.print(strategy.blockBegin);
				for (Link link : websites) {
					dump.println(link);
				}
				dump.print(strategy.blockEnd);
			} finally {
				strategy.decIndent();
			}
			dump.print(strategy.newline);
			dump.print("}");
		dump.print(strategy.lineend);
		dump.print(strategy.blockEnd);
		System.out.println(dump.getOutput());
		return dump.getOutput();
		*/
		return "Not implemented.";
	}
	
	private List<Link> websites = new ArrayList<Link>();
	public List<Link> getWebsites() {
		return websites;
	}
	protected void setWebsites(List<Link> value) {
		websites = value;
	}
	
	public void addWebsite(Link value){
		websites.add(value);
	}
	public boolean removeWebsite(Link value){
		return websites.remove(value);
	}
	/** @return null if not found 
	 */
	public Link findWebsite(Link qbe){
		int i = websites.indexOf(qbe);
		if (i>-1){
			return websites.get(i);
		}
		return null;
	}
     public class View {
         public String[] regularProperties = {"copyright", "familiarName", "fullName"};
         public Map<String,MapProperty> getMapProperties(){
             Map<String,MapProperty> map = new  HashMap<String,MapProperty>();
             map.put("websites", new MapProperty(){
                  public  void add(Object item){}
                  public void remove(Object item){}
             });
             return map;
         }
     }
     public class MapProperty {
         public  void add(Object item){}
     }
     public class Websites extends MapProperty {
         public  void add(Object item){}
         public void remove(Object item){}
         public Object get(Object key){return null;}
         public Object createItem(){return new Object();}


     }

   /*  random thoughts... these aren't even legal java.  I was just musing
       on how to create an interface that would allow you to wrap up the
       collection and target of contained types and have one manager object
       for that property.
    public Designer getDesigner(){

        scalarProperties: { String fullName, String familiarName, Copyright copyright }
        property: websites {
            List getter getWebsites();
            setter(List<Link> setWebsites(List<Link>));
            add:
            remove:
            factory: createLink();

        }
        property Link {
              getter : get
        }
    */

}
	
	
	

