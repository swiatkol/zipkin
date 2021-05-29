package zipkin2.diagram;

import lombok.Value;

// TODO when to use static factory method
@Value(staticConstructor = "create")
class Request {
  String method;
  String path;
  String params;
  String body;
}
