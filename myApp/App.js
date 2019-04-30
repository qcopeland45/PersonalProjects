import React from 'react';
import { Platform, StatusBar, StyleSheet, View } from 'react-native';
import { AppLoading, Asset, Font, Icon } from 'expo';
import AppNavigator from './navigation/AppNavigator';
import Main from './components/main';
export default class App extends React.Component {
  state = {
    isLoadingComplete: false,
  };
  render() {
 
    //My code 
    return (
      <Main />
    );
  }

  
}


