'use strict';

var nameInput = $('#name');
var roomInput = $('#room-id');
var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
//var connectingElement = document.querySelector('.connecting');
var roomIdDisplay = document.querySelector('#room-id-display');


var stompClient = null;
var currentSubscription;
var username = null;
var roomId = null;
var topic = null;
 

function connect(event) {
    //username = document.querySelector('#username').innerText.trim();
    username = document.querySelector('#username').innerText.trim();
  	Cookies.set('name', username);
    //roomId = "123";
    if (username) {
		usernamePage.classList.add('hidden');
    	chatPage.classList.remove('hidden');

    	var socket = new SockJS('/ws');
    	stompClient = Stomp.over(socket);

    	stompClient.connect({}, onConnected, onError);
	}
    
    event.preventDefault();
}

// Connect to WebSocket Server.
//connect();

function enterRoom(newRoomId) {
  roomId = newRoomId;
  Cookies.set('roomId', roomId);
  roomIdDisplay.textContent = roomId;
  topic = `/app/chat/${newRoomId}`;

  if (currentSubscription) {
    currentSubscription.unsubscribe();
  }
  currentSubscription = stompClient.subscribe(`/channel/${roomId}`, onMessageReceived);

  stompClient.send(`${topic}/addUser`,
    {},
    JSON.stringify({sender: username, type: 'JOIN'})
  );
}

function onConnected() {
	enterRoom(roomInput.val());
  	//connectingElement.classList.add('hidden');
}


function onError(error) {
   // connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
   // connectingElement.style.color = 'red';
}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT'
        };
        stompClient.send(`${topic}/sendMessage`, {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined to ' + roomId + '!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else {
        messageElement.classList.add('chat-message');
        
        
        var usernameElement = document.createElement('strong');
        usernameElement.classList.add('nickname');   
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    var textElement = document.createElement('span');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

function create_UUID() {
    var dt = new Date().getTime();
    var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = (dt + Math.random()*16)%16 | 0;
        dt = Math.floor(dt/16);
        return (c=='x' ? r :(r&0x3|0x8)).toString(16);
    });
    return uuid;
} 
 
$(document).ready(function() {
  var savedName = Cookies.get('name');
  if (savedName) {
    nameInput.val(savedName);
  }

  var savedRoom = Cookies.get('roomId');
  if (savedRoom) {
    roomInput.val(savedRoom);
  }

  usernamePage.classList.remove('hidden');
  usernameForm.addEventListener('submit', connect, true);
  messageForm.addEventListener('submit', sendMessage, true);
});