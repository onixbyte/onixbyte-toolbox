# Module `guid`

## Introduction

Module `guid` serves as a guid creator for other `JDevKit` modules. You can also use this module as a guid creator standards.

We have already implemented `SnowflakeGuidCreator`, you can also implement a custom guid creations by implementing `com.onixbyte.identitygenerator.IdentityGenerator`.

## Example usage

### A UUID creator

```java
GuidCreator<UUID> uuidCreator = (GuidCreator<UUID>) UUID::randomUUID;
```

### A custom guid creator

Assume that you need serial guid creator.

```java
@Component
public class CustomGuidCreator implementes GuidCreator<String> {
    
    public final RedisTemplate<String, Long> serialRedisTemplate;
    
    @Autowired
    public CustomGuidCreator(RedisTemplate<String, Long> serialRedisTemplate) {
        this.serialRedisTemplate = serialRedisTemplate;
    }
    
    @Override public String nextId() {
        return "SOME_PREFIX" + serialRedisTemplate.opsForValue().get("some_serial_key");
    }
    
}
```

