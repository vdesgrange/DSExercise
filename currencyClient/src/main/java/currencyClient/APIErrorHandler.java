package client;

import java.io.IOException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.http.client.ClientHttpResponse;

public class APIErrorHandler implements ResponseErrorHandler {
  @Override
  public void handleError(ClientHttpResponse response) throws IOException {
    System.out.println("Error " + response.getStatusCode().toString() + " : " + response.getStatusText());
  }

  @Override
  public boolean hasError(ClientHttpResponse response) throws IOException {
      if (!response.getStatusCode().is2xxSuccessful()) {
        System.out.println("Error " + response.getStatusCode().toString() + " : " + response.getStatusText());
        return true;
      }

      return false;
  }
}
