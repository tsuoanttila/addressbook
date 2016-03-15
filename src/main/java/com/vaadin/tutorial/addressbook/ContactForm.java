package com.vaadin.tutorial.addressbook;

import com.vaadin.server.communication.data.typed.DataSource;
import com.vaadin.tutorial.addressbook.backend.Contact;
import com.vaadin.ui.proto.TypedForm;

/* Create custom UI Components.
 *
 * Create your own Vaadin components by inheritance and composition.
 * This is a form component inherited from VerticalLayout. Use
 * Use BeanFieldGroup to bind data fields from DTO to UI fields.
 * Similarly named field by naming convention or customized
 * with @PropertyId annotation.
 */
public class ContactForm extends TypedForm<Contact> {

	public ContactForm(DataSource<Contact> dataSource) {
		super(Contact.class, dataSource);
		addComponentAsFirst(getButtonLayout());
		setSizeUndefined();
		setSpacing(true);
		setMargin(true);

		// Initially hidden
		setVisible(false);
	}

	@Override
	protected void save() {
		super.save();

		clear();
	}

	@Override
	public void edit(Contact data) {
		super.edit(data);

		setVisible(data != null);
	}
}
