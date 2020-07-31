package hash;

import java.util.*;

/**
 * @author wulizi
 * 没有虚拟节点的一致性hash
 */
public class ConsistentHashingWithoutVirtualNode {
    public static void main(String[] args) {
        ConsistentHashingWithoutVirtualNode hash = new ConsistentHashingWithoutVirtualNode();
        hash.init();
        // 生成随机数进行测试
        Map<String, Integer> resMap = new HashMap<>();

        for (int i = 0; i < 100000; i++) {
            Integer widgetId = (int)(Math.random() * 10000);
            String server = hash.getServer(widgetId.toString());
            if (resMap.containsKey(server)) {
                resMap.put(server, resMap.get(server) + 1);
            } else {
                resMap.put(server, 1);
            }
        }

        resMap.forEach(
                (k, v) -> {
                    System.out.println("group " + k + ": " + v + "(" + v/1000.0D +"%)");
                }
        );
    }

    private SortedMap<Integer, String> serverMap;
    private final String[] servers = {
            "192.168.0.0:111", "192.168.0.1:111", "192.168.0.2:111",
            "192.168.0.3:111", "192.168.0.4:111"
    };

    public ConsistentHashingWithoutVirtualNode() {
        this.serverMap = new TreeMap<>();

    }

    private String[] getServers() {
        return servers;
    }

    private void init() {
        for (String server : getServers()) {
            int hash = HashUtil.getHash(server);
            serverMap.put(hash, server);
        }
    }

    public String getServer(String wightKey) {
        int hash = HashUtil.getHash(wightKey);
        SortedMap<Integer,String> subMap = serverMap.tailMap(hash);
        if (subMap.size() == 0) {
          return serverMap.get(serverMap.firstKey());
        }
        return serverMap.get(subMap.firstKey());
    }
}
