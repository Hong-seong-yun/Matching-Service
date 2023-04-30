package healthmatching.matchingservice.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    CUSTOMER("ROLE_CUSTOMER"),
    TRAINER("ROLE_TRAINER");
    private final String value;
}
