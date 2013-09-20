package us.anarchia.gwt.anarchiaAuthorApp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import us.anarchia.obj.Author;

public interface AnarchiaAuthorAppServiceAsync {
    void getMessage(String msg, AsyncCallback<String> async);
    void storeAuthor(Author author, AsyncCallback<Author> callback);
}
