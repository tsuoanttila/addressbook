package com.vaadin.tutorial.addressbook.backend;

import java.util.Iterator;

import com.vaadin.server.communication.data.typed.AbstractDataSource;

public class ContactDataSource extends AbstractDataSource<Contact> {

	ContactService service = ContactService.createDemoService();
	String filter;

	@Override
	public Iterator<Contact> iterator() {
		return service.findAll(filter).iterator();
	}

	@Override
	public void save(Contact data) {
		service.save(data);
		fireDataChange();
	}

	@Override
	public void remove(Contact data) {
		service.delete(data);
		fireDataRemove(data);
	}

	public void setFilter(String filter) {
		this.filter = filter;
		fireDataChange();
	}
}
