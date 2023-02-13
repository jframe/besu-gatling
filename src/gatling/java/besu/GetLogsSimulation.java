package besu;

import besu.feeders.LatestBlockHashFeeder;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.time.Duration;

import static besu.JsonApiCalls.ethGetLogs;
import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.atOnceUsers;
import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.jsonPath;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class GetLogsSimulation extends Simulation {
    final String host = System.getProperty("besu-rpc-host", "localhost");
    final Integer port = Integer.getInteger("besu-rpc-port", 8545);
    final String baseUrl = "http://" + host + ":" + port;

    final ScenarioBuilder getLogsforLatest =
            scenario("Get Logs for latest Block")
                    .feed(new LatestBlockHashFeeder().getLatestBlockhash())
                    .exec(
                            http("get Logs for latest Block")
                                    .post("/")
                                    .body(StringBody(ethGetLogs))
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

        setUp(getLogsforLatest.injectOpen(constantUsersPerSec(2).during(Duration.ofMinutes(5))))
                .protocols(httpProtocol);
    }
}
