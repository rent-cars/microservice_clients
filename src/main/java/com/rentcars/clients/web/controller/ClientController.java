package com.rentcars.clients.web.controller;

import com.rentcars.clients.model.Client;
import com.rentcars.clients.web.dao.ClientDao;
import com.rentcars.clients.web.exceptions.DrivingLicenseDoesntExistException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@Api("API pour opérations CRUD sur les clients.")
@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private final ClientDao clientDao;
    private RestOperations restTemplate = new RestTemplate();
    public ClientController(ClientDao clientDao) {this.clientDao = clientDao;}

    @RequestMapping(method = RequestMethod.GET)
    public List<Client> clientList(){
        List<Client> clients = clientDao.findAll();
        return clients;
    }

    @ApiOperation(value = "Récupère un client grâce à son ID à condition que celui-ci soit existant!")
    @GetMapping(value = "/{id}")
    public Client showClient(@PathVariable int id) { return clientDao.findById(id); }

    @ApiOperation(value = "Ajoute un client à la liste")
    @PostMapping
    public Client addClient (@RequestBody Client client) {
        textException(client);
        return clientDao.save(client);
    }

    @ApiOperation(value = "Modifie les données d'un client existant")
    @PutMapping
    public void modifyClient (@RequestBody Client client){
        textException(client);
        clientDao.save(client);
    }
    @ApiOperation(value = "Supprime un client existant")
    @DeleteMapping("/{id}")
    public void deleteClient (@PathVariable int id) {
        clientDao.deleteById(id);
    }

    public void textException(Client client){
        String drivingLicenseNumb = client.getLicenseNumber();
        if (!isValidDrivingLicense(drivingLicenseNumb)){
            throw new DrivingLicenseDoesntExistException("Le numéro de permis " + drivingLicenseNumb  +" enregistré est un permis de conduire invalide. Veuillez ré-enregistrer le numéro de permis.");
        }
    }

    public Boolean isValidDrivingLicense(String licenceNumb){
        return restTemplate.getForObject("http://localhost:8081/licenses/" + licenceNumb, Boolean.class);
    }

}
