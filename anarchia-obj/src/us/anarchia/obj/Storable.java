/*
 * Copyright 2010 Laramie Crocker and tagland.org. This file is part of the software tagland.org.
 *    It is licensed under the GNU Affero General Public License.  You may read the software license in the project distribution called LICENSE.txt or here: http://www.gnu.org/licenses/
 *    Source files may be downloaded from https://sourceforge.net/projects/tagland
 */
package us.anarchia.obj;


import com.google.gwt.user.client.rpc.IsSerializable;

public class Storable implements IsSerializable {
    /** You can call this constructor with any system-wide unique id, such as you'd get
     *   from us.anarchia.util.StorageID.getStorageID() .
     */
	public Storable(String id){
		super();
        this.id = id;
	}

    public Storable(){
		super();
	}

	private String id;
	public String getID() {
		return id;
	}
	public void setID(String value) {
		id = value;
	}

	private Metadata metadata;
	
	public Metadata getMetadata() {
		return metadata;
	}
	public void setMetadata(Metadata value) {
		metadata = value;
	}
	

}
