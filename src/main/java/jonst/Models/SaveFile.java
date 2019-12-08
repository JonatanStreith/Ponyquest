package jonst.Models;

public class SaveFile {


    private String name;
    private String path;

    public SaveFile(String name, String path) {

        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
}
