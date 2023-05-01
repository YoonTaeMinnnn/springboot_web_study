package hello.advanced.trace;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TraceStatus {

    private TraceId traceId; //id, level 보유
    private Long startTimeMs; //로그 시작 시간
    private String message; //시작 시, 사용 메시지

}
