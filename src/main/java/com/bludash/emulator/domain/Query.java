package com.bludash.emulator.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * A Query.
 */
@Entity
@Table(name = "query")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Query implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "key")
    private UUID key;

    @Column(name = "label")
    private String label;

    @OneToMany(mappedBy = "query")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Record> records = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getKey() {
        return key;
    }

    public Query key(UUID key) {
        this.key = key;
        return this;
    }

    public void setKey(UUID key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public Query label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Set<Record> getRecords() {
        return records;
    }

    public Query records(Set<Record> records) {
        this.records = records;
        return this;
    }

    public Query addRecord(Record record) {
        this.records.add(record);
        record.setQuery(this);
        return this;
    }

    public Query removeRecord(Record record) {
        this.records.remove(record);
        record.setQuery(null);
        return this;
    }

    public void setRecords(Set<Record> records) {
        this.records = records;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Query)) {
            return false;
        }
        return id != null && id.equals(((Query) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Query{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", label='" + getLabel() + "'" +
            "}";
    }
}
