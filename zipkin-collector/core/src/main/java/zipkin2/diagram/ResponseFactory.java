package zipkin2.diagram;

import zipkin2.Span;

public class ResponseFactory {

  // TODO validate and test more
  Response create(Span span) {
    String status = span.tags().get("response.status");
    String body = span.tags().get("response.body");
    return Response.create(status, body);
  }
}
