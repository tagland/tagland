package us.anarchia.gwt.anarchiaAuthorApp.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import us.anarchia.obj.Author;

@RemoteServiceRelativePath("AnarchiaAuthorAppService")
public interface AnarchiaAuthorAppService extends RemoteService {
    // Sample interface method of remote interface
    String getMessage(String msg);

    Author storeAuthor(Author author) throws IllegalArgumentException;

    /**
     * Utility/Convenience class.
     * Use AnarchiaAuthorAppService.App.getInstance() to access static instance of AnarchiaAuthorAppServiceAsync
     */
    public static class App {
        private static AnarchiaAuthorAppServiceAsync ourInstance = GWT.create(AnarchiaAuthorAppService.class);

        public static synchronized AnarchiaAuthorAppServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
