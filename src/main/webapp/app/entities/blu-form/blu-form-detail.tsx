import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './blu-form.reducer';
import { IBluForm } from 'app/shared/model/blu-form.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBluFormDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BluFormDetail = (props: IBluFormDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { bluFormEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="emulatorApp.bluForm.detail.title">BluForm</Translate> [<b>{bluFormEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="key">
              <Translate contentKey="emulatorApp.bluForm.key">Key</Translate>
            </span>
          </dt>
          <dd>{bluFormEntity.key}</dd>
          <dt>
            <span id="label">
              <Translate contentKey="emulatorApp.bluForm.label">Label</Translate>
            </span>
          </dt>
          <dd>{bluFormEntity.label}</dd>
          <dt>
            <span id="category">
              <Translate contentKey="emulatorApp.bluForm.category">Category</Translate>
            </span>
          </dt>
          <dd>{bluFormEntity.category}</dd>
          <dt>
            <span id="productKey">
              <Translate contentKey="emulatorApp.bluForm.productKey">Product Key</Translate>
            </span>
          </dt>
          <dd>{bluFormEntity.productKey}</dd>
          <dt>
            <span id="pollingInterval">
              <Translate contentKey="emulatorApp.bluForm.pollingInterval">Polling Interval</Translate>
            </span>
          </dt>
          <dd>{bluFormEntity.pollingInterval}</dd>
          <dt>
            <span id="modified">
              <Translate contentKey="emulatorApp.bluForm.modified">Modified</Translate>
            </span>
          </dt>
          <dd>{bluFormEntity.modified}</dd>
        </dl>
        <Button tag={Link} to="/blu-form" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/blu-form/${bluFormEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ bluForm }: IRootState) => ({
  bluFormEntity: bluForm.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BluFormDetail);
