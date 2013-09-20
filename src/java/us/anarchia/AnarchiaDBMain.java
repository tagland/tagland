/*
 * Copyright 2010 Laramie Crocker and tagland.org. This file is part of the software tagland.org.
 *    It is licensed under the GNU Affero General Public License.  You may read the software license in the project distribution called LICENSE.txt or here: http://www.gnu.org/licenses/
 *    Source files may be downloaded from https://sourceforge.net/projects/tagland
 */
package us.anarchia;
//this class came from ObjectDBMain.java, so see other examples of how to use Db40 in there.


    /*
    plug points of a command-executable, e.i. an object that is known by its
    ability to enact a menu item.  It can implement a window handle, in which
    case it can plug into Retro via pub-sub using native java calls,
    else uses xml-rpc or rmi or some-such.  Otherwise it is shelled to.
    Your executable can request and can be plugged into these "ports":
       stdin
        stdout
        macro_out
        macro_in
        env
        outs{stream or var, identifier}
        ins{stream or var, identifier}

     */
import java.io.File;
import java.util.HashMap;

import java.net.URL;
import java.util.Map;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.config.TSerializable;
import com.db4o.ta.TransparentActivationSupport;

import org.tagland.spaces.*;

import us.anarchia.obj.Author;
import us.anarchia.obj.Image;
import us.anarchia.obj.Metadata;
import us.anarchia.obj.Link;
import us.anarchia.obj.Panel;
import us.anarchia.obj.Frame;

public class AnarchiaDBMain {
    public AnarchiaDBMain(){
    }

	public static final String DB4OFILENAME = "/Users/laramie/tmp/tagland/_DB4o/db";   //"C:/tmp/_DB4o/db";   //  /Users/laramie/tmp/tagland/base/data/tagland.org/db"
    public static final String USER_MENU = "Laramie.menu";
    public static final String USER_MENU_DIR = "/Users/laramie/tmp/tagland/base/data/tagland.org/menus";

    private ObjectContainer db;

    private void opendb(){
        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        config.common().objectClass("Author").cascadeOnUpdate(true);
        config.common().objectClass("Link").cascadeOnUpdate(true);
        config.common().add(new TransparentActivationSupport());
        config.common().objectClass("java.net.URL").translate(new TSerializable());
        db = Db4oEmbedded.openFile(config, DB4OFILENAME);
        //ObjectContainer db=Db4o.openFile(DB4OFILENAME);
    }

    private void closedb(){
        db.close();
        db = null;
    }

	public static void addTwoAuthors(ObjectContainer oc) throws Exception {
		Anarchia a = new Anarchia();
		a.ID = "ROOT";
		Author au1 = new Author();
		au1.setID("Laramie");
		au1.setFamiliarName("Laramie");
		au1.setFullName("Laramie Crocker");
		Link l = new Link("http://LaramieCrocker.com", "Laramie's Website");
		au1.addWebsite(l);

        Link ld = new Link("http://dynamide.com", "dynamide website");
        au1.addWebsite(ld);

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
		
		oc.store(a);
        oc.commit();
		System.out.println("addTwoAuthors Stored "+a);
	}

    public Author getAuthor() throws Exception {
        opendb();
        try {
            ObjectSet result = db.queryByExample(Author.class);
            if ( result.hasNext() ){
                Author author = (Author)result.next();
                return author;
            }
        }finally{
            closedb();
        }
        return null;
    }

    public void storeAnarchia() throws Exception {
        opendb();
        try {
            storeAnarchia(db);
        } finally {
            closedb();
        }
    }

    /** storeAnarchia also writes a  user menu out to disk
     *
     * @see: http://xstream.codehaus.org/converter-tutorial.html
      * @throws Exception
      */
	public static void storeAnarchia(ObjectContainer oc) throws Exception {
		ObjectSet result = oc.queryByExample(Author.class);
		if ( !result.hasNext() ){
			throw new Exception("Author not found in database.  Call initdb() first.");
		} else {
			Author lar = (Author)result.next();
			//lar.setID("Laramie2");
			lar.setFamiliarName("Laramie");
			lar.setFullName("Laramie Crocker 2");
			Link l = new Link("http://LaramieCrocker.com/2", "Laramie's Website updated");
			lar.addWebsite(l);


			oc.store(lar);
			System.out.println("Stored "+lar);
		}
		
		Panel p = new Panel();
		
		Image img = new Image();
		img.setImgURL("http://LaramieCrocker.com/1234.jpg");
		img.setWidth(600);
		img.setHeight(800);
		img.setDPI(150);
		
		p.addImage(img);
		
		Frame f = new Frame();
		f.addPanel(p);
		Metadata meta = new Metadata();  //currently, automatically gets a copyright.  TODO: make this not get a copyright.
		meta.author.setFullName("Finbar McGrath");
		meta.author.setFamiliarName("Finbar");
		f.setMetadata(meta);

        Sandbox sandbox = new Sandbox();

        MenuItem root = new MenuItem();
            root.description = "Root MenuManager";
            root.key = "ROOT";
            root.setShortcut("R");

        MenuItem m = new MenuItem();
            m.description = "My First MenuManager";
            m.key = "NEW";
            m.shortcut = "N";
            Command cmd = new BeanshellCommand();
            String src = "import java.lang.System;\r\n\"CurrentTimeMillis(): \"+System.currentTimeMillis();";
            cmd.setSource(src);
            m.setCommand(cmd);
        root.getMenuItems().add(m);

        MenuItem mHN = new MenuItem();
            mHN.description = "Get Localhost hostname";
            mHN.key = "HOSTNAME";
            mHN.setShortcut("H");
            Command cmdHN = new BeanshellCommand();
            String srcHN = "import java.net.InetAddress;\r\n\"hostname: \"+InetAddress.getLocalHost().getHostName();";
            cmdHN.setSource(srcHN);
            mHN.setCommand(cmdHN);
        root.getMenuItems().add(mHN);

        MenuItem mCall = new MenuItem();
            mCall.description = "Call Other Menu";
            mCall.key = "CALL";
            mCall.setShortcut("C");
            Command cmdCALL = new BeanshellCommand();
        cmdCALL.setOtherMenu("us.anarchia.menu.welcome.NewUser");
        cmdCALL.setCmdLine("this is a command line $space.clips");
        mCall.setCommand(cmdCALL);
        root.getMenuItems().add(mCall);


        String xml = Space.menuItem2MenuBundle(root);
        com.dynamide.util.FileTools.saveFile(USER_MENU_DIR, USER_MENU, xml);
        System.out.println(xml);

		ObjectSet resultA = oc.queryByExample(Anarchia.class);
		if ( resultA.hasNext() ){
			Anarchia a = (Anarchia)resultA.next();
			a.addFrame(f);
			a.addPanel(p);
            a.setMenu(root);
			oc.store(a);
            oc.commit();
			System.out.println("anarchia...................");
		}
	}

