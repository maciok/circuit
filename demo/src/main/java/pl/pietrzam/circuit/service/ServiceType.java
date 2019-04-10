package pl.pietrzam.circuit.service;

import static java.lang.String.format;

import com.google.common.collect.MoreCollectors;
import java.util.stream.Stream;

public enum ServiceType {
  BAD,
  ERROR,
  GOOD,
  MAYBE;

  public static ServiceType valueOfIgnoringCase(final String name) {
    final String upperCasedName = name.toUpperCase();
    return Stream.of(values())
        .filter(type -> type.name().equals(upperCasedName))
        .collect(MoreCollectors.toOptional())
        .orElseThrow(() -> new IllegalArgumentException(format("Cannot find service type for name '%s'", name)));
  }
}
