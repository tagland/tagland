/*
 * Copyright 2010 Laramie Crocker and tagland.org. This file is part of the software tagland.org.
 *    It is licensed under the GNU Affero General Public License.  You may read the software license in the project distribution called LICENSE.txt or here: http://www.gnu.org/licenses/
 *    Source files may be downloaded from https://sourceforge.net/projects/tagland
 */
package us.anarchia.obj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//It's a Set with attributes, also a list, also a map, for attributes.
public class StorableComposite extends Storable {

	public StorableComposite() {
	}
	///=========== BEHAVIORS ===================================	
	public enum Behavior {LAZY,
						  IS_WRAPPER,
		                  instantiateChildElementsOnAccess,
		                  instantiateEmptySetOnNull, //return an empty set if actual storage is null in a getElements() call.
	                      singleValueActsAsZeroElement,//Useful for wrappers.
	                      PRELOAD};   
	                      /*singleValueActsAsZeroElement is a bit whack.  it means: do you want to masquerade as your child object, 
	                      e.g. implement that you are a collection, but you stand in for the first in the list.  
	                      It's like the guy who hails a cab, and not like the Maitre d' scenario.
	                      */
	private Behavior[] behaviorSet = {Behavior.LAZY};
	
	public Behavior[] getBehaviorSet(){
		return behaviorSet;
	}
	public void setBehaviorSet(Behavior[]behaviorSet){
		this.behaviorSet = behaviorSet;
	}
	public boolean isWrapper(){
		return valueInArray(Behavior.IS_WRAPPER, behaviorSet);
	}
	public static boolean valueInArray(Object value, Object[]arr){
		for (Object o: arr){
			if (o.equals(value)) return true;
		}
		return false;
	}
	
	///=========== ATTRIBUTES ===================================
	Map<String,Object> attributes = new HashMap<String,Object>();

	/** @return a reference to the storage object for attributes.  BE NICE!     */    //TODO: Fix by hide-n-clone.
	public Map<String,Object> getAttributes(){return attributes;}
	
	public void addAttribute(String key, Object value){
		attributes.put(key, value);
	}

	///=========== ELEMENTS ===================================
	private List<StorableComposite> elements;
	
	//for subclasses to access.
	protected List<StorableComposite> getElements(){
		if (elements==null){
			elements = new ArrayList<StorableComposite>();
			if (valueInArray(Behavior.IS_WRAPPER, behaviorSet)){
				//onLoadZeroElement(elements);
			}
		}
		return elements;
	}
	/** Subclasses should override if they want to be wrappers, that is, they are the 
	 * first and only element in the elements list.  You should insert your element at 
	 * zero in the list.  The list is not a clone.
	 */
	protected void onLoadZeroElement(List<StorableComposite> elements){	
	}
	
	private void initLists(){
		//if (behaviorSet == Behavior.PRELOAD) getElements();
	}
	
	//public boolean hasElements(){
	//	if (null == elements){ return false; }
	//	if (elements.size()==0){ return false;}
	//	return true;
	//}
	
	public void addElement(StorableComposite e){
		getElements().add(e);
	}
	
	public StorableComposite[] getElementArray(){
		return ((StorableComposite[])getElements().toArray());
	}
	
	public List<StorableComposite> getElementList(){
		return new ArrayList<StorableComposite>(getElements());
	}
	
	public Iterator<StorableComposite> getElementIterator(){
		return getElements().iterator();
	}
	
	
}
