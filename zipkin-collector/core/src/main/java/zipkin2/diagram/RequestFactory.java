package zipkin2.diagram;

import zipkin2.Span;

public class RequestFactory {

  // TODO validate input and output
  // TODO think about changing this method
  Request create(Span span) {
    String methodName = SpanExtensions.extractMethod(span).orElse(null);
    String path = SpanExtensions.extractPath(span).orElse(null);
    String params = span.tags().get("request.params");
    String body = span.tags().get("request.body");
    return new Request(methodName, path, params, body);
  }
}
