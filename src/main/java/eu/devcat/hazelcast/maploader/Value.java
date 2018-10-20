package eu.devcat.hazelcast.maploader;

import lombok.*;

import java.io.Serializable;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@ToString
@EqualsAndHashCode
final class Value implements Serializable {
    private String value;
}
