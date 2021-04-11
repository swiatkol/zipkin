package zipkin2.diagram;

import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zipkin2.Span;

import java.util.List;
import java.util.Optional;

class DiagramCreator {

  private static final Logger LOGGER = LoggerFactory.getLogger(DiagramCreator.class);

  Request createRequest(Span span) {
    String methodName = SpanExtensions.extractMethod(span).orElse(null);
    String path = SpanExtensions.extractPath(span).orElse(null);
    String params = span.tags().get("request.params");
    String body = span.tags().get("request.body");
    return new Request(methodName, path, params, body);
  }

  Response createResponse(Span span) {
    String status = span.tags().get("response.status");
      String body = span.tags().get("response.body");
    return new Response(status, body);
  }

  public Optional<String> buildDiagram(List<Span> spans, Span rootSpan) {
    return Optional.empty();
  }
}

@Value
class Request {
  private String method;
  private String path;
  private String params;
  private String body;
}

@Value
class Response {
  private String status;
  private String body;
}
