/*
 * Copyright 2010 Laramie Crocker and tagland.org. This file is part of the software tagland.org.
 *    It is licensed under the GNU Affero General Public License.  You may read the software license in the project distribution called LICENSE.txt or here: http://www.gnu.org/licenses/
 *    Source files may be downloaded from https://sourceforge.net/projects/tagland
 */
package us.anarchia;

import java.util.List;
import java.util.ArrayList;

import java.net.URL;

import com.db4o.Db4o;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.config.TSerializable;
import com.db4o.ta.TransparentActivationSupport;

import us.anarchia.obj.Author;
import us.anarchia.obj.Image;
import us.anarchia.obj.Link;
import us.anarchia.obj.Panel;
import us.anarchia.obj.Frame;



public class ObjectDBMain {
	public static class Anarchia {
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
			
			return res.toString();
		}
	}

	public static final String DB4OFILENAME = "~/tmp/_DB4o/db";
	
	public static void accessDb4o() {
	ObjectContainer db=Db4o.openFile(DB4OFILENAME);
	try {
	//	 do something with db4o
	}
	finally {
	db.close();
	}
	}
    
	public static void addTwoAuthors(ObjectContainer db) throws Exception {
		Anarchia a = new Anarchia();
		a.ID = "ROOT";
		Author au1 = new Author();
		au1.setID("Laramie");
		au1.setFamiliarName("Laramie");
		au1.setFullName("Laramie Crocker");
		Link l = new Link("http://LaramieCrocker.com", "Laramie's Website");
		au1.addWebsite(l);
		a.addAuthor(au1);

		Author au3 = new Author();
		au3.setID("Laramie3");
		au3.setFamiliarName("Laramie3");
		au3.setFullName("Laramie Crocker 3");
		Link l2 = new Link("http://LaramieCrocker.com/3", "Laramie3's Website");
		au3.addWebsite(l2);
		a.addAuthor(au3);
		
		Author au2 = new Author();
		au2.setID("Mr. Cooper");
		au2.setFamiliarName("Cooper");
		au2.setFullName("Mr. Cooper");
		a.addAuthor(au2);
		
		db.store(a);
		System.out.println("addTwoAuthors Stored "+a);
	}
	
	public static void storeAnarchia(ObjectContainer db) throws Exception {
		ObjectSet result = db.queryByExample(Author.class);
		if ( !result.hasNext() ){
			addTwoAuthors(db);
		} else {
			Author lar = (Author)result.next();
			//lar.setID("Laramie2");
			lar.setFamiliarName("Laramie");
			lar.setFullName("Laramie Crocker 2");
			Link l = new Link("http://LaramieCrocker.com/2", "Laramie's Website updated");
			lar.addWebsite(l);

			db.store(lar);
			System.out.println("Stored "+lar);
			
			Panel p = new Panel();
			
			Image img = new Image();
			img.setImgURL("http://LaramieCrocker.com/1234.jpg");
			img.setWidth(600);
			img.setHeight(800);
			img.setDPI(150);
			
			p.addImage(img);
			
			Frame f = new Frame();
			f.addPanel(p);
			
			ObjectSet resultA = db.queryByExample(Anarchia.class);
			if ( resultA.hasNext() ){
				Anarchia a = (Anarchia)resultA.next();
				a.addFrame(f);
				a.addPanel(p);
				db.store(a);
				System.out.println("us.anarchia...................");
			}
			
		}
	}
	
	public static void listResult(ObjectSet result, ObjectContainer db) {
		System.out.println("result.size: "+result.size());
		while(result.hasNext()) {
			Object r = result.next();
			db.activate(r, 2);
		    System.out.println(r);
		}
	}
	public static void main(String[] args) throws Exception {
		//UNCOMMNET TO delete db and start new: 
		//new File(DB4OFILENAME).delete();
							//accessDb4o();
							//new File(DB4OFILENAME).delete();
		EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
		config.common().objectClass("Author").cascadeOnUpdate(true);
		config.common().objectClass("Link").cascadeOnUpdate(true);
		config.common().add(new TransparentActivationSupport());
		config.common().objectClass("java.net.URL").translate(new TSerializable()); 
		ObjectContainer db = Db4oEmbedded.openFile(config, DB4OFILENAME);
		//ObjectContainer db=Db4o.openFile(DB4OFILENAME);
		try {
			
			System.out.println("====== trying QBE...");
			ObjectSet result=db.queryByExample(Anarchia.class);
			listResult(result, db);
			
			
			System.out.println("====== trying QBE Inner search on Author...");
			result=db.queryByExample(Author.class);
			listResult(result, db);
			
			System.out.println("====== storing...");
			storeAnarchia(db);
			db.commit();
			
			System.out.println("====== listing results after store ...");
			result=db.queryByExample(Anarchia.class);
			listResult(result, db);
			
		}
		finally {
			db.close();
		}
	}
	

	
	//====================== EXAMPLE CODE.  NOW MOSTLY KRUFT ==========================================
	
	
	
			/*  test case with:
			 * 
			 storeFirstPilot(db);
			 storeSecondPilot(db);
			 retrieveAllPilots(db);
			 retrievePilotByName(db);
		 	 retrievePilotByExactPoints(db);
				 updatePilot(db);
			 */
			 //deleteFirstPilotByName(db);
			 //deleteSecondPilotByName(db);

		public static void deleteFirstPilotByName(ObjectContainer db) {
		ObjectSet result=db.queryByExample(new Pilot("Michael Schumacher",0));
		Pilot found=(Pilot)result.next();
		db.delete(found);
		System.out.println("Deleted "+found);
		retrieveAllPilots(db);
		}
		
		public static void deleteSecondPilotByName(ObjectContainer db) {
		ObjectSet result=db.queryByExample(new Pilot("Rubens Barrichello",0));
		Pilot found=(Pilot)result.next();
		db.delete(found);
		System.out.println("Deleted "+found);
		retrieveAllPilots(db);
		}
		
		public static void retrieveAllPilotQBE(ObjectContainer db) {
		Pilot proto=new Pilot(null,0);
		ObjectSet result=db.queryByExample(proto);
		listResult(result, db);
		}
		public static void retrieveAllPilots(ObjectContainer db) {
		ObjectSet result=db.queryByExample(Pilot.class);
		listResult(result, db);
		}
		public static void retrievePilotByExactPoints(ObjectContainer db)
		{
		Pilot proto=new Pilot(null,100);
		ObjectSet result=db.queryByExample(proto);
		listResult(result, db);
		}
		public static void retrievePilotByName(ObjectContainer db) {
		Pilot proto=new Pilot("Michael Schumacher",0);
		ObjectSet result=db.queryByExample(proto);
		listResult(result, db);
		}
		public static void storeFirstPilot(ObjectContainer db) {
		Pilot pilot1=new Pilot("Michael Schumacher",100);
		db.store(pilot1);
		System.out.println("Stored "+pilot1);
		}
		public static void storeSecondPilot(ObjectContainer db) {
		Pilot pilot2=new Pilot("Rubens Barrichello",99);
		db.store(pilot2);
		System.out.println("Stored "+pilot2);
		}
		public static void updatePilot(ObjectContainer db) {
		ObjectSet result=db.queryByExample(new Pilot("Michael Schumacher",0));
		Pilot found=(Pilot)result.next();
		found.addPoints(11);
		db.store(found);
		System.out.println("Added 11 points for "+found);
		retrieveAllPilots(db);
		}
	
}
