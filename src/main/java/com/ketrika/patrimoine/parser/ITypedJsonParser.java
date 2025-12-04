package com.ketrika.patrimoine.parser;

public interface ITypedJsonParser<T> extends IJsonParser<T> {
  String type(); // e.g., "bank", "loan", "stock"
}
