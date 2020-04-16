import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './blu-field.reducer';
import { IBluField } from 'app/shared/model/blu-field.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBluFieldDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BluFieldDetail = (props: IBluFieldDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { bluFieldEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="emulatorApp.bluField.detail.title">BluField</Translate> [<b>{bluFieldEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="key">
              <Translate contentKey="emulatorApp.bluField.key">Key</Translate>
            </span>
          </dt>
          <dd>{bluFieldEntity.key}</dd>
          <dt>
            <span id="label">
              <Translate contentKey="emulatorApp.bluField.label">Label</Translate>
            </span>
          </dt>
          <dd>{bluFieldEntity.label}</dd>
          <dt>
            <span id="dataType">
              <Translate contentKey="emulatorApp.bluField.dataType">Data Type</Translate>
            </span>
          </dt>
          <dd>{bluFieldEntity.dataType}</dd>
          <dt>
            <span id="dataFormat">
              <Translate contentKey="emulatorApp.bluField.dataFormat">Data Format</Translate>
            </span>
          </dt>
          <dd>{bluFieldEntity.dataFormat}</dd>
          <dt>
            <span id="fieldValues">
              <Translate contentKey="emulatorApp.bluField.fieldValues">Field Values</Translate>
            </span>
          </dt>
          <dd>{bluFieldEntity.fieldValues}</dd>
          <dt>
            <Translate contentKey="emulatorApp.bluField.bluForm">Blu Form</Translate>
          </dt>
          <dd>{bluFieldEntity.bluForm ? bluFieldEntity.bluForm.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/blu-field" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/blu-field/${bluFieldEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ bluField }: IRootState) => ({
  bluFieldEntity: bluField.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BluFieldDetail);
