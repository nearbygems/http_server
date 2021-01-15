package nearbygems;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
  private final static String DELIMITER = "\r\n\r\n";
  private final static String NEW_LINE = "\r\n";
  private final static String HEADER_DELIMITER = ":";

  private final String message;

  private final HttpMethod method;
  private final String url;
  private final Map<String, String> headers;
  private final String body;

  public HttpRequest(String message) {

    this.message = message;

    var parts = message.split(DELIMITER);
    var head = parts[0];
    var headers = head.split(NEW_LINE);
    var firstLine = headers[0].split(" ");

    method = HttpMethod.valueOf(firstLine[0]);
    url = firstLine[1];

    this.headers = Collections.unmodifiableMap(
      new HashMap<>() {{
        for (int i = 1; i < headers.length; i++) {
          var headerPart = headers[i].split(HEADER_DELIMITER, 2);
          put(headerPart[0].trim(), headerPart[1].trim());
        }
      }}
    );

    var bodyLength = this.headers.get("Content-Length");
    var length = bodyLength != null ? Integer.parseInt(bodyLength) : 0;

    this.body = parts.length > 1 ? parts[1].trim().substring(0, length) : "";
  }

  public String getMessage() {
    return message;
  }

  public HttpMethod getMethod() {
    return method;
  }

  public String getUrl() {
    return url;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public String getBody() {
    return body;
  }
}
