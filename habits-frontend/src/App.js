import logo from './logo.svg';
/*import './App.css';
import {Amplify} from 'aws-amplify'
import config from '../src/aws-exports'

import {AmplifyProvider} from '@aws-amplify/ui-react'
import {studioTheme} from '../src/ui-components'

import '@aws-amplify/ui-react/styles.css'

Amplify.configure(config)

function App() {
  return (
    <AmplifyProvider theme={studioTheme}>
      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <p>
            Edit <code>src/App.js</code> and save to reload.
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
    </AmplifyProvider>
  );
export default App;
}*/
import "@aws-amplify/ui-react/styles.css";
import {
  withAuthenticator,
  Button,
  Heading,
  Image,
  View,
  Card,
} from "@aws-amplify/ui-react";

function App({ signOut }) {
  return (
    <View className="App">
      <Card>
        <Image src={logo} className="App-logo" alt="logo" />
        <Heading level={1}>We now have Auth!</Heading>
      </Card>
      <Button onClick={signOut}>Sign Out</Button>
    </View>
  );
}

export default withAuthenticator(App);
