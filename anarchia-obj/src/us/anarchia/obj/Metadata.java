/*
 * Copyright 2010 Laramie Crocker and tagland.org. This file is part of the software tagland.org.
 *    It is licensed under the GNU Affero General Public License.  You may read the software license in the project distribution called LICENSE.txt or here: http://www.gnu.org/licenses/
 *    Source files may be downloaded from https://sourceforge.net/projects/tagland
 */
package us.anarchia.obj;


public class    Metadata extends Storable {

	public Metadata() {
		author = new Author();
		
		copyright = new Copyright();
		copyright.setDate(Date.createDateCreated());
		
		dates = new StorableComposite();
		dates.addElement(Date.createDateCreated());
		
		adNasty = new AdNasty();
	}
	
	public Author author;  //for single case, just add to element zero.
	
	public Copyright copyright;
	
	public StorableComposite dates;
	
	public AdNasty adNasty;
	
	
	
	// = new StorableComposite or List<Date>; //list of date objects, each with an string attribute that says what kind of date (modified, etc.)
	
	//a nice pattern would be to put the expected values and types of a list
	//    on an attribute object that hung around in StorableComposites' attributes list.
			/*
			MetaList
				IS_A List
				Object[] objects/Elements[]
				Attributes[]  //attributes persist even if children empty.
				*/ 
	public String toString(){
		StringBuffer res = new StringBuffer("Metadata["+getID()+"]");
		
		return res.toString();
	}

}
