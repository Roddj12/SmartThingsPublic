/**
 *  Dimmer-Sonos Bridge
 *
 *  Copyright 2016 Jesse Silverberg
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "Dimmer-Sonos Bridge",
    namespace: "JLS",
    author: "Jesse Silverberg",
    description: "Bridge a virtual dimmer to a Sonos for Alexa volume control.",
    category: "Convenience",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
	section("Bridge this dimmer...") {
		input "myDimmer", "capability.switchLevel", multiple: false, title: "Choose virtual dimmer", required: true
	}
	section("...to this speaker") {
        input "mySpeaker", "capability.musicPlayer", title: "Choose Speaker(s)", multiple: true, required: true 
    } 
}


def installed() {
	log.debug "Installed with settings: ${settings}"
	subscribe(myDimmer, "level", setNewVolume)
}


def updated() {
	log.debug "Updated with settings: ${settings}"
	unsubscribe()
	subscribe(myDimmer, "level", setNewVolume)

}


def setNewVolume(evt) {

	log.debug "in handler with: ${evt.value}"
	def newVolume = myDimmer.currentState("level").integerValue
	mySpeaker.setLevel(newVolume)

}