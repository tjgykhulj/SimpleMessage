package com.arnold.msg.metadata.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.clients.CommonClientConfigs;

import java.util.Properties;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KafkaClusterConfig {

    @JsonProperty("bootstrap.servers")
    private String bootstrapServers;

    @JsonProperty("security.protocol")
    private String securityProtocol;

    // TODO need ssl or sasl related configuration
    public Properties toProperties() {
        Properties props = new Properties();
        if (bootstrapServers != null) {
            props.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        }
        if (securityProtocol != null) {
            props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, securityProtocol);
        }
        return props;
    }

}
