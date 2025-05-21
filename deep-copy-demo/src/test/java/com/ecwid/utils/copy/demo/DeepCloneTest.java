package com.ecwid.utils.copy.demo;


import com.ecwid.utility.copy.CopyUtils;
import com.ecwid.utils.copy.demo.obj.Contact;
import com.ecwid.utils.copy.demo.obj.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class DeepCloneTest {

    @Test
    void testCopyObj() {
        var list = new ArrayList<String>();
        list.add("Book 1");
        list.add("Book 2");
        Man original = new Man("John", 30, list);

        Man copy = CopyUtils.copy(original);

        Assertions.assertNotSame(original, copy);
        Assertions.assertEquals(copy.getAge(), original.getAge());
        Assertions.assertSame(copy.getName(), original.getName());
        Assertions.assertNotSame(copy.getFavoriteBooks(), original.getFavoriteBooks());
        Assertions.assertSame(copy.getFavoriteBooks().getFirst(), original.getFavoriteBooks().getFirst());
    }

    @Test
    void testCopyRec() {
        Person original = new Person("Alex", "NYC");
        Assertions.assertDoesNotThrow(() -> CopyUtils.copy(original));
    }

    @Test
    void testCopyReferenceSameObj() {
        var original = new Contact("12345");
        var list = new ArrayList<Contact>();
        list.add(original);
        original.setContactList(list);
        var copy = CopyUtils.copy(original);

        Assertions.assertNotSame(original, copy);
        Assertions.assertNotSame(original.getContactList(), copy.getContactList());
        Assertions.assertSame(copy.getContactList().getFirst(), copy);
    }

}
