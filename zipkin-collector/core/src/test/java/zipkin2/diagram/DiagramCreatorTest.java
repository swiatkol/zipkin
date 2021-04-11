package zipkin2.diagram;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import zipkin2.Span;

import static org.assertj.core.api.Assertions.assertThat;

class DiagramCreatorTest {

  private final DiagramCreator diagramCreator = new DiagramCreator();

  @Nested
  class CreateRequest {

    @Test
    void shouldCreateRequestForServerSpanWithoutParamsAndBody() {
      // given
      Request expectedRequest = new Request("GET", "/books", null, null);
      Span span = Span.newBuilder()
        .traceId("26f3f26152e42cd2")
        .id("077bd4280ffca791")
        .kind(Span.Kind.SERVER)
        .name("get /books")
        .build();

      // when
      Request request = diagramCreator.createRequest(span);

      // then
      assertThat(request).isEqualTo(expectedRequest);
    }

    @Test
    void shouldCreateRequestForServerSpanWithParamsAndBody() {
      // given
      String params = "{\"ISBN\":\"string\"}";
      String body = "{\"title\":\"string\"}";
      Request expectedRequest = new Request("POST", "/books", params, body);
      Span span = Span.newBuilder()
        .traceId("26f3f26152e42cd2")
        .id("077bd4280ffca791")
        .kind(Span.Kind.SERVER)
        .putTag("request.body", body)
        .putTag("request.params", params)
        .name("POST /books").build();

      // when
      Request request = diagramCreator.createRequest(span);

      // then
      assertThat(request).isEqualTo(expectedRequest);
    }

    @Test
    void shouldCreateRequestForClientSpan() {
      // given
      Request expectedRequest = new Request("GET", null, null, null);
      Span span = Span.newBuilder()
        .traceId("26f3f26152e42cd2")
        .id("077bd4280ffca791")
        .kind(Span.Kind.CLIENT)
        .name("get").build();

      // when
      Request request = diagramCreator.createRequest(span);

      // then
      assertThat(request).isEqualTo(expectedRequest);
    }
  }
}
