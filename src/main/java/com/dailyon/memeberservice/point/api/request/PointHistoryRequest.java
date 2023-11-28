package com.dailyon.memeberservice.point.api.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class PointHistoryRequest {

    private long memberId;

    private boolean status;

    private PointSource source;

    private long amount;

    private String utilize;


}
