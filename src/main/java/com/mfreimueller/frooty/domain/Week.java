package com.mfreimueller.frooty.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Set;

@Entity
@Table(name = "week_tbl")
public class Week {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Group group;

    @Column
    @Temporal(TemporalType.DATE)
    private LocalDate startDate;

    @OneToMany(mappedBy = "week")
    private Set<HistoryEntry> historyEntries = Set.of();

    public Week() {

    }

    public Week(Group group, LocalDate startDate, Set<HistoryEntry> historyEntries) {
        this.group = group;
        this.startDate = startDate;
        this.historyEntries = historyEntries;
    }

    public Week(Integer id, Group group, LocalDate startDate, Set<HistoryEntry> historyEntries) {
        this.id = id;
        this.group = group;
        this.startDate = startDate;
        this.historyEntries = historyEntries;
    }

    public Integer getId() {
        return id;
    }

    public Group getGroup() {
        return group;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Set<HistoryEntry> getHistoryEntries() {
        return historyEntries;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setHistoryEntries(Set<HistoryEntry> historyEntries) {
        this.historyEntries = historyEntries;
    }
}
