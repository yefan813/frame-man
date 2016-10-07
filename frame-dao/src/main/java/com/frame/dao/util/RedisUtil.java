package com.frame.dao.util;

import com.thoughtworks.xstream.XStream;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.*;

public class RedisUtil {
    private static final Logger log = LoggerFactory.getLogger(RedisUtil.class);
    private static XStream xstream = new XStream();
    private JedisPool jedisPool;
    private boolean use;
    private int defaultExpireTime = 10800;

    public RedisUtil() {
    }

    public void rpush(String key, String... val) {
        Jedis jedis = null;

        try {
            jedis = (Jedis) this.jedisPool.getResource();
            jedis.rpush(key, val);
            return;
        } catch (Exception var8) {
            log.error("redis操作发生异常", var8);
        } finally {
            returnJedis(jedis);

        }

    }

    public void lpush(String key, String... val) {
        Jedis jedis = null;

        try {
            jedis = (Jedis) this.jedisPool.getResource();
            jedis.lpush(key, val);
            return;
        } catch (Exception var8) {
            log.error("redis操作发生异常", var8);
        } finally {
            returnJedis(jedis);

        }

    }

    public Long llen(String key) {
        if (!this.use) {
            return null;
        } else {
            Jedis jedis = null;

            Long var4;
            try {
                jedis = (Jedis) this.jedisPool.getResource();
                Long e = jedis.llen(key);
                var4 = e;
                return var4;
            } catch (Exception var8) {
                log.error("redis操作发生异常", var8);
                var4 = null;
            } finally {
                returnJedis(jedis);

            }

            return var4;
        }
    }

    public List lrange(String key, int start, int end) {
        if (!this.use) {
            return null;
        } else {
            Jedis jedis = null;

            List var6;
            try {
                jedis = (Jedis) this.jedisPool.getResource();
                List e = jedis.lrange(key, (long) start, (long) end);
                var6 = e;
                return var6;
            } catch (Exception var10) {
                log.error("redis操作发生异常", var10);
                var6 = null;
            } finally {
                returnJedis(jedis);

            }

            return var6;
        }
    }

    public void lset(String key, int index, String value) {
        Jedis jedis = null;

        try {
            jedis = (Jedis) this.jedisPool.getResource();
            jedis.lset(key, (long) index, value);
            return;
        } catch (Exception var9) {
            log.error("redis操作发生异常", var9);
        } finally {
            returnJedis(jedis);

        }

    }

    public String lpop(String key) {
        if (!this.use) {
            return null;
        } else {
            Jedis jedis = null;

            String var4;
            try {
                jedis = (Jedis) this.jedisPool.getResource();
                String e = jedis.lpop(key);
                var4 = e;
                return var4;
            } catch (Exception var8) {
                log.error("redis操作发生异常", var8);
                var4 = null;
            } finally {
                returnJedis(jedis);

            }

            return var4;
        }
    }

    public String rpop(String key) {
        if (!this.use) {
            return null;
        } else {
            Jedis jedis = null;

            String var4;
            try {
                jedis = (Jedis) this.jedisPool.getResource();
                String e = jedis.rpop(key);
                var4 = e;
                return var4;
            } catch (Exception var8) {
                log.error("redis操作发生异常", var8);
                var4 = null;
            } finally {
                returnJedis(jedis);

            }

            return var4;
        }
    }

    public boolean exists(String key) {
        Jedis jedis = null;

        boolean var4;
        try {
            jedis = (Jedis) this.jedisPool.getResource();
            Boolean e = jedis.exists(key);
            var4 = e.booleanValue();
            return var4;
        } catch (Exception var8) {
            log.error("redis操作发生异常", var8);
            var4 = false;
        } finally {
            returnJedis(jedis);

        }

        return var4;
    }

    public Long del(String key) {
        Jedis jedis = null;

        Long var4;
        try {
            jedis = (Jedis) this.jedisPool.getResource();
            Long e = jedis.del(key);
            return e;
        } catch (Exception var8) {
            log.error("redis操作发生异常", var8);
            var4 = Long.valueOf(0L);
        } finally {
            returnJedis(jedis);

        }

        return var4;
    }

