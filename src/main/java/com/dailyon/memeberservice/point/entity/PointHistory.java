package com.dailyon.memeberservice.point.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class PointHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long member_id;

    @Column(nullable = false)
    private boolean status;

    @Column(nullable = false)
    private String source;

    private String utilize;

    @Column(nullable = false)
    private String createdAt;
}
