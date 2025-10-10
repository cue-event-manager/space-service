package cue.edu.co.api.common.mappers;

import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface OptionalMapper {

    default Optional<String> toOptional(String value) {
        return Optional.ofNullable(value);
    }

    default Optional<Long> toOptional(Long value) {
        return Optional.ofNullable(value);
    }


    default String fromOptional(Optional<String> value) {
        return value.orElse(null);
    }
}
