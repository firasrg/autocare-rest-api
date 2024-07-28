package com.frg.carservice.technical;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ModelObjectBuilder {
  private final Map<String, Object> jsonObject;

  private ModelObjectBuilder() {
    this.jsonObject = new LinkedHashMap<>();
  }

  public static ModelObjectBuilder createBuilder() {
    return new ModelObjectBuilder();
  }

  public ModelObjectBuilder addAttribute(String key, String value) {
    jsonObject.put(key, value);
    return this;
  }

  public ModelObjectBuilder addAttribute(String key, long value) {
    jsonObject.put(key, value);
    return this;
  }

  public ModelObjectBuilder addAttribute(String key, Map<String, Object> value) {
    jsonObject.put(key, value);
    return this;
  }

  public ModelObjectBuilder addAttribute(String key, ModelObjectBuilder builder) {
    jsonObject.put(key, builder.build());
    return this;
  }

  public void addArrayAttribute(String key, ModelObjectBuilder... builders) {
    List<Map<String, Object>> jsonArray = new ArrayList<>();
    for (ModelObjectBuilder builder : builders) {
      jsonArray.add(builder.build());
    }
    jsonObject.put(key, jsonArray);
  }

  public Map<String, Object> build() {
    return jsonObject;
  }
}
