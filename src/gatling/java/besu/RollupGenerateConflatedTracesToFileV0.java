package besu;

import besu.feeders.BlockRangeFeeder;
import besu.feeders.LatestBlockHashFeeder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;

import static besu.JsonApiCalls.ethGetLogs;
import static besu.JsonApiCalls.rollupGenerateConflatedTracesToFileV0;
import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.jsonPath;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class RollupGenerateConflatedTracesToFileV0 extends AbstractBesuSimulation {

    final ScenarioBuilder generateConflatedTracesTest =
            scenario("Get Logs for latest Block")
                    .feed(new BlockRangeFeeder().getBlockRange())
                    .exec(
                            http("get Logs for latest Block")
                                    .post("/")
                                    .body(StringBody(rollupGenerateConflatedTracesToFileV0))
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

        setUp(generateConflatedTracesTest.injectOpen(constantUsersPerSec(10).during(Duration.ofMinutes(5))))
                .protocols(httpProtocol);
    }
}
