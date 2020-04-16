package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Record.
 */
@Entity
@Table(name = "record")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Record implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "record_id")
    private Integer recordId;

    @OneToMany(mappedBy = "record")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<QueryData> queryData = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("records")
    private Query query;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public Record recordId(Integer recordId) {
        this.recordId = recordId;
        return this;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Set<QueryData> getQueryData() {
        return queryData;
    }

    public Record queryData(Set<QueryData> queryData) {
        this.queryData = queryData;
        return this;
    }

    public Record addQueryData(QueryData queryData) {
        this.queryData.add(queryData);
        queryData.setRecord(this);
        return this;
    }

    public Record removeQueryData(QueryData queryData) {
        this.queryData.remove(queryData);
        queryData.setRecord(null);
        return this;
    }

    public void setQueryData(Set<QueryData> queryData) {
        this.queryData = queryData;
    }

    public Query getQuery() {
        return query;
    }

    public Record query(Query query) {
        this.query = query;
        return this;
    }

    public void setQuery(Query query) {
        this.query = query;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Record)) {
            return false;
        }
        return id != null && id.equals(((Record) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Record{" +
            "id=" + getId() +
            ", recordId=" + getRecordId() +
            "}";
    }
}
