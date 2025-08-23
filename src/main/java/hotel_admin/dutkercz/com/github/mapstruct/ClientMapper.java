package hotel_admin.dutkercz.com.github.mapstruct;

import hotel_admin.dutkercz.com.github.dtos.ClientUpdate;
import hotel_admin.dutkercz.com.github.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "cpf", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "gender", ignore = true)
    @Mapping(target = "status", ignore = true)
    void updateEntityFromDto(ClientUpdate update, @MappingTarget Client entity);

}
