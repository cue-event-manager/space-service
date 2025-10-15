package cue.edu.co.api.campus.constants;

public class CampusEndpoint {
    private CampusEndpoint() {
    }

    public static final String CAMPUS_BASE = "/api/campuses";
    public static final String CAMPUS_GET_ALL_ENDPOINT = CAMPUS_BASE + "/all";
    public static final String CAMPUS_CREATE_ENDPOINT = CAMPUS_BASE + "/create";
    public static final String CAMPUS_BY_ID = CAMPUS_BASE + "/{id}";
    public static final String CAMPUS_UPDATE_ENDPOINT = CAMPUS_BASE + "/{id}/update";
    public static final String CAMPUS_DELETE_ENDPOINT = CAMPUS_BASE + "/{id}/delete";
}
