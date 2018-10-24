import React, { Component } from 'react';
import { Stomp } from '@stomp/stompjs';
import logo from './logo.svg';
import './App.css';

class App extends Component {

  state = {
    serverTime: null,
  }

  componentDidMount() {
    console.log('Component did mount');
    this.client = Stomp.client('ws://localhost:8080/stomp');

    this.client.onConnect(() => {
      console.log('onConnect');

      this.client.subscribe('/queue/now', message => {
        console.log(message);
        this.setState({serverTime: message.body});
      });

      this.client.subscribe('/topic/greetings', message => {
        alert(message.body);
      });
    });

    this.client.activate();
  }

  clickHandler = () => {
    this.client.publish({destination: '/app/greetings', body: 'Hello world'});
  }

  render() {
    return (
      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <p>
            Edit <code>src/App.js</code> and save to reload.
          </p>
          <p>
            Server time: {this.state.serverTime ? this.state.serverTime : 'no data'}
          </p>
          <p>
            <button onClick={this.clickHandler}>Click me</button>
          </p>
          <a
            className="App-link"
            href="https://reactjs.org"
            target="_blank"
            rel="noopener noreferrer"
          >
            Learn React
          </a>
        </header>
      </div>
    );
  }
}

export default App;