    public void set(String key, String val) {
        Jedis jedis = null;

        try {
            jedis = (Jedis) this.jedisPool.getResource();
            jedis.setex(key, this.defaultExpireTime, val);
            return;
        } catch (Exception var8) {
            log.error("redis操作发生异常", var8);
        } finally {
            returnJedis(jedis);

        }

    }

    public void set(String key, String val, boolean expire) {
        Jedis jedis = null;

        try {
            jedis = (Jedis) this.jedisPool.getResource();
            if (expire) {
                jedis.setex(key, this.defaultExpireTime, val);
            } else {
                jedis.set(key, val);
            }

            return;
        } catch (Exception var9) {
            log.error("redis操作发生异常", var9);
        } finally {
            returnJedis(jedis);

        }

    }

    public String get(String key) {
        if (!this.use) {
            return null;
        } else {
            Jedis jedis = null;

            String var4;
            try {
                jedis = (Jedis) this.jedisPool.getResource();
                String e = jedis.get(key);
                if (StringUtils.isNotBlank(e) && e.startsWith("<") && e.endsWith(">")) {
                    e = null;
                    this.del(key);
                }

                var4 = e;
                return var4;
            } catch (Exception var8) {
                log.error("redis操作发生异常", var8);
                var4 = null;
            } finally {
                returnJedis(jedis);

            }

            return var4;
        }
    }

    public String getset(String key, String value) {
        if (!this.use) {
            return null;
        } else {
            Jedis jedis = null;

            String var5;
            try {
                jedis = (Jedis) this.jedisPool.getResource();
                String e = jedis.getSet(key, value);
                var5 = e;
                return var5;
            } catch (Exception var9) {
                log.error("redis操作发生异常", var9);
                var5 = null;
            } finally {
                returnJedis(jedis);

            }

            return var5;
        }
    }

    public void lrem(String key, Integer count, String val) {
        Jedis jedis = null;

        try {
            jedis = (Jedis) this.jedisPool.getResource();
            jedis.lrem(key, (long) count.intValue(), val);
            return;
        } catch (Exception var9) {
            log.error("redis操作发生异常", var9);
        } finally {
            returnJedis(jedis);

        }

    }

    public void sadd(String key, String val) {
        Jedis jedis = null;

        try {
            jedis = (Jedis) this.jedisPool.getResource();
            jedis.sadd(key, new String[]{val});
            return;
        } catch (Exception var8) {
            log.error("redis操作发生异常", var8);
        } finally {
            returnJedis(jedis);

        }

    }

    public String spop(String key) {
        if (!this.use) {
            return null;
        } else {
            Jedis jedis = null;

            String var4;
            try {
                jedis = (Jedis) this.jedisPool.getResource();
                String e = jedis.spop(key);
                var4 = e;
                return var4;
            } catch (Exception var8) {
                log.error("redis操作发生异常", var8);
                var4 = null;
            } finally {
                returnJedis(jedis);

            }

            return var4;
        }
    }

    public Set<String> smembers(String key) {
        if (!this.use) {
            return null;
        } else {
            Jedis jedis = null;

            Set var4;
            try {
                jedis = (Jedis) this.jedisPool.getResource();
                Set e = jedis.smembers(key);
                var4 = e;
                return var4;
            } catch (Exception var8) {
                log.error("redis操作发生异常", var8);
                var4 = null;
            } finally {
                returnJedis(jedis);

            }

            return var4;
        }
    }

    public int scard(String key) {
        if (!this.use) {
            return 0;
        } else {
            Jedis jedis = null;

            byte var4;
            try {
                jedis = (Jedis) this.jedisPool.getResource();
                Long e = jedis.scard(key);
                int var10 = e.intValue();
                return var10;
            } catch (Exception var8) {
                log.error("redis操作发生异常", var8);
                var4 = 0;
            } finally {
                returnJedis(jedis);

            }

            return var4;
        }
    }

