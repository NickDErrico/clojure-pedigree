'use strict';

const React = require('react');
const {Component} = React;
const _ = require('lodash');
const $ = require('jquery');

const personUtil = require('person_util.js');


module.exports = class Col2View extends Component {

  /**
   * Handle status select change
   */
  handleStatusChange() {
    const status = this.statusesSelect.value.trim();
    const tree = this.props.tree;
    tree.set(['person', 'aliveStatus'], status);
  }


  /**
     * Render <select> for statuses
     * @return {object}
   */
  renderStatuses() {
    const statuses = personUtil.statusesList;

    return (
      <select name="status" className="form-control"
              onChange={this.handleStatusChange.bind(this)}
              defaultValue={this.props.person.aliveStatus}
              ref={(ref) => this.statusesSelect = ref}>
        {_.map(statuses, (v, k) => <option key={k} value={k}>{v}</option>)}
      </select>
    );
  }

  /**
   * Render <select> for genders
   * @return {object}
   */
  renderGenders() {
    const genders = personUtil.gendersList;

    return (
      <select className="form-control"
              defaultValue={this.props.person.gender}
              name="gender">
        {_.map(genders, (v, k) => <option key={k} value={k}>{v}</option>)}
      </select>
    );
  }


  /**
   * Init bootstrap date picker for the birth date and death date
   */
  initDatePicker() {
    // Find components
    const birthDateInput = $(this.birthDateInput);
    const deathDateInput = $(this.deathDateInput);

    birthDateInput.datepicker({
      language: 'vi'
    });
    deathDateInput.datepicker({
      language: 'vi'
    });
  }


  /**
   * Init bootstrap date picker for the birth date and death date
   */
  initSummaryEditor() {
    // Find components
    const historyEditor = $(this.summaryInput);

    historyEditor.markdown({
      iconlibrary: 'fa',
      resize: 'vertical',
      height: 300
    });
  }


  componentDidMount() {
    this.initDatePicker();
    this.initSummaryEditor();
  }


  /**
   * @return {object}
   */
  render() {
    const statusesView = this.renderStatuses();
    const gendersView = this.renderGenders();

    return (
      <div className="editperson-col-2">
        <input name="personid" type="hidden" value={this.props.person.id} />

        <div className="profile-container">
          <div className="profile-header">
            Hồ sơ
          </div>

          <div className="profile-body">
            <div className="profile-body-row">
              <div className="profile-body-left">
                Tên
              </div>
              <div className="profile-body-right">
                <input className="form-control" name="name" type="text"
                       defaultValue={this.props.person.fullName} />
              </div>
            </div>

            <div className="profile-body-row">
              <div className="profile-body-left">
                Ngày sinh
              </div>
              <div className="profile-body-right">
                <input className="form-control"
                       ref={(ref) => this.birthDateInput = ref}
                       defaultValue={this.props.person.birthDate}
                       name="birthdate" type="text"/>
              </div>
            </div>

            <div className="profile-body-row">
              <div className="profile-body-left">
                Tình trạng
              </div>
              <div className="profile-body-right">
                {statusesView}
              </div>
            </div>

            <div className={(this.props.person.aliveStatus === 'dead' ? '' : 'hidden ') + 'profile-body-row'}>
              <div className="profile-body-left">
                Ngày mất
              </div>
              <div className="profile-body-right">
                <input className="form-control"
                       ref={(ref) => this.deathDateInput = ref}
                       defaultValue={this.props.person.deathDate}
                       name="deathdate" type="text" />
              </div>
            </div>

            <div className="profile-body-row">
              <div className="profile-body-left">
                Giới tính
              </div>
              <div className="profile-body-right">
                {gendersView}
              </div>
            </div>

            <div className="profile-body-row">
              <div className="profile-body-left">
                Điện thoại
              </div>
              <div className="profile-body-right">
                <input className="form-control" name="phone" type="text" defaultValue={this.props.person.phoneNo}/>
              </div>
            </div>

            <div className="profile-body-row">
              <div className="profile-body-left">
                Địa chỉ
              </div>
              <div className="profile-body-right">
                <textarea className="form-control" cols="30" id=""
                          defaultValue={this.props.person.address}
                          name="address" rows="3"></textarea>
              </div>
            </div>

          </div>
        </div>

        <div className="history-container">
          <div className="history-header">
            Tiểu sử
          </div>
          <div className="history-body">
            <textarea name="history" defaultValue={this.props.person.summary}
                      ref={(ref) => this.summaryInput = ref}
                      className="form-control"></textarea>
          </div>
        </div>
      </div>
    );
  }
};
