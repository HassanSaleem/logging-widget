import React from 'react';
import SockJsClient from 'react-stomp';

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      clientConnected: false,
      logs: [ ],
      columns: [
        {key: 'level', label: 'Level'},
        {key: 'count', label: 'Count'}
      ]
    };
  }

  onMessageReceive = (msg, topic) => {
    this.setState(prevState => ({
      logs: msg
    }));
  }

  render() {
    const wsSourceUrl = window.location.protocol + "//" + window.location.host + "/logs";
    console.log(wsSourceUrl)
    return (
      <div>
        <SockJsClient url={ wsSourceUrl } topics={["/user/notification/logs"]}
          onMessage={ this.onMessageReceive } ref={ (client) => { this.clientRef = client }}
          onConnect={ () => { 
            this.setState({ clientConnected: true }) 
            this.clientRef.sendMessage("/app/start")
          } }
          onDisconnect={ () => { this.setState({ clientConnected: false }) } }
          debug={ true }/>
          <table>
            <tr>
              <th>LogLevel</th>
              <th>Count</th>
            </tr>
            {this.state.logs.map(log => {
              return (
                <tr>
                  <th>{log.level}</th>
                  <th>{log.count}</th>
                </tr>
              )
            })}
          </table>
      </div>
    );
  }
}

export default App