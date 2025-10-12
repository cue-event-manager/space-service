package cue.edu.co.api.spacestatus.constants;

public class SpaceStatusEndpoint {
    private SpaceStatusEndpoint() {}

    public static final String SPACE_STATUS_BASE = "/api/space-statuses";
    public static final String SPACE_STATUS_CREATE_ENDPOINT = SPACE_STATUS_BASE;
    public static final String SPACE_STATUS_BY_ID = SPACE_STATUS_BASE + "/{id}";
    public static final String SPACE_STATUS_UPDATE_ENDPOINT = SPACE_STATUS_BASE + "/{id}";
    public static final String SPACE_STATUS_DELETE_ENDPOINT = SPACE_STATUS_BASE + "/{id}";
}
