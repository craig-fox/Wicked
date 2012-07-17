package demonic.client;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.ListDataProvider;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Wicked implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	
	 private static class Contact {
		    private final String address;
		    private final String name;

		    public Contact(String name, String address) {
		      this.name = name;
		      this.address = address;
		    }
		  }
	 
	 // The list of data to display.
	  private static List<Contact> CONTACTS = Arrays.asList(new Contact("John",
	      "123 Fourth Road"), new Contact("Mary", "222 Lancer Lane"), new Contact(
	      "Zander", "94 Road Street"));

	  
	  
	  
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final Button sendButton = new Button("Send");
		final TextBox nameField = new TextBox();
		nameField.setText("GWT User");
		final Label errorLabel = new Label();

		// We can add style names to widgets
		sendButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);

		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();
		
		CellTable<Contact> rockTable = new CellTable<Contact>();
		TextColumn<Contact> name = new TextColumn<Contact>(){

			@Override
			public String getValue(Contact object) {
				// TODO Auto-generated method stub
				return object.name;
			}
			
		};
        rockTable.addColumn(name, "Big Fat Name");
		
		Column<Contact, SafeHtml> stoneColumn = new Column<Contact, SafeHtml>(
		        new ClickableSafeHtmlCell()) {
		    @Override
		    public SafeHtml getValue(Contact object) {
		        SafeHtmlBuilder sb = new SafeHtmlBuilder();
		        sb.appendHtmlConstant("<a>");
		        sb.appendEscaped(object.name);
		        sb.appendHtmlConstant("</a>");
		        return sb.toSafeHtml();
		    }
		};

		stoneColumn.setFieldUpdater(new FieldUpdater<Contact, SafeHtml>() {
		        @Override
		        public void update(int index, Contact object, SafeHtml value) {
		             Window.alert("You have clicked: " + object.name);

		        }
		    });

		SafeHtmlBuilder sbStone = new SafeHtmlBuilder();
		sbStone.appendHtmlConstant("<a href=\"http://www.cricinfo.com\">");
		sbStone.appendHtmlConstant("<img src=\"facebook.png\"></img>");
	        sbStone.appendHtmlConstant("</a>");
	        
	        Header<SafeHtml> header = new Header<SafeHtml>(new ClickableSafeHtmlCell()) {
	           
	            @Override
			    public SafeHtml getValue() {
	            	SafeHtmlBuilder sbStone = new SafeHtmlBuilder();
	        		sbStone.appendHtmlConstant("<a>");
	        		sbStone.appendHtmlConstant("<img src=\"facebook.png\"></img>");
	        	        sbStone.appendHtmlConstant("</a>");
	        	        return sbStone.toSafeHtml();
	            }

	            @Override
	            public void onBrowserEvent(Context context, Element elem, NativeEvent event) {
	                  Window.alert("Free Beer!"); // NOT CALLED
	            }
	      };    
		rockTable.addColumn(stoneColumn, header);
		
		
		// Create a data provider.
	    ListDataProvider<Contact> dataProvider = new ListDataProvider<Contact>();

	    // Connect the table to the data provider.
	    dataProvider.addDataDisplay(rockTable);

	    // Add the data to the data provider, which automatically pushes it to the
	    // widget.
	    List<Contact> list = dataProvider.getList();
	    for (Contact contact : CONTACTS) {
	      list.add(contact);
	    }
		
		RootPanel.get().add(rockTable);
	}
}
