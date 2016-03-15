package com.vaadin.tutorial.addressbook;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.tutorial.addressbook.backend.Contact;
import com.vaadin.tutorial.addressbook.backend.ContactDataSource;
import com.vaadin.tutorial.addressbook.backend.ContactService;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.proto.TypedGrid;

/* User Interface written in Java.
 *
 * Define the user interface shown on the Vaadin generated web page by extending the UI class.
 * By default, a new UI instance is automatically created when the page is loaded. To reuse
 * the same instance, add @PreserveOnRefresh.
 */
@Title("Addressbook")
@Theme("valo")
public class AddressbookUI extends UI {

	/*
	 * Hundreds of widgets. Vaadin's user interface components are just Java
	 * objects that encapsulate and handle cross-browser support and
	 * client-server communication. The default Vaadin components are in the
	 * com.vaadin.ui package and there are over 500 more in
	 * vaadin.com/directory.
	 */
	TextField filter = new TextField();
	TypedGrid<Contact> contactList;
	Button newContact = new Button("New contact");

	// ContactForm is an example of a custom component class
	ContactForm contactForm;

	// ContactService is a in-memory mock DAO that mimics
	// a real-world datasource. Typically implemented for
	// example as EJB or Spring Data based service.
	ContactService service = ContactService.createDemoService();

	/*
	 * The "Main method".
	 *
	 * This is the entry point method executed to initialize and configure the
	 * visible user interface. Executed on every browser reload because a new
	 * instance is created for each web page loaded.
	 */
	@Override
	protected void init(VaadinRequest request) {
		configureComponents();
		buildLayout();
	}

	private void configureComponents() {
		ContactDataSource dataSource = new ContactDataSource();
		/*
		 * Synchronous event handling.
		 *
		 * Receive user interaction events on the server-side. This allows you
		 * to synchronously handle those events. Vaadin automatically sends only
		 * the needed changes to the web page without loading a new page.
		 */
		contactForm = new ContactForm(dataSource);
		contactList = new TypedGrid<>(Contact.class, dataSource);

		newContact.addClickListener(e -> contactForm.edit(new Contact()));

		filter.setInputPrompt("Filter contacts...");
		filter.addTextChangeListener(e -> dataSource.setFilter(e.getText()));

		contactList.setColumnOrder("firstName", "lastName", "email");
		contactList.removeColumn("id");
		contactList.removeColumn("birthDate");
		contactList.removeColumn("phone");
		contactList.addSelectionListener(e -> contactForm.edit(e.getSelected()));
	}

	/*
	 * Robust layouts.
	 *
	 * Layouts are components that contain other components. HorizontalLayout
	 * contains TextField and Button. It is wrapped with a Grid into
	 * VerticalLayout for the left side of the screen. Allow user to resize the
	 * components with a SplitPanel.
	 *
	 * In addition to programmatically building layout in Java, you may also
	 * choose to setup layout declaratively with Vaadin Designer, CSS and HTML.
	 */
	private void buildLayout() {
		HorizontalLayout actions = new HorizontalLayout(filter, newContact);
		actions.setWidth("100%");
		filter.setWidth("100%");
		actions.setExpandRatio(filter, 1);

		VerticalLayout left = new VerticalLayout(actions, contactList);
		left.setSizeFull();
		contactList.setSizeFull();
		left.setExpandRatio(contactList, 1);

		HorizontalLayout mainLayout = new HorizontalLayout(left, contactForm);
		mainLayout.setSizeFull();
		mainLayout.setExpandRatio(left, 1);

		// Split and allow resizing
		setContent(mainLayout);
	}

	/*
	 * Deployed as a Servlet or Portlet.
	 *
	 * You can specify additional servlet parameters like the URI and UI class
	 * name and turn on production mode when you have finished developing the
	 * application.
	 */
	@WebServlet(urlPatterns = "/*")
	@VaadinServletConfiguration(ui = AddressbookUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}

}
