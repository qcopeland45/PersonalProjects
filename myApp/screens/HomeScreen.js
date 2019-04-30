import React from 'react';
import {
  Image,
  Platform,
  ScrollView,
  StyleSheet,
  Text,
  TouchableOpacity,
  Button,
  View,
} from 'react-native';
import { WebBrowser } from 'expo';
import { MonoText } from '../components/StyledText';
import { TextInput } from 'react-native-gesture-handler';

export default class HomeScreen extends React.Component {
  static navigationOptions = {
    header: null,
  };
  //My CODE
  onPressLearnMore = () => {
    console.log("do more stuff")
  }

  render() {
    return (
      //My CODE
    <View style={{ flex: 1, justifyContent: "center", alignItems: "center", backgroundColor: "#63c2e8", }}>
        <Text>Quincy's Test flight app</Text>
     <View style={{backgroundColor: 'black'}}>
       <Button
          onPress={this.onPressLearnMore}
          title="Learn More"
          size={15}
          type="outline"
          color="white"
          
          accessibilityLabel="Learn more about this purple button"
        />
      </View>
    </View>
    );
  }

  _maybeRenderDevelopmentModeWarning() {
    if (__DEV__) {
      const learnMoreButton = (
        <Text onPress={this._handleLearnMorePress} style={styles.helpLinkText}>
          Learn more
        </Text>
      );

      return (
        <Text style={styles.developmentModeText}>
          Development mode is enabled, your app will be slower but you can use useful development
          tools. {learnMoreButton}
        </Text>
      );
    } else {
      return (
        <Text style={styles.developmentModeText}>
          You are not in development mode, your app will run at full speed.
        </Text>
      );
    }
  }

  _handleLearnMorePress = () => {
    WebBrowser.openBrowserAsync('https://docs.expo.io/versions/latest/guides/development-mode');
  };

  _handleHelpPress = () => {
    WebBrowser.openBrowserAsync(
      'https://docs.expo.io/versions/latest/guides/up-and-running.html#can-t-see-your-changes'
    );
  };
}

const styles = StyleSheet.create({
  //something I changed 
  container: {
    
  },

});
