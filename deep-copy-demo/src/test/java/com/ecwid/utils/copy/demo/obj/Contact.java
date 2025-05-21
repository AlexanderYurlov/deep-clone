package com.ecwid.utils.copy.demo.obj;

import java.util.List;

public class Contact {

    private String number;
    private List<Contact> contactList;

    public Contact(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }
}
