package jonst.Models;

import jonst.Data.SystemData;
import jonst.Models.Objects.Creature;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonTest {

    @Test
    public void Test() {

        String full = "theEnd";

        String part = full.substring(full.length()-3);

        System.out.println(part);
    }
}
