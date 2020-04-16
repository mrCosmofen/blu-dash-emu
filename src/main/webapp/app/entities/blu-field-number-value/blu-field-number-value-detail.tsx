import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './blu-field-number-value.reducer';
import { IBluFieldNumberValue } from 'app/shared/model/blu-field-number-value.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBluFieldNumberValueDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BluFieldNumberValueDetail = (props: IBluFieldNumberValueDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { bluFieldNumberValueEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="emulatorApp.bluFieldNumberValue.detail.title">BluFieldNumberValue</Translate> [
          <b>{bluFieldNumberValueEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="fieldValue">
              <Translate contentKey="emulatorApp.bluFieldNumberValue.fieldValue">Field Value</Translate>
            </span>
          </dt>
          <dd>{bluFieldNumberValueEntity.fieldValue}</dd>
          <dt>
            <Translate contentKey="emulatorApp.bluFieldNumberValue.bluField">Blu Field</Translate>
          </dt>
          <dd>{bluFieldNumberValueEntity.bluField ? bluFieldNumberValueEntity.bluField.id : ''}</dd>
          <dt>
            <Translate contentKey="emulatorApp.bluFieldNumberValue.bluFormData">Blu Form Data</Translate>
          </dt>
          <dd>{bluFieldNumberValueEntity.bluFormData ? bluFieldNumberValueEntity.bluFormData.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/blu-field-number-value" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/blu-field-number-value/${bluFieldNumberValueEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ bluFieldNumberValue }: IRootState) => ({
  bluFieldNumberValueEntity: bluFieldNumberValue.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BluFieldNumberValueDetail);
