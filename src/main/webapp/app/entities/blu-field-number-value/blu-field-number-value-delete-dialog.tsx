import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IBluFieldNumberValue } from 'app/shared/model/blu-field-number-value.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './blu-field-number-value.reducer';

export interface IBluFieldNumberValueDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BluFieldNumberValueDeleteDialog = (props: IBluFieldNumberValueDeleteDialogProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const handleClose = () => {
    props.history.push('/blu-field-number-value');
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const confirmDelete = () => {
    props.deleteEntity(props.bluFieldNumberValueEntity.id);
  };

  const { bluFieldNumberValueEntity } = props;
  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose}>
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="emulatorApp.bluFieldNumberValue.delete.question">
        <Translate contentKey="emulatorApp.bluFieldNumberValue.delete.question" interpolate={{ id: bluFieldNumberValueEntity.id }}>
          Are you sure you want to delete this BluFieldNumberValue?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-bluFieldNumberValue" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

const mapStateToProps = ({ bluFieldNumberValue }: IRootState) => ({
  bluFieldNumberValueEntity: bluFieldNumberValue.entity,
  updateSuccess: bluFieldNumberValue.updateSuccess
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BluFieldNumberValueDeleteDialog);
