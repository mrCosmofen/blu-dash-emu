import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './blu-field-currency-value.reducer';
import { IBluFieldCurrencyValue } from 'app/shared/model/blu-field-currency-value.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBluFieldCurrencyValueDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BluFieldCurrencyValueDetail = (props: IBluFieldCurrencyValueDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { bluFieldCurrencyValueEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="emulatorApp.bluFieldCurrencyValue.detail.title">BluFieldCurrencyValue</Translate> [
          <b>{bluFieldCurrencyValueEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="value">
              <Translate contentKey="emulatorApp.bluFieldCurrencyValue.value">Value</Translate>
            </span>
          </dt>
          <dd>{bluFieldCurrencyValueEntity.value}</dd>
          <dt>
            <span id="currency">
              <Translate contentKey="emulatorApp.bluFieldCurrencyValue.currency">Currency</Translate>
            </span>
          </dt>
          <dd>{bluFieldCurrencyValueEntity.currency}</dd>
          <dt>
            <Translate contentKey="emulatorApp.bluFieldCurrencyValue.bluField">Blu Field</Translate>
          </dt>
          <dd>{bluFieldCurrencyValueEntity.bluField ? bluFieldCurrencyValueEntity.bluField.id : ''}</dd>
          <dt>
            <Translate contentKey="emulatorApp.bluFieldCurrencyValue.bluFormData">Blu Form Data</Translate>
          </dt>
          <dd>{bluFieldCurrencyValueEntity.bluFormData ? bluFieldCurrencyValueEntity.bluFormData.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/blu-field-currency-value" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/blu-field-currency-value/${bluFieldCurrencyValueEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ bluFieldCurrencyValue }: IRootState) => ({
  bluFieldCurrencyValueEntity: bluFieldCurrencyValue.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BluFieldCurrencyValueDetail);
