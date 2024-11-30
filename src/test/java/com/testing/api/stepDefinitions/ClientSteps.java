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
        }

        Assert.assertTrue(clientList.size() >= 10);
    }

    @Given("there are at least 5 active resources")
    public void thereAreAtLeast5ActiveResources() {
        response = resourceRequest.getActiveResources();
        resourceList = resourceRequest.getResourcesEntity(response);
        assertEquals(200, response.statusCode());
        Assert.assertTrue(resourceList.size() >= 5);
        /*if (resourceList.size() < 5) {
            List<Response> responses = clientRequest.createDefaultClients();
            for (Response r : responses) {
                assertEquals(201, r.statusCode());
            }
            clientList = clientRequest.getClientsEntity(response);
        }*/
    }

    @When("I find all active resources")
    public void findAllActiveResources(){
        response = resourceRequest.getActiveResources();
        resourceList = resourceRequest.getResourcesEntity(response);
    }

    @When("I find the first client named {string}")
    public void findClient(String clientName) {
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

    }


    @Then("her new phone number should be different")
    public void validatePhoneNumber() {
        response = clientRequest.getClient(client.getId());
        Client client = clientRequest.getClientEntity(response);
        String newNumber = client.getPhone();
        logger.info("Old number: " + phoneNumber + " - New number: " + newNumber);
        Assert.assertNotEquals(newNumber, phoneNumber);
    }

    @Then("Delete all the registered clients")
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
}