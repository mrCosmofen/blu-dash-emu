package com.bludash.emulator.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A BluFieldNumberValue.
 */
@Entity
@Table(name = "blu_field_number_value")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BluFieldNumberValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "field_value")
    private Long fieldValue;

    @OneToOne
    @JoinColumn(unique = true)
    private BluField bluField;

    @ManyToOne
    @JsonIgnoreProperties("bluFieldNumberValues")
    private BluFormData bluFormData;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFieldValue() {
        return fieldValue;
    }

    public BluFieldNumberValue fieldValue(Long fieldValue) {
        this.fieldValue = fieldValue;
        return this;
    }

    public void setFieldValue(Long fieldValue) {
        this.fieldValue = fieldValue;
    }

    public BluField getBluField() {
        return bluField;
    }

    public BluFieldNumberValue bluField(BluField bluField) {
        this.bluField = bluField;
        return this;
    }

    public void setBluField(BluField bluField) {
        this.bluField = bluField;
    }

    public BluFormData getBluFormData() {
        return bluFormData;
    }

    public BluFieldNumberValue bluFormData(BluFormData bluFormData) {
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
        if (!(o instanceof BluFieldNumberValue)) {
            return false;
        }
        return id != null && id.equals(((BluFieldNumberValue) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BluFieldNumberValue{" +
            "id=" + getId() +
            ", fieldValue=" + getFieldValue() +
            "}";
    }
}
