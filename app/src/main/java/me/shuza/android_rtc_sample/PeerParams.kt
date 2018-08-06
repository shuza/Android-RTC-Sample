package me.shuza.android_rtc_sample

import org.webrtc.*
import kotlin.properties.Delegates

/**
 *
 * :=  created by:  Shuza
 * :=  create date:  2/3/2018
 * :=  (C) CopyRight Shuza
 * :=  www.shuza.me
 * :=  shuza.sa@gmail.com
 * :=  Fun  :  Coffee  :  Code
 *
 **/
var connectedUser: String = ""
val videoConstrains = MediaConstraints()
val peerConnectionFactory = PeerConnectionFactory()
var peerConnection: PeerConnection by Delegates.notNull<PeerConnection>()
val localMS = peerConnectionFactory.createLocalMediaStream("ARDAMS")

//  Local video screen position before call is connected
val LOCAL_X_CONNECTING: Int = 0
val LOCAL_Y_CONNECTING: Int = 0
val LOCAL_WIDTH_CONNECTING: Int = 100
val LOCAL_HEIGHT_CONNECTING: Int = 100

//  Local video screen position after call is connected
val LOCAL_X_CONNECTED: Int = 72
val LOCAL_Y_CONNECTED: Int = 72
val LOCAL_WIDTH_CONNECTED: Int = 25
val LOCAL_HEIGHT_CONNECTED: Int = 25

//  Remote video screen position
val REMOTE_X = 0
val REMOTE_Y = 0
val REMOTE_WIDTH = 100
val REMOTE_HEIGHT = 0

fun initPeerConnection() {
    val iceServerList = ArrayList<PeerConnection.IceServer>()
    iceServerList.add(PeerConnection.IceServer("stun:stun.1.google.com:19302"))
    iceServerList.add(PeerConnection.IceServer("stun:23.21.150.121"))

    val peerConstraints = MediaConstraints()
    peerConstraints.mandatory.add(MediaConstraints.KeyValuePair("OfferToReceiveAudio", "true"))
    peerConstraints.mandatory.add(MediaConstraints.KeyValuePair("OfferToReceiveVideo", "true"))
    peerConstraints.mandatory.add(MediaConstraints.KeyValuePair("DtlsSrtpKeyAgreement", "true"))

    peerConnection = peerConnectionFactory.createPeerConnection(iceServerList, peerConstraints, object : PeerConnection.Observer {
        override fun onIceGatheringChange(p0: PeerConnection.IceGatheringState?) {

        }

        override fun onAddStream(p0: MediaStream?) {

        }

        override fun onIceCandidate(p0: IceCandidate?) {

        }

        override fun onDataChannel(p0: DataChannel?) {

        }

        override fun onSignalingChange(p0: PeerConnection.SignalingState?) {

        }

        override fun onRemoveStream(p0: MediaStream?) {

        }

        override fun onIceConnectionChange(p0: PeerConnection.IceConnectionState?) {

        }

        override fun onRenegotiationNeeded() {

        }
    })
}
