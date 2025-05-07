package com.mfreimueller.frooty.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "plan_invite_tbl")
public class PlanInvite {
    public enum Status {
        PENDING,
        ACCEPTED,
        DECLINED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Plan plan;

    @ManyToOne
    private User invitedUser;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private Status status = Status.PENDING;

    @Column
    @Temporal(TemporalType.DATE)
    private LocalDate sentAt;

    @Column
    @Temporal(TemporalType.DATE)
    private LocalDate updatedAt;

    public PlanInvite() {}

    public PlanInvite(Plan plan, User invitedUser, LocalDate sentAt) {
        this.plan = plan;
        this.invitedUser = invitedUser;
        this.sentAt = sentAt;
    }

    public Integer getId() {
        return id;
    }

    public Plan getPlan() {
        return plan;
    }

    public User getInvitedUser() {
        return invitedUser;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        if (this.status != Status.PENDING) {
            throw new IllegalStateException("Status of already finalized invitation cannot be changed!");
        }

        this.status = status;
    }

    public LocalDate getSentAt() {
        return sentAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }
}
