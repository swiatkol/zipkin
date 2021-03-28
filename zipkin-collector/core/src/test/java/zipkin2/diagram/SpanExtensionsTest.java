package zipkin2.diagram;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import zipkin2.Span;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class SpanExtensionsTest {

  @Nested
  @TestInstance(TestInstance.Lifecycle.PER_CLASS)
  class ExtractingMethod {

    @ParameterizedTest(name = "[{index}] when span name is \"{0}\"")
    @MethodSource
    void shouldExtractMethodFromSpan(String spanName, String expectedMethod) {
      // given
      Span span = SPAN.toBuilder().name(spanName).build();

      // when
      Optional<String> method = SpanExtensions.extractMethod(span);

      // then
      assertThat(method).hasValue(expectedMethod);
    }

    private Stream<Arguments> shouldExtractMethodFromSpan() {
      return Stream.of(
        Arguments.of("get /books", "GET"),
        Arguments.of("GET /books", "GET"),
        Arguments.of("post /books", "POST"),
        Arguments.of("get\t/books", "GET"),
        Arguments.of("get", "GET"),
        Arguments.of("get ", "GET"),
        Arguments.of("get b k", "GET")
      );
    }

    @ParameterizedTest(name = "[{index}] when span name is \"{0}\"")
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void shouldReturnEmptyOptional(String spanName) {
      // given
      Span span = SPAN.toBuilder().name(spanName).build();

      // when
      Optional<String> method = SpanExtensions.extractMethod(span);

      // then
      assertThat(method).isEmpty();
    }

    @ParameterizedTest(name = "[{index}] of type \"{0}\" when span is \"{1}\"")
    @MethodSource
    void shouldThrowException(Class<Throwable> throwableClass, Span span) {
      // when
      Throwable throwable = catchThrowable(() -> SpanExtensions.extractMethod(span));

      // then
      assertThat(throwable).isExactlyInstanceOf(throwableClass);
    }

    private Stream<Arguments> shouldThrowException() {
      return Stream.of(
        Arguments.of(IllegalArgumentException.class, SPAN.toBuilder().name("g").build()),
        Arguments.of(IllegalArgumentException.class, SPAN.toBuilder().name("g ").build()),
        Arguments.of(IllegalArgumentException.class, SPAN.toBuilder().name("g e t").build()),
        Arguments.of(IllegalArgumentException.class, SPAN.toBuilder().name("g e t ").build()),
        Arguments.of(IllegalArgumentException.class, SPAN.toBuilder().name(" g e t").build()),
        Arguments.of(IllegalArgumentException.class, SPAN.toBuilder().name(" get").build()),
        Arguments.of(IllegalArgumentException.class, SPAN.toBuilder().name("get/books").build()),
        Arguments.of(NullPointerException.class, null)
      );
    }
  }

  @Nested
  @TestInstance(TestInstance.Lifecycle.PER_CLASS)
  class ExtractingPath {

    @ParameterizedTest(name = "[{index}] when span name is \"{0}\"")
    @MethodSource
    void shouldExtractPathFromSpanName(String spanName, String expectedPath) {
      // given
      Span span = SPAN.toBuilder().name(spanName).build();

      // when
      Optional<String> path = SpanExtensions.extractPath(span);

      // then
      assertThat(path).hasValue(expectedPath);
    }

    private Stream<Arguments> shouldExtractPathFromSpanName() {
      return Stream.of(
        Arguments.of("get /books", "/books"),
        Arguments.of("get /books sth", "/books"),
        Arguments.of("get\t/books sth", "/books"),
        Arguments.of("get /", "/"),
        Arguments.of(" /books", "/books")
      );
    }

    @ParameterizedTest(name = "[{index}] when span name is \"{0}\"")
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n", "get", "get "})
    void shouldReturnEmptyOptional(String spanName) {
      // given
      Span span = SPAN.toBuilder().name(spanName).build();

      // when
      Optional<String> path = SpanExtensions.extractPath(span);

      // then
      assertThat(path).isEmpty();
    }

    @ParameterizedTest(name = "[{index}] of type \"{0}\" when span is \"{1}\"")
    @MethodSource
    void shouldThrowException(Class<Throwable> throwableClass, Span span) {
      // when
      Throwable throwable = catchThrowable(() -> SpanExtensions.extractPath(span));

      // then
      assertThat(throwable).isExactlyInstanceOf(throwableClass);
    }

    private Stream<Arguments> shouldThrowException() {
      return Stream.of(
        Arguments.of(IllegalArgumentException.class, SPAN.toBuilder().name("get books").build()),
        Arguments.of(IllegalArgumentException.class, SPAN.toBuilder().name(" books").build()),
        Arguments.of(NullPointerException.class, null)
      );
    }
  }

  // TODO extract path from http.path tag

  private static final Span SPAN = Span.newBuilder()
    .traceId("26f3f26152e42cd2")
    .id("077bd4280ffca791")
    .build();
}
