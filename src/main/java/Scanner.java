import java.util.ArrayList;
import java.util.List;

public class Scanner {
    private String source ;
    private final List<String> tokens = new ArrayList<>();

    Scanner(String souce){
        this.source = souce;
    }

    List<String> scanToken(){
        tokens.add("EOF  null");
        return tokens ;
    }

}
