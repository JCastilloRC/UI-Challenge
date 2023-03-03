package helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import miscclasses.Genre;
import miscclasses.Movie;
import miscclasses.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Helper {
    public static User parseUserYAML(String yamlPath) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        return mapper.readValue(new File(yamlPath), User.class);
    }
    public static Movie parseMovieYAML(String yamlPath) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        return mapper.readValue(new File(yamlPath), Movie.class);
    }

    public static List<Movie> parseMovieListYAML(String yamlPath) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        CollectionType listType = mapper.getTypeFactory()
                .constructCollectionType(ArrayList.class, Movie.class);
        mapper.findAndRegisterModules();
        return mapper.readValue(new File(yamlPath), listType);
    }
    public static Genre parseGenreYAML(String yamlPath) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        return mapper.readValue(new File(yamlPath), Genre.class);
    }
    public static boolean datesOrderedAscending(Date[] dates){
        for(int i=0; i<dates.length-1; i++){
            if(dates[i].compareTo(dates[i+1])>=0){
                return false;
            }
        }
        return true;
    }
}
