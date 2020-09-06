package az.kapitalbank.websocketmock.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class WebSocketMessage {

    private ProcessStatus status;
    private String user;
    private String businessKey;

}
