package zipkin2.diagram;

import zipkin2.Span;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class SpanExtensions {

  private static final Set<String> HTTP_METHODS = Stream.of(
    "GET", "POST", "PUT", "PATCH", "DELETE", "HEAD", "OPTIONS", "CONNECT", "TRACE"
  ).collect(Collectors.toSet());

  static Optional<String> extractMethod(Span span) {
    String spanName = span.name();
    if (spanName == null) {
      return Optional.empty();
    }
    String[] nameParts = spanName.split("\\s+");
    if (nameParts.length == 0) {
      return Optional.empty();
    }
    String method = nameParts[0].toUpperCase();
    if (!HTTP_METHODS.contains(method)) {
      throw new IllegalArgumentException("Extracted name '" + method + "' is not a proper HTTP method");
    }
    return Optional.of(method);
  }
}
