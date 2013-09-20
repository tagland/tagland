/*
 * Copyright 2010 Laramie Crocker and tagland.org. This file is part of the software tagland.org.
 *    It is licensed under the GNU Affero General Public License.  You may read the software license in the project distribution called LICENSE.txt or here: http://www.gnu.org/licenses/
 *    Source files may be downloaded from https://sourceforge.net/projects/tagland
 */
package us.anarchia.obj;

import java.util.ArrayList;
import java.util.List;

public class ImageGroup extends StorableComposite {
	
	/*	useful for grouping Image objects, reason==container, sizes, layout, etc.is composite, can hold group or image, 
	 * which are both image group
	 */
	//todo: figure out if things like Caption are an attribute or a property with getter/setter.
		
	private List<Image> images = new ArrayList<Image>();
	
	public List<Image> getImages() {
		return images;
	}

	public void addImage(Image img) {
		images.add(img);
	}
	
	public String toString(){
		StringBuffer res = new StringBuffer("ImageGroup["+getID()+"]");
		res.append("\r\n\tImages:");
		for (Image i: images){
		    res.append("\r\n\t\t")  
		       .append(i);
		}
		res.append("\r\n\tDefault Image:");
		res.append(""+defaultImage);
		res.append("\r\n\tMetadata:");
		res.append(""+getMetadata());		
		return res.toString();
	}
	
	private Image defaultImage;
	public Image getDefaultImage(){
		return defaultImage;
	}
	public void setDefaultImage(Image value){
		defaultImage = value;
	}
	
	public String getImageURLForBounds(int width, int height){
		Image img = getImageForBounds(width, height);
		if (img != null){
			return img.getImgURL();
		} else {
			return "http://imgserver.com/1234.jpg";  //TODO.
		}
	}
	
	/** @return null if couldn't find an image that fit in the bounds. */
	public Image getImageForBounds(int width, int height){
		Image bestImg = null;
		int w, h;
		int bestW = 0, bestH = 0;
		for (Image i: images) {
			w = i.getWidth();
			h = i.getHeight();
			if (w<width && h<height){  //are we less than both bounds?
				if (w>bestW && h>bestH){   //are we bigger than the previous best that was less than the bounds?
					bestW = w;
					bestH = h;
					bestImg = i;
				}
			}
		}
		return bestImg;		
	}
	

}
