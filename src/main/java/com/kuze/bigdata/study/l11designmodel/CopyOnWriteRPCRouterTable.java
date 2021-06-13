package com.kuze.bigdata.study.l11designmodel;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class CopyOnWriteRPCRouterTable {

    public final class Router {
        private final String ip;
        private final Integer port;
        private final String interfaceStr;

        public Router(String ip, Integer port, String interfaceStr) {
            this.ip = ip;
            this.port = port;
            this.interfaceStr = interfaceStr;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Router) {
                Router r = (Router) obj;
                return interfaceStr.equals(r.interfaceStr) && ip.equals(r.ip) && port.equals(r.port);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }
    }

    public class RouterTable {

        ConcurrentHashMap<String, CopyOnWriteArraySet<Router>> cr = new ConcurrentHashMap<>();

        public Set<Router> get(String interfaceStr) {
            Set<Router> routers = cr.get(interfaceStr);
            return routers;
        }

        public void add(Router router) {
            Set set = cr.computeIfAbsent(router.interfaceStr, r -> new CopyOnWriteArraySet<>());
            set.add(router);
        }

        public void remove(Router router) {
            Set set = cr.get(router.interfaceStr);
            if (set != null) {
                set.remove(router);
            }
        }

    }

}
