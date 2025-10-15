package cue.edu.co.api.spacestatus.constants;

public class SpaceStatusEndpoint {
    private SpaceStatusEndpoint() {}

    public static final String SPACE_STATUS_BASE = "/api/space-statuses";
    public static final String SPACE_STATUS_GET_ALL_ENDPOINT = SPACE_STATUS_BASE + "/all";
    public static final String SPACE_STATUS_CREATE_ENDPOINT = SPACE_STATUS_BASE + "/create";
    public static final String SPACE_STATUS_BY_ID = SPACE_STATUS_BASE + "/{id}";
    public static final String SPACE_STATUS_UPDATE_ENDPOINT = SPACE_STATUS_BASE + "/{id}/update";
    public static final String SPACE_STATUS_DELETE_ENDPOINT = SPACE_STATUS_BASE + "/{id}/delete";
}
