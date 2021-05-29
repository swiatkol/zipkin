package zipkin2.diagram;

import lombok.Value;
import zipkin2.Span;

@Value(staticConstructor = "create")
class Response {
  String status;
  String body;
}
