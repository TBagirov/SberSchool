package org.bagirov.model;

import java.util.*;

public class PhoneDirectory {
    private Map<String, List<String>> person;

    public PhoneDirectory() {
        person = new HashMap<>();
    }

    public void add(String surname, String phoneNumber){
        if(!person.containsKey(surname)){
            person.put(surname, new ArrayList<>());
        }
        person.get(surname).add(phoneNumber);
    }

    public List<String> get(String surname) {
        if(person.get(surname) == null){
            return new ArrayList<>();
        }
        return person.get(surname);
    }
}

