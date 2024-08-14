/**
 * AutoCare REST API - Technical model used to construct JSON objects with flexibility.
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

public final class ModelObjectBuilder {

  private final ModelObject modelObject;

  private ModelObjectBuilder() {
    this.modelObject = new ModelObject();
  }

  public static ModelObjectBuilder createBuilder() {
    return new ModelObjectBuilder();
  }

  public ModelObjectBuilder addAttribute(String key, Object value) {
    this.modelObject.addAttribute(key, value);
    return this;
  }

  public ModelObjectBuilder addAttribute(String key, ModelObject value) {
    this.modelObject.addAttribute(key, value);
    return this;
  }

  public ModelObjectBuilder addArrayAttribute(String key, ModelObject... values) {
    this.modelObject.addArrayAttribute(key, values);
    return this;
  }

  public ModelObject build() {
    return this.modelObject;
  }
}
