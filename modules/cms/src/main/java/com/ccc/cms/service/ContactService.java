package com.ccc.cms.service;

import com.ccc.cms.model.Contact;
import com.ccc.cms.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    // Cache the contact by id
    @Cacheable(value = "contacts", key = "#id")
    public Optional<Contact> getContactById(Long id) {
        return contactRepository.findById(id);
    }

    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }

    @CacheEvict(value = "contacts", key = "#id")
    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }

    public List<Contact> getAllContact() {
        return contactRepository.findAll();
    }
}
