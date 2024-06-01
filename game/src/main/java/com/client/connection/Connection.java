package com.client.connection;

public enum Connection {
    /** The economy (main) world. */
    WORLD_1("Live", "192.41.102.184", "43594"),

    /** The management world - used for private testing by the management team. */
    WORLD_2("Beta", "192.248.211.249", "43594"),

    /** The development world - used by developers. */
    DEVELOPMENT("Local", "localhost", "43594")
    ;

    /** The connection name. */
    public final String name;

    /** The connection IP address. */
    public final String address;

    /** The connection IP address. */
    public final String port;

    /** Constructs a new <code>Connection</code>. */
    Connection(String name, String address, String port) {
        this.name = name;
        this.address = address;
        this.port = port;
    }
}
