import React, { Component } from 'react';
import HTTPClientService from './HTTPClientService';

export default class TabInputComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      tabContent: '',
      artist: '',
      title: ''
    };

    this.client = new HTTPClientService()
    this.handleTitleChange = this.handleTitleChange.bind(this);
    this.handleTabChange = this.handleTabChange.bind(this);
    this.handleArtistChange = this.handleArtistChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleTabChange(event) {
    this.setState({tabContent: event.target.value});
  }
  handleArtistChange(event) {
    this.setState({artist: event.target.value});
  }

  handleTitleChange(event) {
      this.setState({title: event.target.value});
  }
  handleSubmit(event) {
    this.client.sendToServer({
    "artist": this.state.artist,
    "title": this.state.title,
    "tab": this.state.tabContent
    })
    event.preventDefault();
  }

  render() {
    return (
      <form onSubmit={this.handleSubmit}>
        <div>
        <label>
          Tab Creation Form:
        </label>
        </div>
        <div>
            Artist: <input value={this.state.artist} onChange={this.handleArtistChange} placeholder='Song artist' />
        </div>
        <div>
            Song Title: <input value={this.state.title} onChange={this.handleTitleChange} placeholder='Song title' />
        </div>
        <div>
         <textarea value={this.state.tabContent} onChange={this.handleTabChange} placeholder='Write your tab here'
         rows = '50' cols = '100'/>
         </div>
        <input type="submit" value="Submit" />
      </form>
    );
  }
}