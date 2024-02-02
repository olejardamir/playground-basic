import ca.uhn.fhir.rest.client.api.IClientInterceptor;
import ca.uhn.fhir.rest.client.api.IHttpRequest;
import ca.uhn.fhir.rest.client.api.IHttpResponse;
import ca.uhn.fhir.util.StopWatch;

public class StopwatchTimer implements IClientInterceptor {

    private StopWatch requestStopWatch;
    private long requestTime; // Store the elapsed time here

    public StopwatchTimer() {
        requestStopWatch = new StopWatch();
        requestTime = 0;
    }

    @Override
    public void interceptRequest(IHttpRequest iHttpRequest) {
        requestStopWatch.restart();
        requestTime = 0;
    }

    @Override
    public void interceptResponse(IHttpResponse iHttpResponse) {
        requestTime = requestStopWatch.getMillisAndRestart();
    }

    // Method to retrieve the elapsed time
    public long getRequestTime() {
        return requestTime;
    }

}