package com.frg.carservice.technical;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ModelObject {

  private final Map<String, Object> jsonObject;

  public ModelObject() {
    this.jsonObject = new LinkedHashMap<>();
  }

  public void addAttribute(String key, Object value) {
    this.jsonObject.put(key, value);
  }

  public void addAttribute(String key, ModelObject value) {
    this.jsonObject.put(key, value.toMap());
  }

  public void addArrayAttribute(String key, ModelObject... values) {

    if (values == null) {
      this.jsonObject.put(key, null);
      return;
    }

    List<Map<String, Object>> jsonArray = new ArrayList<>();
    for (ModelObject value : values) {
      jsonArray.add(value.toMap());
    }
    this.jsonObject.put(key, jsonArray);
  }

  @JsonAnyGetter
  public Map<String, Object> toMap() {
    return this.jsonObject;
  }
}
