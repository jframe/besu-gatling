package besu.feeders;



import org.apache.commons.lang3.RandomUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class TransactionHashFeeder {

    List<String> transactionHashes = List.of("0x52c6f24f14ad4456364365af5e68184a00f3f7e0c7fe65846f37b3e0fc79ba83", "0xbc6e47c0326031496e74f86b1e67bbe12267c7f87513a714d5c1f0eee3de7d13" , "0xc25a237b837b96048a8f4d82f1139d46180a85c9c29f73d7583974e57aabc518", "0xb91f18ec1d596f5a5676813ec0cd490269ed3476f8f93e0e79597f0f4b209309", "0x173d6651c37c7be408cf54f5d97766ea5905176382639408744471a035b46c81", "0xe59d7df45a38ac1e58ecc8b7722f86d62e3dc37f6df8242a2a29ad4c148eed2e",
            "0x0f4b0dc53f904ae32fe114bc9a426f28ed01df13cf81e315ab0f0e7dd861481e", "0xe554d8dfa5262edbef7870b9578cda798848d4b986aff5c5e794f1285bb60f57", "0x3c51016a9c6027741852abaf9b6b3aa8396b3ea2d5c1247466ebf20c8ecfb8b8","0x1e2500d3178f009e5b73d18ebf8c3eca23d9f42031cd16bcddc595026caefd71");
    public Iterator<Map<String, Object>> getTransactionsHashes() {
        return Stream.generate(
                        (Supplier<Map<String, Object>>)
                                () -> {
                                    String transactionHash = getTransactionsHash();
                                    return Map.of(
                                            "id", RandomUtils.nextLong(),
                                            "transactionHash", transactionHash);})
                .iterator();
    }

    private String getTransactionsHash() {
        return transactionHashes.get(RandomUtils.nextInt(0,transactionHashes.size()-1));


    }

    public static void main(String[] args) {
        TransactionHashFeeder feeder = new TransactionHashFeeder();
        for (int i = 0; i <10 ; i++) {
            System.out.println(feeder.getTransactionsHash());
        }
    }
}