    public void expire(String key, int num) {
        Jedis jedis = null;

        try {
            jedis = (Jedis) this.jedisPool.getResource();
            jedis.expire(key, num);
            return;
        } catch (Exception var8) {
            log.error("redis操作发生异常", var8);
        } finally {
            returnJedis(jedis);

        }

    }

    public void setex(String key, int seconds, String value) {
        Jedis jedis = null;

        try {
            jedis = (Jedis) this.jedisPool.getResource();
            jedis.setex(key, seconds, value);
            return;
        } catch (Exception var9) {
            log.error("redis操作发生异常", var9);
        } finally {
            returnJedis(jedis);

        }

    }

    public void srem(String key, String val) {
        Jedis jedis = null;

        try {
            jedis = (Jedis) this.jedisPool.getResource();
            jedis.srem(key, new String[]{val});
            return;
        } catch (Exception var8) {
            log.error("redis操作发生异常", var8);
        } finally {
            returnJedis(jedis);

        }

    }

    public boolean sismember(String key, String member) {
        if (!this.use) {
            return false;
        } else {
            Jedis jedis = null;

            boolean var5;
            try {
                jedis = (Jedis) this.jedisPool.getResource();
                boolean e = jedis.sismember(key, member).booleanValue();
                return e;
            } catch (Exception var9) {
                log.error("redis操作发生异常", var9);
                var5 = false;
            } finally {
                returnJedis(jedis);

            }

            return var5;
        }
    }

    public Long incr(String key) {
        if (!this.use) {
            return null;
        } else {
            Jedis jedis = null;

            Object var4;
            try {
                jedis = (Jedis) this.jedisPool.getResource();
                Long e = jedis.incr(key);
                return e;
            } catch (Exception var8) {
                log.error("redis操作发生异常", var8);
                var4 = null;
            } finally {
                returnJedis(jedis);

            }

            return (Long) var4;
        }
    }

    public Long decr(String key) {
        if (!this.use) {
            return null;
        } else {
            Jedis jedis = null;

            Object var4;
            try {
                jedis = (Jedis) this.jedisPool.getResource();
                Long e = jedis.decr(key);
                return e;
            } catch (Exception var8) {
                log.error("redis操作发生异常", var8);
                var4 = null;
            } finally {
                returnJedis(jedis);

            }

            return (Long) var4;
        }
    }

    public Long hincrBy(String key, String field, long value) {
        if (!this.use) {
            return null;
        } else {
            Jedis jedis = null;

            Object var7;
            try {
                jedis = (Jedis) this.jedisPool.getResource();
                Long e = jedis.hincrBy(key, field, value);
                return e;
            } catch (Exception var11) {
                log.error("redis操作发生异常", var11);
                var7 = null;
            } finally {
                returnJedis(jedis);

            }

            return (Long) var7;
        }
    }

    public boolean hmexite(String key, String field) {
        if (!this.use) {
            return false;
        } else {
            Jedis jedis = null;

            boolean var5;
            try {
                jedis = (Jedis) this.jedisPool.getResource();
                boolean e = jedis.hexists(key, field).booleanValue();
                return e;
            } catch (Exception var9) {
                log.error("redis操作发生异常", var9);
                var5 = false;
            } finally {
                returnJedis(jedis);

            }

            return var5;
        }
    }

    public Long hgetNum(String key, String field) {
        if (!this.use) {
            return null;
        } else {
            Jedis jedis = null;

            Long var5;
            try {
                jedis = (Jedis) this.jedisPool.getResource();
                String e = jedis.hget(key, field);
                if (StringUtils.isNotBlank(e)) {
                    var5 = Long.valueOf(e);
                    return var5;
                }

                var5 = Long.valueOf(0L);
                return var5;
            } catch (Exception var9) {
                log.error("redis操作发生异常", var9);
                var5 = null;
            } finally {
                returnJedis(jedis);

            }

            return var5;
        }
    }

    public Long hsetnx(String key, String field, String value) {
        if (!this.use) {
            return null;
        } else {
            Jedis jedis = null;

            Object var6;
            try {
                jedis = (Jedis) this.jedisPool.getResource();
                Long e = jedis.hsetnx(key, field, value);
                return e;
            } catch (Exception var10) {
                log.error("redis操作发生异常", var10);
                var6 = null;
            } finally {
                returnJedis(jedis);

            }

            return (Long) var6;
        }
    }

