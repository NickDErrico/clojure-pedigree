'use strict';

const React = require('react');
const { Component } = React;
const { TransitionMotion, spring } = require('react-motion');
const _ = require('lodash');
const PropTypes = require('baobab-react/prop-types');

const Marriage = require('./marriage.jsx');


class NodesGroup extends Component {

  constructor(props) {
    super(props);
    this.state = {
      oldNodesList: null
    };
  }


  render() {
    // get nodesList from parent
    const { nodesList } = this.props;

    // calculate position
    const nodesConfig = nodesList.map(node => ({
      key: node.info.id.toString(),
      style: {x: spring(node.x), y: spring(node.y)},
      data: node
    }));

    // default starting position
    const defaultNodesConfig = nodesList.map(node => {
      const style = node.parent ? {x: node.parent.x, y: node.parent.y} : {x: node.x, y: 0};
      return {
        key: node.info.id.toString(),
        style: style,
        data: node
      };
    });

    // render the nodes using TransitionMotion
    const nodes = (
      <TransitionMotion
          willEnter={this.nodeWillEnter.bind(this)}
          willLeave={this.nodeWillLeave.bind(this)}
          defaultStyles={defaultNodesConfig}
          styles={nodesConfig}>
        {
          nodesConfig => {
            return (
              <g transform="translate(0,0)">
                {
                  nodesConfig.map(
                    config => {
                      return (
                        <g key={config.key} className="node"
                           transform={`translate(${config.style.x}, ${config.style.y})`}>
                          <circle onClick={this.handleCircleClick.bind(this, config.data)}
                                  r="10" style={{'fill': config.data._children ? 'lightsteelblue' : '#fff'}} />
                          <text y="-19" dy=".35em" textAnchor="middle"
                                style={{'fillOpacity': 1}}>{config.data.info.fullName}</text>
                          <image onClick={this.handleImageClick.bind(this, config.data)}
                                 href={config.data.info.picture} x="-20" y="-68"
                                 width="40px" height="40px"></image>
                          <Marriage marriages={config.data.marriage} />
                        </g>
                      );
                    }
                  )
                }
              </g>
            );
          }
        }
      </TransitionMotion>
    );

    return nodes;
  }


  /**
   * Handle image click to show modal
   * @param {object} person
   */
  handleImageClick(person) {
    const { tree } = this.context;
    tree.set('selectedPerson', person);
    tree.set('showDetail', true);
  }


  renderMarriage(config) {
    const marriages = config.data.marriage;

    return _.map(marriages, (marriage, i) => {
      return (
        <Marriage key={marriage.id} person={marriage} order={i} />
      );
    });
  }


  componentWillReceiveProps() {
    const oldNodesList = this.props.nodesList;
    this.setState({oldNodesList});
  }


  nodeWillEnter(node) {
    const nodesList = this.state.oldNodesList || this.props.nodesList;
    const { x, y } = this.findParent(node.data, nodesList);
    return { x, y };
  }


  nodeWillLeave(node) {
    const { nodesList } = this.props;
    const { x, y } = this.findParent(node.data, nodesList);
    return {
      x: spring(x),
      y: spring(y)
    };
  }


  /**
   * @param {object} node
   * @param {array} nodesList
   */
  findParent(node, nodesList) {
    let parent = _.find(nodesList, {id: node.parent.id});
    parent = parent ? parent : this.findParent(node.parent, nodesList);
    return parent;
  }


  /**
   * Handle click on circle to expand or collapse the tree
   * @param {object} d
   */
  handleCircleClick(d) {
    /* has no children, do nothing */
    if (!d.children && !d._children) {
      return;
    }

    const { tree } = this.context;
    const path = this.findPathInTree(d);
    const cursor = tree.select(path);
    const data = cursor.get();

    if (data.children) {
      cursor.set('_children', cursor.select('children').get());
      cursor.unset('children');
    } else {
      cursor.set('children', cursor.select('_children').get());
      cursor.unset('_children');
    }
  }


  /**
   * Input the person, find its path in the baobab tree to query
   * @param {object} d
   * @return {array}
   */
  findPathInTree(d) {
    let path;

    /* root node */
    if (d.path.length == 1) {
      path = ['pedigreeTree'];
      return path;
    }

    /* create the path to query in the baobab tree */
    path = _.tail(d.path);
    path = _.reduce(path, (result, id) => {
      result.push((d) => d.id == id);
      return result;
    }, []);
    path = _.zip(_.fill(Array(path.length), 'children'), path);
    path = _.flatten(path);
    path = _.compact(path);
    path.unshift('pedigreeTree');

    return path;
  }
}


NodesGroup.contextTypes = {
  tree: PropTypes.baobab
};

module.exports = NodesGroup;
