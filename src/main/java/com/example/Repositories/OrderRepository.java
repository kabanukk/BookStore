package com.example.Repositories;

import com.example.Suppliers.Order;
import com.example.Suppliers.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByPersonOrderByOrderDateDesc(Person person);
    List<Order> findAllByOrderByOrderDateDesc();
}