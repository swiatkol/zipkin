package zipkin2.diagram;

import org.junit.jupiter.api.Test;
import zipkin2.Span;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseFactoryTest {

  private final ResponseFactory responseFactory = new ResponseFactory();

  @Test
  void shouldCreateResponseFromSpan() {
    // given
    String body = "{\"id\":\"string\"}";
    String status = "200";
    Response expectedResponse = Response.create(status, body);
    Span span = Span.newBuilder()
      .traceId("26f3f26152e42cd2")
      .id("077bd4280ffca791")
      .kind(Span.Kind.SERVER)
      .putTag("response.status", "200")
      .putTag("response.body", body)
      .build();

    // when
    Response request = responseFactory.create(span);

    // then
    assertThat(request).isEqualTo(expectedResponse);
  }

  @Test
  void shouldCreateResponseFromSpanWithoutStatusAndBody() {
    // given
    Response expectedResponse = Response.create(null, null);
    Span span = Span.newBuilder()
      .traceId("26f3f26152e42cd2")
      .id("077bd4280ffca791")
      .kind(Span.Kind.SERVER)
      .build();

    // when
    Response request = responseFactory.create(span);

    // then
    assertThat(request).isEqualTo(expectedResponse);
  }
}
