package us.anarchia.gwt.anarchiaAuthorApp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import us.anarchia.obj.Author;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class AnarchiaAuthorApp implements EntryPoint {

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        final Button button = new Button("Click me");
        final Label label = new Label();

        button.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                if (label.getText().equals("")) {
                    //AnarchiaAuthorAppService.App.getInstance().getMessage("Hello, World!", new MyAsyncCallback(label));

                    Author author = new Author();
                    author.setFamiliarName("fromclient");
                   AnarchiaAuthorAppService.App.getInstance().storeAuthor(author, new AuthorAsyncCallback(author, label));

                } else {
                    label.setText("");
                }
            }
        });

        // Assume that the host HTML has elements defined whose
        // IDs are "slot1", "slot2".  In a real app, you probably would not want
        // to hard-code IDs.  Instead, you could, for example, search for all
        // elements with a particular CSS class and replace them with widgets.
        //
        RootPanel.get("slot1").add(button);
        RootPanel.get("slot2").add(label);
    }

    private static class MyAsyncCallback implements AsyncCallback<String> {
        private Label label;

        public MyAsyncCallback(Label label) {
            this.label = label;
        }

        public void onSuccess(String result) {
            label.getElement().setInnerHTML(result);
        }

        public void onFailure(Throwable throwable) {
            label.setText("Failed to receive answer from server!");
        }
    }

    //=========== The AsyncCallback for Author ==============
     private static class AuthorAsyncCallback implements AsyncCallback<Author> {
        private Author authorCon;
        private Label label;
        public AuthorAsyncCallback(Author author,Label label) {
            this.authorCon = author;
            this.label = label ;
        }

        public void onSuccess(Author result) {
            label.getElement().setInnerHTML(result.toString());
        }

        public void onFailure(Throwable throwable) {
            label.setText("Failed to receive answer from server!");
        }
    }
}
