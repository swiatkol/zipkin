package zipkin2.diagram;

import org.junit.jupiter.api.Test;
import zipkin2.Span;

import static org.assertj.core.api.Assertions.assertThat;

class RequestFactoryTest {

  private final RequestFactory requestFactory = new RequestFactory();

  @Test
  void shouldCreateRequestForServerSpanWithoutParamsAndBody() {
    // given
    Request expectedRequest = Request.create("GET", "/books", null, null);
    Span span = Span.newBuilder()
      .traceId("26f3f26152e42cd2")
      .id("077bd4280ffca791")
      .kind(Span.Kind.SERVER)
      .name("get /books")
      .build();

    // when
    Request request = requestFactory.create(span);

    // then
    assertThat(request).isEqualTo(expectedRequest);
  }

  @Test
  void shouldCreateRequestForServerSpanWithParamsAndBody() {
    // given
    String params = "{\"ISBN\":\"string\"}";
    String body = "{\"title\":\"string\"}";
    Request expectedRequest = Request.create("POST", "/books", params, body);
    Span span = Span.newBuilder()
      .traceId("26f3f26152e42cd2")
      .id("077bd4280ffca791")
      .kind(Span.Kind.SERVER)
      .putTag("request.body", body)
      .putTag("request.params", params)
      .name("POST /books").build();

    // when
    Request request = requestFactory.create(span);

    // then
    assertThat(request).isEqualTo(expectedRequest);
  }

  @Test
  void shouldCreateRequestForClientSpan() {
    // given
    Request expectedRequest = Request.create("GET", null, null, null);
    Span span = Span.newBuilder()
      .traceId("26f3f26152e42cd2")
      .id("077bd4280ffca791")
      .kind(Span.Kind.CLIENT)
      .name("get").build();

    // when
    Request request = requestFactory.create(span);

    // then
    assertThat(request).isEqualTo(expectedRequest);
  }

  // TODO test validation
}
