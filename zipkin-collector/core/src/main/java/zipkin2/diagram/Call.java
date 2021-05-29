package zipkin2.diagram;

import lombok.Value;
import zipkin2.Span;

@Value(staticConstructor = "create")
class Call {
  String from;
  String to;
  Request request;
  Response response;
}
