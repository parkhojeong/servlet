package hello.servlet.web.frontcontrollerimpl;

import java.util.HashMap;

public class Model {
    HashMap<String, Object> attributes = new HashMap<>();

    public void addAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    public HashMap<String, Object> getAttributes() {
        return attributes;
    }
}
