package br.com.onepiece.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

public class CustomModelMapper {
      private static ModelMapper mapper = new ModelMapper();

    public static <Origin, Destination> Destination parseObject(Origin origin, Class<Destination> destination) {
        return mapper.map(origin, destination);
    }

    public static <Origin, Destination> List<Destination> parseObjectList(List<Origin> origin, Class<Destination> destination) {
        List<Destination> destinationList = new ArrayList<Destination>();
        for (Origin o : origin) {
            destinationList.add(mapper.map(o, destination));
        }
        return  destinationList;
    }
}
