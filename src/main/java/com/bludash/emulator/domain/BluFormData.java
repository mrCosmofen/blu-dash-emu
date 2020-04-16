package com.bludash.emulator.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A BluFormData.
 */
@Entity
@Table(name = "blu_form_data")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BluFormData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "retrieved")
    private Long retrieved;

    @OneToMany(mappedBy = "bluFormData")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BluFieldStringValue> bluFieldStringValues = new HashSet<>();

    @OneToMany(mappedBy = "bluFormData")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BluFieldCurrencyValue> bluFieldCurrencyValues = new HashSet<>();

    @OneToMany(mappedBy = "bluFormData")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BluFieldNumberValue> bluFieldNumberValues = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("bluFormData")
    private BluForm bluForm;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRetrieved() {
        return retrieved;
    }

    public BluFormData retrieved(Long retrieved) {
        this.retrieved = retrieved;
        return this;
    }

    public void setRetrieved(Long retrieved) {
        this.retrieved = retrieved;
    }

    public Set<BluFieldStringValue> getBluFieldStringValues() {
        return bluFieldStringValues;
    }

    public BluFormData bluFieldStringValues(Set<BluFieldStringValue> bluFieldStringValues) {
        this.bluFieldStringValues = bluFieldStringValues;
        return this;
    }

    public BluFormData addBluFieldStringValue(BluFieldStringValue bluFieldStringValue) {
        this.bluFieldStringValues.add(bluFieldStringValue);
        bluFieldStringValue.setBluFormData(this);
        return this;
    }

    public BluFormData removeBluFieldStringValue(BluFieldStringValue bluFieldStringValue) {
        this.bluFieldStringValues.remove(bluFieldStringValue);
        bluFieldStringValue.setBluFormData(null);
        return this;
    }

    public void setBluFieldStringValues(Set<BluFieldStringValue> bluFieldStringValues) {
        this.bluFieldStringValues = bluFieldStringValues;
    }

    public Set<BluFieldCurrencyValue> getBluFieldCurrencyValues() {
        return bluFieldCurrencyValues;
    }

    public BluFormData bluFieldCurrencyValues(Set<BluFieldCurrencyValue> bluFieldCurrencyValues) {
        this.bluFieldCurrencyValues = bluFieldCurrencyValues;
        return this;
    }

    public BluFormData addBluFieldCurrencyValue(BluFieldCurrencyValue bluFieldCurrencyValue) {
        this.bluFieldCurrencyValues.add(bluFieldCurrencyValue);
        bluFieldCurrencyValue.setBluFormData(this);
        return this;
    }

    public BluFormData removeBluFieldCurrencyValue(BluFieldCurrencyValue bluFieldCurrencyValue) {
        this.bluFieldCurrencyValues.remove(bluFieldCurrencyValue);
        bluFieldCurrencyValue.setBluFormData(null);
        return this;
    }

    public void setBluFieldCurrencyValues(Set<BluFieldCurrencyValue> bluFieldCurrencyValues) {
        this.bluFieldCurrencyValues = bluFieldCurrencyValues;
    }

    public Set<BluFieldNumberValue> getBluFieldNumberValues() {
        return bluFieldNumberValues;
    }

    public BluFormData bluFieldNumberValues(Set<BluFieldNumberValue> bluFieldNumberValues) {
        this.bluFieldNumberValues = bluFieldNumberValues;
        return this;
    }

    public BluFormData addBluFieldNumberValue(BluFieldNumberValue bluFieldNumberValue) {
        this.bluFieldNumberValues.add(bluFieldNumberValue);
        bluFieldNumberValue.setBluFormData(this);
        return this;
    }

    public BluFormData removeBluFieldNumberValue(BluFieldNumberValue bluFieldNumberValue) {
        this.bluFieldNumberValues.remove(bluFieldNumberValue);
        bluFieldNumberValue.setBluFormData(null);
        return this;
    }

    public void setBluFieldNumberValues(Set<BluFieldNumberValue> bluFieldNumberValues) {
        this.bluFieldNumberValues = bluFieldNumberValues;
    }

    public BluForm getBluForm() {
        return bluForm;
    }

    public BluFormData bluForm(BluForm bluForm) {
        this.bluForm = bluForm;
        return this;
    }

    public void setBluForm(BluForm bluForm) {
        this.bluForm = bluForm;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BluFormData)) {
            return false;
        }
        return id != null && id.equals(((BluFormData) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BluFormData{" +
            "id=" + getId() +
            ", retrieved=" + getRetrieved() +
            "}";
    }
}
