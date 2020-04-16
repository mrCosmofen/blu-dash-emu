import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/blu-form">
      <Translate contentKey="global.menu.entities.bluForm" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/blu-field">
      <Translate contentKey="global.menu.entities.bluField" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/blu-field-value">
      <Translate contentKey="global.menu.entities.bluFieldValue" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/data-set">
      <Translate contentKey="global.menu.entities.dataSet" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/query">
      <Translate contentKey="global.menu.entities.query" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/data-model">
      <Translate contentKey="global.menu.entities.dataModel" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/record">
      <Translate contentKey="global.menu.entities.record" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/query-data">
      <Translate contentKey="global.menu.entities.queryData" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
