package com.frg.carservice.technical;

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
