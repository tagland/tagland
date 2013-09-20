/*
 * Copyright 2010 Laramie Crocker and tagland.org. This file is part of the software tagland.org.
 *    It is licensed under the GNU Affero General Public License.  You may read the software license in the project distribution called LICENSE.txt or here: http://www.gnu.org/licenses/
 *    Source files may be downloaded from https://sourceforge.net/projects/tagland
 */
package us.anarchia.obj;


public class Date extends StorableComposite {
	public Date() {
	}

	public enum DateEvent {CREATED, ACCESSED, COPIED, MODIFIED, OBVIATED, DELETED };
	
	public enum CalendarType {GREGORIAN ("Gregorian"), //TODO: check to see what Date API does with this.
		                      JULIAN    ("Julian");
		private String m_value;	
		private CalendarType(String value){
			m_value = value;
		}
	}
	
	public CalendarType calendarType;
	public java.util.Date date;
	public DateEvent event;
	
	public void setToNow(){
		date = new java.util.Date();
	}
	public static java.util.Date now(){
		return new java.util.Date();
	}
	
	public static Date createDateCreated(){
		Date result = new Date();
		result.date = new java.util.Date();
		result.calendarType = CalendarType.GREGORIAN;
		result.event = DateEvent.CREATED;
		return result;
	}

}