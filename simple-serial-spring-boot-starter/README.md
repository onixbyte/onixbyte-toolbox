# Simple Serial

> Thanks to [@siujamo](https://github.com/siujamo)'s donation.

Simple Serial reuses the configuration of Redis connections to provide am easy-to-use serial
service.

## Configuration

Simple Serial reused the redis configuration of Spring Boot to provide redis support for the
service.

Besides, **Simple Serial** provides a configuration property `onixbyte.serial.start-serial` to
specify the start value of a serial, and default to `0`.