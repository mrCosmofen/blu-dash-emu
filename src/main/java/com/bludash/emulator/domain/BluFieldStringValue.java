package com.bludash.emulator.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A BluFieldStringValue.
 */
@Entity
@Table(name = "blu_field_string_value")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BluFieldStringValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "value")
    private String value;

    @OneToOne
    @JoinColumn(unique = true)
    private BluField bluField;

    @ManyToOne
    @JsonIgnoreProperties("bluFieldStringValues")
    private BluFormData bluFormData;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public BluFieldStringValue value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public BluField getBluField() {
        return bluField;
    }

    public BluFieldStringValue bluField(BluField bluField) {
        this.bluField = bluField;
        return this;
    }

    public void setBluField(BluField bluField) {
        this.bluField = bluField;
    }

    public BluFormData getBluFormData() {
        return bluFormData;
    }

    public BluFieldStringValue bluFormData(BluFormData bluFormData) {
        this.bluFormData = bluFormData;
        return this;
    }

    public void setBluFormData(BluFormData bluFormData) {
        this.bluFormData = bluFormData;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BluFieldStringValue)) {
            return false;
        }
        return id != null && id.equals(((BluFieldStringValue) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BluFieldStringValue{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
