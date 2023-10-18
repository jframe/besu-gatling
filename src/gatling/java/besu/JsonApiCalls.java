package besu;

public class JsonApiCalls {
  public static final String ethGetBlockNumber =
      """
            {
                "jsonrpc": "2.0",
                "method": "eth_blockNumber",
                "params": [],
                "id": #{id}
            }
            """;
  public static final String ethGetBlockByNumberLatest =
      """
            {
                "jsonrpc": "2.0",
                "method": "eth_getBlockByNumber",
                "params": [
                    "latest",
                    true
                ],
                "id": #{id}
            }
            """;

  public static final String ethGetBlockByNumberRandom =
      """
            {
                "jsonrpc": "2.0",
                "method": "eth_getBlockByNumber",
                "params": [
                    "#{blockNumber}",
                    true
                ],
                "id": #{id}
            }
            """;

  public static final String  ethGetLogs =
          """
                {
                    "jsonrpc": "2.0",
                    "method": "eth_getLogs",
                    "params": [{
                        "blockHash":"#{blockHash}"
                    }],
                    "id": #{id}
                }
                """;

  public static final String  rollupGenerateConflatedTracesToFileV0 =
          """
                {
                    "jsonrpc": "2.0",
                    "method": "rollup_generateConflatedTracesToFileV0",
                    "params": ["#{startBlock}","#{endBlock}", "6.16.0"],
                    "id": #{id}
                }
                """;
}
