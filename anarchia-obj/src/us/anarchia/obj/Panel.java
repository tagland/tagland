/*
 * Copyright 2010 Laramie Crocker and tagland.org. This file is part of the software tagland.org.
 *    It is licensed under the GNU Affero General Public License.  You may read the software license in the project distribution called LICENSE.txt or here: http://www.gnu.org/licenses/
 *    Source files may be downloaded from https://sourceforge.net/projects/tagland
 */
package us.anarchia.obj;

public class Panel extends StorableComposite {
	private Author author;
	public Author getAuthor() {
		return author;
	}
	private void setAuthor(Author value) {
		author = value;
	}

	private Copyright copyright;
	public Copyright getCopyright() {
		return copyright;
	}
	private void setCopyright(Copyright value) {
		copyright = value;
	}
	
	private ImageGroup imageGroup = new ImageGroup();
	public ImageGroup getImageGroup(){
		return imageGroup;
	}
	public void addImage(Image img){
		getImageGroup().addImage(img);
	}
	public String getDefaultImageURL() {
		return imageGroup.getDefaultImage().getImgURL();
	}
	
	public String toString(){
		StringBuffer res = new StringBuffer("Panel["+getID()+"]");
		res.append("\r\n\tImages:");
		res.append("\r\n\t\t").append(imageGroup);
		return res.toString();
	}
	
	
	public String getImageURLForBounds(int width, int height){
		//TODO: loop over images and return the one that fits.
		//return "http://imgserver.com/1234.jpg";
		return imageGroup.getImageURLForBounds(width, height);
	}
	
	
	

}