    public Map<String, String> hgetAll(String key) {
        if (!this.use) {
            return null;
        } else {
            Jedis jedis = null;

            Object var4;
            try {
                jedis = (Jedis) this.jedisPool.getResource();
                Map e = jedis.hgetAll(key);
                return e;
            } catch (Exception var8) {
                log.error("redis操作发生异常", var8);
                var4 = null;
            } finally {
                returnJedis(jedis);

            }

            return (Map) var4;
        }
    }

    public boolean isUse() {
        return this.use;
    }

    public void setUse(boolean use) {
        this.use = use;
    }

    public Object getBySerialize(String key) {
        if (!this.use) {
            return null;
        } else {
            Jedis jedis = null;

            Object var4;
            try {
                jedis = (Jedis) this.jedisPool.getResource();
                String e = jedis.get(key);
                if (e != null) {
                    var4 = xstream.fromXML(e);
                    return var4;
                }

                var4 = null;
            } catch (Exception var8) {
                log.error("redis操作发生异常", var8);
                var4 = null;
                return var4;
            } finally {
                returnJedis(jedis);

            }

            return var4;
        }
    }

    public void setBySerialize(String key, Object val) {
        Jedis jedis = null;

        try {
            if (val == null) {
                return;
            }

            jedis = (Jedis) this.jedisPool.getResource();
            jedis.setex(key, this.defaultExpireTime, xstream.toXML(val));
            return;
        } catch (Exception var8) {
            log.error("redis操作发生异常", var8);
        } finally {
            returnJedis(jedis);

        }

    }

    public void setexBySerialize(String key, int seconds, Object val) {
        Jedis jedis = null;

        try {
            jedis = (Jedis) this.jedisPool.getResource();
            jedis.setex(key, seconds, val == null ? null : xstream.toXML(val));
            return;
        } catch (Exception var9) {
            log.error("redis操作发生异常", var9);
        } finally {
            returnJedis(jedis);

        }

    }

    public void zadd(String key, double score, String member) {
        if (this.use) {
            if (!StringUtils.isBlank(key)) {
                Jedis jedis = null;

                try {
                    jedis = (Jedis) this.jedisPool.getResource();
                    jedis.zadd(key, score, member);
                    return;
                } catch (Exception var10) {
                    log.error("redis操作发生异常", var10);
                } finally {
                    returnJedis(jedis);

                }

            }
        }
    }

    public void zrem(String key, String member) {
        if (this.use) {
            if (!StringUtils.isBlank(key)) {
                Jedis jedis = null;

                try {
                    jedis = (Jedis) this.jedisPool.getResource();
                    jedis.zrem(key, new String[]{member});
                    return;
                } catch (Exception var8) {
                    log.error("redis操作发生异常", var8);
                } finally {
                    returnJedis(jedis);

                }

            }
        }
    }

    public Double zscore(String key, String member) {
        Double result = null;
        if (!this.use) {
            return null;
        } else if (StringUtils.isBlank(key)) {
            return null;
        } else {
            Jedis jedis = null;

            Object var6;
            try {
                jedis = (Jedis) this.jedisPool.getResource();
                result = jedis.zscore(key, member);
                return result;
            } catch (Exception var10) {
                log.error("redis操作发生异常", var10);
                var6 = null;
            } finally {
                returnJedis(jedis);

            }

            return (Double) var6;
        }
    }

    public List<String> zquery(String key, long start, long end, boolean isAsc) {
        if (!this.use) {
            return null;
        } else if (StringUtils.isBlank(key)) {
            return null;
        } else {
            Jedis jedis = null;

            ArrayList list;
            try {
                jedis = (Jedis) this.jedisPool.getResource();
                Set e = null;
                if (isAsc) {
                    e = jedis.zrange(key, start, end);
                } else {
                    e = jedis.zrevrange(key, start, end);
                }

                if (null != e && e.size() > 0) {
                    list = new ArrayList();
                    Iterator i$ = e.iterator();

                    while (i$.hasNext()) {
                        String s = (String) i$.next();
                        list.add(s);
                    }

                    ArrayList i$1 = list;
                    return i$1;
                }

                list = null;
                return list;
            } catch (Exception var15) {
                log.error("redis操作发生异常", var15);
                list = null;
            } finally {
                returnJedis(jedis);

            }

            return list;
        }
    }

