package cue.edu.co.api.space.constants;

public class SpaceEndpoint {
    private SpaceEndpoint() {
    }

    public static final String SPACE_BASE = "/api/spaces";
    public static final String SPACE_CREATE_ENDPOINT = SPACE_BASE + "/create";
    public static final String SPACE_UPDATE_ENDPOINT = SPACE_BASE + "/{id}/update";
    public static final String SPACE_DELETE_ENDPOINT = SPACE_BASE + "/{id}/delete";
    public static final String SPACE_BY_ID = SPACE_BASE + "/{id}";
    public static final String SPACE_GET_ALL = SPACE_BASE + "/all";
    public static final String SPACE_AVAILABILITY =  SPACE_BASE + "/{id}/availability";
    public static final String SPACE_RESERVE = SPACE_BASE + "/{id}/reserve";
    public static final String SPACE_AVAILABLE = SPACE_BASE + "/available";

}
