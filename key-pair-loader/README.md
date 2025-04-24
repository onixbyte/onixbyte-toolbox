# KeyLoader

KeyLoader provides utility methods to load keys from pem-formatted key texts.

## ECDSA-based algorithm

### Generate key pair

#### Generate private key

Generate a private key by `genpkey` command provided by OpenSSL:

```shell
openssl genpkey -algorithm EC -pkeyopt ec_paramgen_curve:P-256 -out ec_private_key.pem
```

The output of this command is a file called `ec_private_key.pem` and its content looks like the
following:

```text
-----BEGIN PRIVATE KEY-----
MIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgs79JlARgXEf6EDV7
+PHQCTHEMtqIoHOy1GZ1+ynQJ6yhRANCAARkA7GRY2i4gg8qx0XViAXUP9cPw9pn
Jg1wfrQ41FaMyqVBejNYxvaLtamErF/ySimnjafMJ+VZCh34lBj6Ez8R
-----END PRIVATE KEY-----
```

#### Generate public key by private key

Export public key from private key with `ec` command provided by OpenSSL:

```shell
openssl ec -in ec_private_key.pem -pubout -out ec_public_key.pem
```

The output of this command is a file called `ec_public_key.pem` and its content looks like the
following:

```text
-----BEGIN PUBLIC KEY-----
MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEZAOxkWNouIIPKsdF1YgF1D/XD8Pa
ZyYNcH60ONRWjMqlQXozWMb2i7WphKxf8kopp42nzCflWQod+JQY+hM/EQ==
-----END PUBLIC KEY-----
```

## RSA-based algorithm

### Generate key pair

#### Generate private key

Generate a private key by `genpkey` command provided by OpenSSL:

```shell
openssl genpkey -algorithm RSA -out rsa_private_key.pem -pkeyopt rsa_keygen_bits:2048
```

The output of this command is a file called `rsa_private_key.pem` and its content looks like the
following:

```text
-----BEGIN PRIVATE KEY-----
MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQD4VIFYJFMAs15j
J3V3IicHd7sI2TIFqTZME40zlOlVAlPKLZmTQvZFLNgaUAAsvPi5i1DR2ywwK6Al
BfnwVnzvmDXC5mKHOz4oxOQVA6Nlp2yVaQMzidmfYNSkMtcv/4HRPsatc7K/M5l6
pCP20DVRjkikBdIy8e9w+x6BrIFp5Q8PZc/X2BGNAUMMYACdeYH5R/A0CxqkND13
esc4gkynMOrvZrZGHCz51usfSCqyDWWwsN+GG6LYWia4GkNlS0erQnP8gS93dfjl
e96BIfy3z7Iv+kUrf5ikNW2P8jMxLAv6LO+dcUAu9k477wIAF7Iq5KMuH/otsDOu
+h+2qXmBAgMBAAECggEAdRqcmC0g+y6arxV3fkObthjPGYAa57KBCWUa7B0n30+m
pavVRS2Jpttb2SSqwG4ouI6rARti/iBEd9EWqTCP4AieKZetFOpqCJ24lPRPRGus
d9S6jr5N4qut+vSCp37NABijZj4uJ540nTH0R7qtuhTnynl4Q0/1wwiYvTvVF1Lg
dn+I/8aRbshwDhdAOWOUe6GL7/eaCYgN8/UmlKIpp8tg0w2iWxbaFiR7gZiM41LA
M6SXXfcCas+ZVXsGbzQ3SNiVurCGuuRNcCScXS3/WoEDIb3cNtp49iOmQS+nmEoo
wh4uiEd+0+BrzxngS4o5+mKnHJnwgY0+veGVYLMR5QKBgQD9WKQmevMDU5c+NPq9
8jaR457Fuxq1gwzeFNJdWfOc/K2LEWh+nFNFCb++EboEj6FdxWaWNMxbrmJps5gs
EoBUYy/Tl7UycDqDfiYLmDdTsf2pVjjh9jaIADiLcJ8S6wwJMZKub7Tp8UVkenAl
535MqShLUC11Y7VxLb3Tsll4XwKBgQD67mm6iCmshr/eszPfNE3ylZ+PiNa7nat7
N7lQzBIiRJflT1kmVidC5gE+jASqH728ChkZZKxbHsjxpmWdAhLOITdXoTB4sDsd
wtV1lxkXxK9FnrpFvO3y1wZ/QsD3Z2KXxHYZqawkUETO9F3nqAXW0b2GDar5Qiyo
J3Tx/43aHwKBgDC0NMJtCoDONhowZy/S+6iqQKC0qprQec3L5PErVMkOTnKYwyTr
+pogGKt6ju9HiXcUdvdTaSIK8UJu00dNuzv94XjlBmGO78DNpJTAC4rcge5m9AKE
qdEVcclkukARzbuKuy8rrHT4/CUn4J141m/4aRWpcUPLCluato6XD9ozAoGBANvf
JhOFFgcPd3YazfvpZ9eE1XA+tfFlYYmxNRcgCU+vjO0oDvSxjutmgHae18N91pG6
w21lskSRf/+GDwl5dKLbphOJsOA/gz07qDDGOf2CoRW+1Hcg6drcINxH0K+4DkLv
qZApBSY4k2JH6zR+HMeztn6M4WBRZLHfCPC3PUN/AoGAA3AoHbLTZvqMIKSDkP4Y
U/tTsSFDY4aYo7LG/jk8af3oPU3KyGh4ZFBd6aMmXbS8f8FjvmrM+/e+y9OOGAlq
iOl0hYrs5cJSMLW6i4KnJYuYbMkgmk3bN2t9apu64xKR94gbPrI6AGnPZp+iIzp0
hXKe4HcuhQ3G0a2hjayiQ84=
-----END PRIVATE KEY-----
```

#### Generate public key by private key

Export public key from private key by OpenSSL:

```shell
openssl pkey -in rsa_private_key.pem -pubout -out rsa_public_key.pem
```

The output of this command is a file called `rsa_public_key.pem` and its content looks like the
following:

```text
-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA+FSBWCRTALNeYyd1dyIn
B3e7CNkyBak2TBONM5TpVQJTyi2Zk0L2RSzYGlAALLz4uYtQ0dssMCugJQX58FZ8
75g1wuZihzs+KMTkFQOjZadslWkDM4nZn2DUpDLXL/+B0T7GrXOyvzOZeqQj9tA1
UY5IpAXSMvHvcPsegayBaeUPD2XP19gRjQFDDGAAnXmB+UfwNAsapDQ9d3rHOIJM
pzDq72a2Rhws+dbrH0gqsg1lsLDfhhui2FomuBpDZUtHq0Jz/IEvd3X45XvegSH8
t8+yL/pFK3+YpDVtj/IzMSwL+izvnXFALvZOO+8CABeyKuSjLh/6LbAzrvoftql5
gQIDAQAB
-----END PUBLIC KEY-----
```