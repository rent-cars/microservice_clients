package com.rentcars.clients.web.dao;

import com.rentcars.clients.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientDao extends JpaRepository<Client, Integer> {
    List<Client> findAll();
    Client findById(int id);
    Client save(Client client);
    void deleteById(Integer id);
}
