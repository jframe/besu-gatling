package besu.feeders;

import org.apache.commons.lang3.RandomUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class LatestBlockHashFeeder {
    final Web3j web3 = Web3j.build(new HttpService(String.format("%s:%d",System.getProperty("besu-rpc-host", "localhost"), Integer.getInteger("besu-rpc-port", 8545))));
    public Iterator<Map<String, Object>> getLatestBlockhash() {
        return Stream.generate(
                        (Supplier<Map<String, Object>>)
                                () ->
                                        Map.of(
                                                "id",
                                                RandomUtils.nextLong(),
                                                "blockHash",
                                                getBlockHashForLtest()))
                .iterator();
    }

    private Object getBlockHashForLtest() {
        Request<?, EthBlock> ethBlockRequest;
        ethBlockRequest = web3.ethGetBlockByNumber(DefaultBlockParameter.valueOf(DefaultBlockParameterName.LATEST.getValue()), true);
        String hash = null;
        try {
            hash = ethBlockRequest.send().getBlock().getHash();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hash;

    }
}
