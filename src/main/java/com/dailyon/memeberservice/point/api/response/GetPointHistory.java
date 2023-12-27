package com.dailyon.memeberservice.point.api.response;

import com.dailyon.memeberservice.point.api.request.PointSource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class GetPointHistory {

    private long amount;

    private boolean status;

    private PointSource source;

    private String utilize;

    private LocalDateTime createdAt;

}
