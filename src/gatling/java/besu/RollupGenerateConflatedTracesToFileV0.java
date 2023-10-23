package besu;

import besu.feeders.BlockRangeFeeder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;

import static besu.JsonApiCalls.rollupGenerateConflatedTracesToFileV0;
import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.atOnceUsers;
import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.jsonPath;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class RollupGenerateConflatedTracesToFileV0 extends AbstractBesuSimulation {

    final ScenarioBuilder generateConflatedTracesTest =
            scenario("Generate Conflated traces")
                    .feed(new BlockRangeFeeder().getBlockRange())
                    .exec(
                            http("Generate Conflated traces")
                                    .post("/")
                                    .body(StringBody(rollupGenerateConflatedTracesToFileV0))
                                    .asJson()
                                    .check(status().is(200))
                                    .check(jsonPath("$.id").isEL("#{id}"))).pause(4);

    final HttpProtocolBuilder httpProtocol =  http.baseUrl(baseUrl)
            .acceptHeader("*/*")
                                .acceptLanguageHeader("en-US,en;q=0.5")
                                .acceptEncodingHeader("gzip, deflate, br")
                                .contentTypeHeader("application/json")
                                .userAgentHeader("Gatling Test");

    {
        System.out.println("Running Gatling Scenarios on " + baseUrl);

        var scn = scenario("My Scenario")
                .exec(http("My Request")
                        .get("http://my-endpoint-url.com"))
                .pause(4) ;// Pause for 4 seconds

        setUp(
                generateConflatedTracesTest.injectOpen(atOnceUsers(1)) // Injects a single user
        ).protocols(httpProtocol).maxDuration(Duration.ofMinutes(10))  ;

    }
}
