package com.ccc.cms.controller;

import com.ccc.cms.model.Contact;
import com.ccc.cms.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping("/getContact/{id}")
    public ResponseEntity<Contact> getContact(@PathVariable Long id) {
        Optional<Contact> contact = contactService.getContactById(id);
        return contact.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.ofNullable(null));
    }

    @GetMapping("/getAllContact")
    public ResponseEntity<List<Contact>> getAllContact() {
        List<Contact> contacts = contactService.getAllContact();
        return ResponseEntity.ok(contacts);
    }

    @PostMapping("/createContact")
    public Contact createContact(@RequestBody Contact contact) {
        return contactService.saveContact(contact);
    }

    @DeleteMapping("/deleteContact/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return ResponseEntity.ok().build();
    }
}