    /** This reads menus from disk as XML
        and puts them into db4o and returns the MenuItem it created in memory.  */
    public MenuItem readMenus() throws Exception {
        return readMenus(USER_MENU_DIR, USER_MENU);
    }

    public MenuItem readMenus(String userMenuDir, String userMenu){
        MenuItem root = null;
        try {
            opendb();
            try {
                String xml = com.dynamide.util.FileTools.readFile(userMenuDir, userMenu);
                root = Space.menuBundle2MenuItem(xml);

                ObjectSet resultA = db.queryByExample(Anarchia.class);
                if ( resultA.hasNext() ){
                    Anarchia a = (Anarchia)resultA.next();
                    a.setMenu(root);
                    db.store(a);
                    db.commit();
                    System.out.println("anarchia menus set. -->\r\n\t"+root);
                }
            } finally {
                closedb();
            }
        }catch (Throwable t){
            System.out.println("error in readMenus(): "+t);
        }
        return root;
    }

    public Object testMenu(String key) throws Exception {
        Object bigResult = null;
        opendb();
        try {
            ObjectSet result=db.queryByExample(Anarchia.class);
            while ( result.hasNext() ){
                Anarchia anarchia = (Anarchia)(result.next());
                if (anarchia!=null){
                    MenuItem menuitem = anarchia.getMenu();
                    System.out.println("MenuManager from DB: "+menuitem);
                    MenuManager menu = new MenuManager(menuitem);

                    Sandbox sb = getSandbox();
                    Map<String, Object> properties = new HashMap<String, Object>();
                    properties.put("anarchia", anarchia);
                    sb.setProperties(properties);

                    InputObject inputObject = new InputObject();

                    menu.setSandbox(sb);
                    Object menuResult = menu.fireMenuKey(key, inputObject); //key = "NEW", ...
                    System.out.println("MenuManager result: "+menuResult);
                    bigResult = menuResult;
                }

            }

        } finally {
            closedb();
        }
        return bigResult;
    }

    public void initdb()throws Exception {
        opendb();
        try {
            ObjectSet result = db.queryByExample(Author.class);
            if ( !result.hasNext() ){
                addTwoAuthors(db);
                System.out.println("initdb added two authors.");
            }
		}
		finally {
		    closedb();
            System.out.println("initdb DONE.");
        }
    }

    public void testdb() throws Exception {
        opendb();
		try {
			ObjectSet result=db.queryByExample(Anarchia.class);
			listResult(result, db, "QBE...");


			result=db.queryByExample(Author.class);
			listResult(result, db, "QBE Inner search on Author...");

			System.out.println("====== storeAnarchia ...");
			storeAnarchia(db);
			db.commit();

			result=db.queryByExample(Anarchia.class);
			listResult(result, db, "listing results after store ...");
		}
		finally {
			db.close();
		}
    }

    public Sandbox getSandbox(){
        //TODO: validate for user.
        return new Sandbox();
    }

    public static void listResult(ObjectSet result, ObjectContainer db, String logline) {
        System.out.println("======================== "+logline);
        System.out.println("result.size: "+result.size());
        while(result.hasNext()) {
            Object r = result.next();
            db.activate(r, 2);
            System.out.println(r);
        }
    }

	public static void main(String[] args) throws Exception {
        com.dynamide.util.Opts opts = new com.dynamide.util.Opts(args);
        if (opts.getOptionBool("-clean")){
            //TO delete db and start new:
            new File(DB4OFILENAME).delete();
        }
        AnarchiaDBMain main = new AnarchiaDBMain();
        if (opts.getOptionBool("-initdb")) {
            main.initdb();
        }
        if (opts.getOptionBool("-testdb")) {
            main.testdb();
        }
        if (opts.getOptionBool("-storeAnarchia")) {
            main.storeAnarchia();
        }
        if (opts.getOptionBool("-readMenus")) {
            main.readMenus();
        }
        String key = opts.getOption("-testmenu", "");
        if (key.length()>0) {
            main.testMenu(key);
        }

    }
	


	

}
