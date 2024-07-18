package com.example.Services;

import com.example.Repositories.PersonRepository;
import com.example.Suppliers.Person;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public PersonService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Person> getUsers(String role) {
        return personRepository.findByRole(role);
    }

    public Optional<Person> getPerson(String username) {
        return personRepository.findByUsername(username);
    }
    @Transactional
    public void save(Person person){
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        personRepository.save(person);
    }

    public void savePerson(Person person) {
        personRepository.save(person);
    }

}