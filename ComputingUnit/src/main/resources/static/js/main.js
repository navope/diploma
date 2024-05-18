'use strict';

var dataArea = document.querySelector('#dataArea');

var stompClient = null;

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected, onError);
}

function onConnected() {
    // Subscribe to the Sensor Data Topic
    stompClient.subscribe('/topic/sensorData', onDataReceived);
}

function onError(error) {
    console.error('Could not connect to WebSocket server. Please refresh this page to try again!', error);
}

function onDataReceived(payload) {
    var data = JSON.parse(payload.body);

    var dataElement = document.createElement('li');

    var typeElement = document.createElement('span');
    var typeText = document.createTextNode(data.type + ": ");
    typeElement.appendChild(typeText);
    dataElement.appendChild(typeElement);

    var valueElement = document.createElement('span');
    var valueText = document.createTextNode(data.value + " at " + data.timestamp);
    valueElement.appendChild(valueText);
    dataElement.appendChild(valueElement);

    dataArea.appendChild(dataElement);
    dataArea.scrollTop = dataArea.scrollHeight;
}

// Connect to the WebSocket server
connect();