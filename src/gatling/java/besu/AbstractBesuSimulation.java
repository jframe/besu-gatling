package besu;

import io.gatling.javaapi.core.Simulation;

public class AbstractBesuSimulation extends Simulation {

    final String host = System.getProperty("besu-rpc-host", "localhost");
    final Integer port = Integer.getInteger("besu-rpc-port", 8545);
    final String baseUrl = "http://" + host + ":" + port;

}
