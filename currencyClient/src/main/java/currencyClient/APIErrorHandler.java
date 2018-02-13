package client;

import java.io.IOException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.http.client.ClientHttpResponse;

/**
 * APIErrorHandler
 * Handle HTTP error when calling API
 */
public class APIErrorHandler implements ResponseErrorHandler {
    /**
     * handleError
     * Display error message when http error.
     * @param response - ClientHttpResponse - http response
     */
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        System.out.println("Error " + response.getStatusCode().toString() + " : " + response.getStatusText());
    }

    /**
     * hasError
     * Check if there is an http error.
     * @param response - ClientHttpResponse - http response
     */
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
      if (!response.getStatusCode().is2xxSuccessful()) {
        System.out.println("Error " + response.getStatusCode().toString() + " : " + response.getStatusText());
        return true;
      }

      return false;
    }
}
