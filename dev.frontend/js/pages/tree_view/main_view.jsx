'use strict';

const React = require('react');
const {Component} = React;

const TreeView = require('./tree_view.jsx');


module.exports = class MainView extends Component {

  render() {

    return (
      <div className="tree-page">
        <div className="container site-container">
          <div className="panel panel-default">
            <div className="panel-body controls-container">
              <div>
                <input type="checkbox" name="checkboxG4" id="checkboxG4" className="controls-checkbox js-toggle-marriage" />
                <label for="checkboxG4" className="controls-label">Hiện vợ/chồng</label>
              </div>

              <div className="controls-depth-container">
                <input placeholder="Số đời con cháu" className="form-control js-tree-depth-input" name="" type="text" value=""/>
                <button className="btn btn-default js-update-tree-depth">Cập nhật</button>
              </div>
            </div>
          </div>
        </div>

        { this.props.initializing ?
          <div className="tree-container">
            <i className="fa fa-spinner fa-spin fa-5x fa-fw"></i>
          </div>
          :
          <TreeView tree={this.context.tree} />
        }


          <div id="js-user-modal-container"></div>
      </div>
    );
  }
};
