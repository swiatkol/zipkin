package zipkin2.diagram;

import lombok.AllArgsConstructor;
import zipkin2.Span;

@AllArgsConstructor
public class CallFactory {

  private static final String CLIENT = "client";

  private final RequestFactory requestFactory;
  private final ResponseFactory responseFactory;

  // TODO test it
  Call create(Span span, boolean isRootSpan) {
    Request request = requestFactory.create(span);
    Response response = responseFactory.create(span);
    String from = isRootSpan ? CLIENT : span.remoteServiceName();
    return Call.create(from, span.localServiceName(), request, response);
  }
}
