package cue.edu.co.usecase.space;

import cue.edu.co.model.space.Space;
import cue.edu.co.model.space.constants.SpaceExceptionCode;
import cue.edu.co.model.space.exceptions.InvalidGetAvailableSpacesParametersException;
import cue.edu.co.model.space.gateways.SpaceRepository;
import cue.edu.co.model.space.queries.GetAvailableSpacesQuery;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
public class GetAvailableSpacesUseCase {

    private final SpaceRepository spaceRepository;

    public List<Space> execute(GetAvailableSpacesQuery query) {
        validateQuery(query);
        return spaceRepository.findAvailableSpaces(query);
    }

    private void validateQuery(GetAvailableSpacesQuery query) {
        validateDateParameters(query);
        validateRecurrenceParameters(query);
        validateTimeParameters(query);
    }

    private void validateDateParameters(GetAvailableSpacesQuery q) {
        boolean hasSingleDate = q.date().isPresent();
        boolean hasDateRange = q.startDate().isPresent() || q.endDate().isPresent();

        if (!hasSingleDate && !hasDateRange) {
            throw new InvalidGetAvailableSpacesParametersException(SpaceExceptionCode.MISSING_DATE_OR_RANGE);
        }

        if (hasSingleDate && hasDateRange) {
            throw new InvalidGetAvailableSpacesParametersException(SpaceExceptionCode.CONFLICTING_DATE_PARAMETERS);
        }
    }

    private void validateRecurrenceParameters(GetAvailableSpacesQuery q) {
        if (q.startDate().isEmpty() && q.endDate().isEmpty()) return;

        validateRecurrenceDatesExist(q);
        validateDateRangeIsValid(q);
        validateRecurrenceTypeIsPresent(q);
    }

    private void validateRecurrenceDatesExist(GetAvailableSpacesQuery q) {
        if (q.startDate().isEmpty() || q.endDate().isEmpty()) {
            throw new InvalidGetAvailableSpacesParametersException(SpaceExceptionCode.MISSING_RECURRENCE_DATA);
        }
    }

    private void validateDateRangeIsValid(GetAvailableSpacesQuery q) {
        LocalDate start = q.startDate().get();
        LocalDate end = q.endDate().get();

        if (start.isAfter(end)) {
            throw new InvalidGetAvailableSpacesParametersException(SpaceExceptionCode.INVALID_DATE_RANGE);
        }
    }

    private void validateRecurrenceTypeIsPresent(GetAvailableSpacesQuery q) {
        if (q.recurrenceType().isEmpty()) {
            throw new InvalidGetAvailableSpacesParametersException(SpaceExceptionCode.MISSING_RECURRENCE_TYPE);
        }
    }

    private void validateTimeParameters(GetAvailableSpacesQuery q) {
        if (q.startTime().isEmpty() || q.endTime().isEmpty()) {
            throw new InvalidGetAvailableSpacesParametersException(SpaceExceptionCode.MISSING_TIME_RANGE);
        }

        validateTimeRange(q.startTime().get(), q.endTime().get());
    }

    private void validateTimeRange(LocalTime startTime, LocalTime endTime) {
        if (startTime.isAfter(endTime)) {
            throw new InvalidGetAvailableSpacesParametersException(SpaceExceptionCode.INVALID_TIME_RANGE);
        }
    }
}