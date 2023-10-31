package besu;

import besu.feeders.BlockRangeFeeder;
import besu.feeders.TransactionHashFeeder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import org.simpleflatmapper.util.TransformIterator;

import java.time.Duration;

import static besu.JsonApiCalls.debugTraceTransaction;
import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.jsonPath;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class DebugTraceTransactionSimulation extends AbstractBesuSimulation {

    final ScenarioBuilder debugTransactionTest =
            scenario("Generate Debug Transaction traces")
                    .feed(new TransactionHashFeeder().getTransactionsHashes())
                    .exec(
                            http("Generate Debug Transaction  traces")
                                    .post("/")
                                    .body(StringBody(debugTraceTransaction))
                                    .asJson()
                                    .check(status().is(200))
                                    .check(jsonPath("$.id").isEL("#{id}")));

    final HttpProtocolBuilder httpProtocol =  http.baseUrl(baseUrl)
            .acceptHeader("*/*")
            .acceptLanguageHeader("en-US,en;q=0.5")
            .acceptEncodingHeader("gzip, deflate, br")
            .contentTypeHeader("application/json")
            .userAgentHeader("Gatling Test");

    {
        System.out.println("Running Gatling Scenarios on " + baseUrl);

        setUp(debugTransactionTest.injectOpen(constantUsersPerSec(1).during(Duration.ofMinutes(5))))
                .protocols(httpProtocol);

    }
}
