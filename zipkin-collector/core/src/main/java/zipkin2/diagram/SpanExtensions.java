package zipkin2.diagram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zipkin2.Span;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class SpanExtensions {

  private static final Logger LOGGER = LoggerFactory.getLogger(SpanExtensions.class);

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
    if (span.kind() == Span.Kind.CLIENT && nameParts.length > 1) {
      LOGGER.warn(nameParts.length + " parts found in span name (" + spanName + "). Expecting only 1");
    }
    String method = nameParts[0].toUpperCase();
    if (!HTTP_METHODS.contains(method)) {
      throw new IllegalArgumentException("Extracted name '" + method + "' is not a proper HTTP method");
    }
    return Optional.of(method);
  }

  static Optional<String> extractPath(Span span) {
    String spanName = span.name();
    if (spanName == null) {
      return Optional.empty();
    }
    String[] nameParts = spanName.split("\\s+");
    if (nameParts.length < 2) {
      return Optional.empty();
    }
    if (nameParts.length > 2) {
     LOGGER.warn(nameParts.length + " parts found in span name (" + spanName + "). Expecting only 2");
    }
    String path = nameParts[1];
    if (!path.startsWith("/")) {
      throw new IllegalArgumentException("Extracted path '" + path + "' doesn't start with '/' char");
    }
    return Optional.of(path);
  }
}