    public long zcard(String key) {
        if (!this.use) {
            return 0L;
        } else if (StringUtils.isBlank(key)) {
            return 0L;
        } else {
            Jedis jedis = null;

            long var4;
            try {
                jedis = (Jedis) this.jedisPool.getResource();
                long e = jedis.zcard(key).longValue();
                return e;
            } catch (Exception var9) {
                log.error("redis操作发生异常", var9);
                var4 = 0L;
            } finally {
                returnJedis(jedis);

            }

            return var4;
        }
    }

    public void setDefaultExpireTime(int defaultExpireTime) {
        this.defaultExpireTime = defaultExpireTime;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public String hget(String key, String field) {
        if (!this.use) {
            return null;
        } else if (!StringUtils.isBlank(key) && !StringUtils.isBlank(field)) {
            Jedis jedis = null;

            Object var5;
            try {
                jedis = (Jedis) this.jedisPool.getResource();
                String e = jedis.hget(key, field);
                return e;
            } catch (Exception var9) {
                log.error("redis操作发生异常", var9);
                var5 = null;
            } finally {
                returnJedis(jedis);

            }

            return (String) var5;
        } else {
            return null;
        }
    }

    public Long hdel(String key, String... fields) {
        if (!this.use) {
            return null;
        } else if (!StringUtils.isBlank(key) && fields != null && fields.length > 0) {
            Jedis jedis = null;

            Object var5;
            try {
                jedis = (Jedis) this.jedisPool.getResource();
                Long e = jedis.hdel(key, fields);
                return e;
            } catch (Exception var9) {
                log.error("redis操作发生异常", var9);
                var5 = null;
            } finally {
                returnJedis(jedis);

            }

            return (Long) var5;
        } else {
            return null;
        }
    }

    public Long hset(String key, String field, String value) {
        if (!this.use) {
            return Long.valueOf(0L);
        } else if (!StringUtils.isBlank(key) && !StringUtils.isBlank(field) && !StringUtils.isBlank(value)) {
            Jedis jedis = null;
            Long result = Long.valueOf(0L);

            Long var7;
            try {
                jedis = (Jedis) this.jedisPool.getResource();
                result = jedis.hset(key, field, value);
                jedis.expire(key, this.defaultExpireTime);
                return result;
            } catch (Exception var11) {
                log.error("redis操作发生异常", var11);
                var7 = Long.valueOf(0L);
            } finally {
                returnJedis(jedis);
            }

            return var7;
        } else {
            return Long.valueOf(0L);
        }
    }

    public void sadd(String key, String... member) {
        Jedis jedis = null;

        try {
            jedis = (Jedis) this.jedisPool.getResource();
            jedis.sadd(key, member);
            return;
        } catch (Exception var8) {
            log.error("redis操作发生异常", var8);
        } finally {
            returnJedis(jedis);
        }
    }

    private void returnJedis(Jedis jedis) {
        if (jedis != null) {
            this.jedisPool.returnResource(jedis);
        }
    }

    public String mset(String... keyvalues) {
        Jedis jedis = getJedis();
        try {
            return jedis.mset(keyvalues);
        } catch (Exception e) {
            log.error("redis操作发生异常", e);
            return null;
        } finally {
            returnJedis(jedis);
        }
    }

    public List<String> mget(String... keys) {
        Jedis jedis = getJedis();
        try {
            return jedis.mget(keys);
        } catch (Exception e) {
            log.error("redis操作发生异常", e);
            return null;
        } finally {
            returnJedis(jedis);
        }
    }

    public Jedis getJedis() {
        return jedisPool.getResource();
    }


}
