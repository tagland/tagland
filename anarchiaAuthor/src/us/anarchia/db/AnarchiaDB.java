package us.anarchia.db;

import java.util.UUID;

import us.anarchia.obj.Author;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.config.TSerializable;
import com.db4o.ta.TransparentActivationSupport;

public class AnarchiaDB {
	public static final String DB4OFILENAME = "/Users/laramie/tmp/_DB4o_gwt_anarchia.db"; 

	private ObjectContainer db;

    private void opendb(){
        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        config.common().objectClass("Author").cascadeOnUpdate(true);
        config.common().objectClass("Link").cascadeOnUpdate(true);
        config.common().add(new TransparentActivationSupport());
        config.common().objectClass("java.net.URL").translate(new TSerializable());
        db = Db4oEmbedded.openFile(config, DB4OFILENAME);
    }

    private void closedb(){
        db.close();
        db = null;
    }

    /** @return The ID of the stored author, or empty string if not stored. */
    public String storeAuthor(Author author){
    	opendb();
	    try {
	        ObjectSet result = db.queryByExample(author);
	        if ( !result.hasNext() ){
	        	UUID uuid = UUID.randomUUID();
	        	author.setID(uuid.toString());
	        	db.store(author);
	            db.commit();
	            System.out.println("storeAuthor() added author: "+author);
	            return author.getID();
	        } else {
	        	Author resultAuthor = (Author)result.next();
	        	System.out.println("Author alread in DB: "+resultAuthor);
	        	return resultAuthor.getID();
	        }
		}
		finally {
		    closedb();
	        System.out.println("storeAuthor DONE.");
	    }
    }
    
    public Author getAuthor(String id){
    	opendb();
	    try {
	    	Author template = new Author();
	    	template.setID(id);
	        ObjectSet result = db.queryByExample(template);
	        if ( !result.hasNext() ){
	        	template.setFamiliarName("created"+System.currentTimeMillis());
	        	return template;
	        } else {
	        	Author author = (Author)(result.next());
	        	System.out.println("Author found in DB: "+author);
	        	return author;
	        }
		}
		finally {
		    closedb();
	        System.out.println("getAuthor() DONE.");
	    }
    }

}
