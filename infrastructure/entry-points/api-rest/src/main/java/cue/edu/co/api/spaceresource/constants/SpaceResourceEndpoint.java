package cue.edu.co.api.spaceresource.constants;

public class SpaceResourceEndpoint {
    private SpaceResourceEndpoint(){}

    public static final String SPACE_RESOURCE_BASE = "/api/space-resources";
    public static final String SPACE_RESOURCE_CREATE_ENDPOINT = SPACE_RESOURCE_BASE + "/create";
    public static final String SPACE_RESOURCE_UPDATE_ENDPOINT = SPACE_RESOURCE_BASE + "/{id}/update";
    public static final String SPACE_RESOURCE_DELETE_ENDPOINT = SPACE_RESOURCE_BASE + "/{id}/delete";
    public static final String SPACE_RESOURCE_BY_ID = SPACE_RESOURCE_BASE + "/{id}";
}
