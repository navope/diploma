'use strict';

const alarmSound = document.getElementById('alarm-sound');
const temperatureValue = document.getElementById('temperature-value');
const pressureValue = document.getElementById('pressure-value');

const maxTemperature = 100; // Example threshold, replace with actual value
const maxPressure = 200; // Example threshold, replace with actual value

const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/interface'
});

stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/sensorsData', (message) => {
        const data = JSON.parse(message.body);
        updateSensorData(data);
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

stompClient.activate();

function updateSensorData(data) {
    const temperature = data.temperatureDTO.value;
    const pressure = data.pressureDTO.value;

    temperatureValue.textContent = `${temperature}°C`;
    pressureValue.textContent = `${pressure} Pa`;

    checkThresholds(temperature, pressure);
}

function checkThresholds(temperature, pressure) {
    if (temperature > maxTemperature) {
        document.getElementById('temperature').style.backgroundColor = 'red';
        playAlarm();
    } else {
        document.getElementById('temperature').style.backgroundColor = 'green';
    }

    if (pressure > maxPressure) {
        document.getElementById('pressure').style.backgroundColor = 'red';
        playAlarm();
    } else {
        document.getElementById('pressure').style.backgroundColor = 'green';
    }
}

function playAlarm() {
    alarmSound.play();
}
