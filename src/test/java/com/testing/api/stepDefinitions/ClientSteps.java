package com.testing.api.stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.globant.testing.models.Client;
import org.globant.testing.models.Resource;
import org.globant.testing.requests.ClientRequest;
import org.globant.testing.requests.ResourceRequest;
import org.junit.Assert;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class ClientSteps {
    private static final Logger logger = LogManager.getLogger(ClientSteps.class);
    private final ClientRequest clientRequest = new ClientRequest();
    private final ResourceRequest resourceRequest = new ResourceRequest();
    private Response response;
    private Resource resource;
    private Client client;
    private List<Resource> resourceList;
    private List<Client> clientList;
    private String phoneNumber;

    @Given("there are at least 10 registered clients")
    public void thereAreAtLeast10RegisteredClients() {
        response = clientRequest.getClients();
        clientList = clientRequest.getClientsEntity(response);
        assertEquals(200, response.statusCode());

        if (clientList.size() < 10) {
            List<Response> responses = clientRequest.createDefaultClients();
            for (Response r : responses) {
                assertEquals(201, r.statusCode());
            }
            clientList = clientRequest.getClientsEntity(response);
            //Assert.assertTrue(clientList.size() >= 10);
        }
    }

    @Given("there are at least 5 active resources")
    public void thereAreAtLeast5ActiveResources() {
        response = resourceRequest.getActiveResources();
        resourceList = resourceRequest.getResourcesEntity(response);
        assertEquals(200, response.statusCode());
        Assert.assertTrue(resourceList.size() >= 5);
        while (resourceList.size() < 15){
            response = resourceRequest.createNewResource();
            response = resourceRequest.getActiveResources();
            resourceList = resourceRequest.getResourcesEntity(response);
            assertEquals(200, response.statusCode());
        }
    }

    @Given("there are at least 15 resources")
    public void hereAreAtLeast15Resources(){
        response = resourceRequest.getResources();
        resourceList = resourceRequest.getResourcesEntity(response);
        assertEquals(200, response.statusCode());
        while (resourceList.size() < 15){
            response = resourceRequest.createNewResource();
            response = resourceRequest.getResources();
            resourceList = resourceRequest.getResourcesEntity(response);
            assertEquals(200, response.statusCode());
        }
    }


    @When("I find all active resources")
    public void findAllActiveResources(){
        response = resourceRequest.getActiveResources();
        resourceList = resourceRequest.getResourcesEntity(response);
    }

    @When("I find the first client named {string}")
    public void findClient(String clientName) {
        response = clientRequest.getClients();
        clientList = clientRequest.getClientsEntity(response);
        assertEquals(200, response.statusCode());
        boolean clientFound = false;
        for (Client client : clientList) {
            if (client.getName().equals(clientName)) {
                clientFound = true;
                logger.info("Found client: " + client.getName());
                logger.info("Client id: " + client.getId());
                this.client=client;
                break;
            }
        }
        Assert.assertTrue("Client with name " + clientName + "found", clientFound);
    }

    @When("I save her current phone number")
    public void saveClientNumber() {
        phoneNumber=client.getPhone();
        response = clientRequest.getClient(client.getId());
        Client client = clientRequest.getClientEntity(response);

        assertEquals(phoneNumber, client.getPhone());

        Assert.assertNotNull("El número de teléfono no puede ser nulo", phoneNumber);
        Assert.assertFalse("El número de teléfono no puede estar vacío", phoneNumber.isEmpty());
    }

    @When("I update her phone number")
    public void updateClientNumber() {
        Random random = new Random();
        int rand = 100000000 + random.nextInt(900000000);
        String newPhone = String.valueOf(rand);
        client.setPhone(newPhone);
        response = clientRequest.updateClient(client, client.getId());
    }

    @When("I create a new client")
    public void createNewClient(){
        response = clientRequest.createClient();
        assertEquals(201, response.statusCode());
    }

    @When("I find the latest resource")
    public void findLastResource(){
        resource = resourceList.get(resourceList.size()-1);
        logger.info("Current last resource: " + resource);
    }

    @When("I update all the parameters of this resource")
    public void updateAllOfLastResource(){
        resource.setName("LaptopGB");
        resource.setTrademark("Globant");
        resource.setStock("10");
        resource.setPrice("300.00");
        resource.setDescription("Laptop proporcionada por Globant");
        resource.setTags("Tecnologia");
        if (resource.isActive()){
            resource.setActive(false);
        }
        logger.info("Updated resource: " + resource);
        response = resourceRequest.updateResources(resource);
        Assert.assertEquals(200, response.statusCode());
    }

    @Then("her new phone number should be different")
    public void validatePhoneNumber() {
        response = clientRequest.getClient(client.getId());
        Client client = clientRequest.getClientEntity(response);
        String newNumber = client.getPhone();
        logger.info("Old number: " + phoneNumber + " - New number: " + newNumber);
        Assert.assertNotEquals(newNumber, phoneNumber);
    }

    @Then("I delete all the registered clients")
    public void DeleteAll() {
        for(Client cliente: clientList){
            response = clientRequest.deleteClient(cliente.getId());
        }
        assertEquals(200, response.getStatusCode());
        logger.info("clientes eliminados");
    }

    @Then("I update them as inactive")
    public void updateAllActive(){
        for (Resource resource:resourceList){
            resource.setActive(false);
            logger.info(resource);
            response = resourceRequest.updateResources(resource);
            Assert.assertEquals(200, response.statusCode());
        }
    }

    @Then("I should find the new client")
    public void findLastClient(){
        response = clientRequest.getClients();
        clientList = clientRequest.getClientsEntity(response);
        assertEquals(200, response.statusCode());
        client = clientList.get(clientList.size() - 1);
        logger.info("Current last client: " + client);
    }

    @Then("I update any parameter of the new client")
    public void updateLast(){
        updateClientNumber();
        logger.info("Actualizacion del número a: " + client.getPhone());
        assertEquals(200, response.statusCode());
    }

    @Then("I delete the new client")
    public void deleteLastClient(){
        response = clientRequest.deleteClient(client.getId());
        logger.info("Last client has been deleted");
        findLastClient();
        assertEquals(200, response.statusCode());
    }
}