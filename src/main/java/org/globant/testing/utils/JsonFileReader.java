package org.globant.testing.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.globant.testing.models.Client;
import org.globant.testing.models.Resource;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

public class JsonFileReader {
    /**
     * This method read a JSON file and deserialize the body into a Client object
     *
     * @param jsonFileName json file location path
     *
     * @return Client : client
     */
    public Client getClientByJson(String jsonFileName) {
        Client client = new Client();
        try (Reader reader = new FileReader(jsonFileName)) {
            Gson gson = new Gson();
            client = gson.fromJson(reader, Client.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return client;
    }

    public List<Client> getClientsByJson(String jsonFileName) {
        List<Client> clients = null;
        try (Reader reader = new FileReader(jsonFileName)) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Client>>() {}.getType();
            clients = gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clients;
    }

    public Resource getResourceByJson(String jsonFileName) {
        Resource resource = new Resource();
        try (Reader reader = new FileReader(jsonFileName)) {
            Gson gson = new Gson();
            resource = gson.fromJson(reader, Resource.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resource;
    }

    public List<Resource> getResourcesByJson(String jsonFileName) {
        List<Resource> resources = null;
        try (Reader reader = new FileReader(jsonFileName)) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Resource>>() {}.getType();
            resources = gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resources;
    }
}
