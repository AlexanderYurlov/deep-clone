package com.ecwid.utils.copy.demo;


import com.ecwid.utility.copy.CopyUtils;

import java.util.ArrayList;

public class DeepCopyDemo {

    public static void main(String[] args) {

        var list = new ArrayList<String>();
        list.add("Book 1");
        list.add("Book 2");
        Man original = new Man("John", 30, list);

        Man copy = (Man) CopyUtils.copy(original);

        System.out.println("Is this the same object? " + (original == copy));

    }

}
