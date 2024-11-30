package org.globant.testing.requests;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.globant.testing.models.Client;
import org.globant.testing.models.Resource;
import org.globant.testing.utils.Constants;
import org.globant.testing.utils.JsonFileReader;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ResourceRequest extends BaseRequest{
    private String endpoint;
    /**
     Get active resources

     @return rest-assured response
     */
    public Response getActiveResources() {
        endpoint = String.format(Constants.URL_WITH_OTHER_PARAM, Constants.RESOURCES_PATH, "active", true);
        return requestGet(endpoint, createBaseHeaders());
    }

    public Response updateResources(Resource resource){
        endpoint = String.format(Constants.URL_WITH_PARAM, Constants.RESOURCES_PATH, resource.getId());
        return requestPut(endpoint, createBaseHeaders(), resource);
    }


    public List<Response> createDefaultResources() {
        JsonFileReader jsonFile = new JsonFileReader();

        List<Resource> resources = jsonFile.getResourcesByJson(Constants.DEFAULT_RESOURCE_FILE_PATH);
        List<Response> responses = new ArrayList<>();

        for (Resource resource : resources) {
            Response response = this.createResource(resource);
            responses.add(response);
        }

        return responses;
    }

    /**
     Create client

     @param resource model
     @return rest-assured response
     */
    public Response createResource(Resource resource) {
        endpoint = String.format(Constants.URL, Constants.RESOURCES_PATH);
        return requestPost(endpoint, createBaseHeaders(), resource);
    }

    public Resource getResourceEntity(@NotNull Response response) {
        return response.as(Resource.class);
    }

    public List<Resource> getResourcesEntity(@NotNull Response response) {
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getList("", Resource.class);
    }
}
