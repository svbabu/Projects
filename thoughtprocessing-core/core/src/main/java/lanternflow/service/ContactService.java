package lanternflow.service;

import jakarta.validation.Valid;
import lanternflow.model.Contact;
import lanternflow.model.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lanternflow.repository.ContactRepository;

@Service
public class ContactService {

    @Autowired
    private final ContactRepository contactRepository;



    @Autowired
    public ContactService(ContactRepository contactRepository)
    {
        this.contactRepository = contactRepository;

    }
    public Contact save(Contact contact) {

        return (Contact) contactRepository.save(contact);
    }
    public void saveMessage(@Valid MessageDto message) {
        Contact contact = new Contact();
        /*contact.setName(message.getId());*/
        contact.setName(message.getName());
        contact.setEmail(message.getEmail());
        contact.setMobile(message.getMobile());
        contact.setSubject(message.getSubject());
        contact.setMessage(message.getMessage());

        contactRepository.save(contact);
    }


    /*public Optional<Contact> findById(long id) {
        return contactRepository.findById(id);
    }
    public List<Contact> findAll() {
        return contactRepository.findAll();
    }
    public Contact update(Contact contact)
    {
    return null;  // Optional<Contact> contactUpdate=contactRepository.findById(contact.getId());

    }*/
}
