package cue.edu.co.api.common.mappers;

import cue.edu.co.model.event.enums.RecurrenceType;
import org.mapstruct.Mapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface OptionalMapper {

    default Optional<String> toOptional(String value) {
        return Optional.ofNullable(value);
    }

    default Optional<Integer> toOptional(Integer value){return  Optional.ofNullable(value);};

    default Optional<LocalDate> toOptional(LocalDate value){return  Optional.ofNullable(value);}

    default Optional<LocalTime> toOptional(LocalTime value){return  Optional.ofNullable(value);}

    default Optional<RecurrenceType> toOptional(RecurrenceType value){return  Optional.ofNullable(value);}

    default Optional<Long> toOptional(Long value) {
        return Optional.ofNullable(value);
    }

    default String fromOptional(Optional<String> value) {
        return value.orElse(null);
    }
}
