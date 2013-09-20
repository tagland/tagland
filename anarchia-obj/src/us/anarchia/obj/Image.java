/*
 * Copyright 2010 Laramie Crocker and tagland.org. This file is part of the software tagland.org.
 *    It is licensed under the GNU Affero General Public License.  You may read the software license in the project distribution called LICENSE.txt or here: http://www.gnu.org/licenses/
 *    Source files may be downloaded from https://sourceforge.net/projects/tagland
 */
package us.anarchia.obj;


public class Image extends Storable {
	
	
	public Image() {
	}
	
	private String imgURL;

	public String getImgURL() {
		return imgURL;
	}

	public void setImgURL(String value) {
		imgURL = value;
	}
	
	private int width;

	/** @return width in pixels */
	public int getWidth() {
		return width;
	}

	public void setWidth(int pixels) {
		width = pixels;
	}	
	
	private int height;

	/** @return height in pixels */
	public int getHeight() {
		return height;
	}

	public void setHeight(int pixels) {
		height = pixels;
	}
	
	private int dpi;

	public int getDPI() {
		return dpi;
	}

	public void setDPI(int pixelsPerInch) {
		dpi = pixelsPerInch;
	}
	
	private String caption;
	
	public String getCaption() {
		return caption;
	}

	private void setCaption(String value) {
		caption = value;
	}
	
	private String metadataKey;

	/** @return the key that a caller may set on this image to distinguish it in the Metadata object, 
	 * which may be shared by others in this Composite's elements list.
	 */
	public String getMetadataKey() {
		return metadataKey;
	}

	public void setMetadataKey(String value) {
		metadataKey = value;
	}
	
	public String toString(){
		StringBuffer res = new StringBuffer("Image["+getID()+"]");
		res.append("\r\n\tURL:").append(getImgURL());
		res.append("\r\n\tSize:").append(width).append(',').append(height);
		res.append("\r\n\tDPI:").append(dpi);
		res.append("\r\n\tCaption:").append(caption);
		res.append("\r\n\tMetadata:").append(getMetadata());
		return res.toString();
	}
	
	
	public String getImageURLForBounds(int width, int height){
		return "http://imgserver.com/1234.jpg";
	}
	
	
}
