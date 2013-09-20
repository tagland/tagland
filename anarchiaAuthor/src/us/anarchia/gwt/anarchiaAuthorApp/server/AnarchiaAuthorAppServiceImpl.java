package us.anarchia.gwt.anarchiaAuthorApp.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import us.anarchia.db.AnarchiaDB;
import us.anarchia.gwt.anarchiaAuthorApp.client.AnarchiaAuthorAppService;
import us.anarchia.obj.Author;

public class AnarchiaAuthorAppServiceImpl extends RemoteServiceServlet implements AnarchiaAuthorAppService {

    private AnarchiaDB anarchiaDB = new AnarchiaDB();

    // Implementation of sample interface method
    public String getMessage(String msg) {
        return "Client said: \"" + msg + "\"<br>Anarchia Server answered: \"Hi!\"";
    }

    @Override
	public Author storeAuthor(Author author) throws IllegalArgumentException {
		String id = anarchiaDB.storeAuthor(author);
		Author result = anarchiaDB.getAuthor(id);
        result.setFamiliarName(result.getFamiliarName()+"_onServer:"+id);
        return result;
    }
}