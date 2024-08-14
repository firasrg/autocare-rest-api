/**
 * AutoCare REST API - Technical model builder used to construct JSON objects with flexibility.
 * Copyright (C) 2024  AutoCare REST API original author or authors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this application.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.frg.autocare.technical;

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
