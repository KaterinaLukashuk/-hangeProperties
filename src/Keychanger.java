import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Keychanger {
    String filename;
    FileInputStream input;
    FileOutputStream output;
    Properties prop;



    public void setFilename(String filename) {
        this.filename = filename;
    }


    // lambda which corrects the keys

    Function change =
            (entry) -> {
               entry = entry.toString();
               String key = ((String)entry).substring(0,((String) entry).indexOf('='));
               String value = ((String)entry).substring(((String) entry).indexOf('=')+ 1 );
                key = ((String) key).toLowerCase();
                int i = ((String) key).indexOf('_', 0);
                while (i != -1) {
                    key = ((String) key).substring(0, i) + "." + ((String) key).substring(i + 1);
                    i = ((String) key).indexOf('_', i);
                }
                return key + "=" + value;
            };

    // lambda which takes key-part of entry

    Function key = (key) ->
    {
        key = ((String) key).substring(0, ((String) key).indexOf('='));
        return key;
    };

    // lambda which takes value-part of entry

    Function value = (value) ->
    {
        value = (((String) value).substring(((String) value).indexOf('=') + 1));
        return value;
    };

    //read properties

    public Properties read(String filename) throws IOException {
        input = new FileInputStream( filename);
        prop = new Properties();
        prop.load(input);
        return prop;
    }

    //write properties

    public void write(String filename, Properties prop) throws IOException {
        output = new FileOutputStream(filename);
        prop.store(output,"new properties");
    }

    //change properties

    public Properties change() {
           Map map = (Map) prop.entrySet().stream().map(change).collect(Collectors.toMap(key,value));
           prop.clear();
           prop.putAll(map);
           prop.list(System.out);
           return prop;
    }
}
