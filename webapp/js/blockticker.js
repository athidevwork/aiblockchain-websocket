var wsUri = "ws://localhost:8083/wsticker";
var output;

function init() {
	output = document.getElementById("output");
	testWebSocket();
}

function testWebSocket() {
	websocket = new WebSocket(wsUri);
	websocket.onopen = function (evt) {
		onOpen(evt)
	};
	websocket.onclose = function (evt) {
		onClose(evt)
	};
	websocket.onmessage = function (evt) {
		onMessage(evt)
	};
	websocket.onerror = function (evt) {
		onError(evt)
	};
}

function onOpen(evt) {
	writeToScreen("CONNECTED");
	//doSend('{"command":"add", "tickerSymbol":"GOOG"}');
	//doSend('{"command":"add", "tickerSymbol":"F"}');
}

function onClose(evt) {
	writeToScreen("DISCONNECTED");
}

function onMessage(evt) {
	writeToScreen('<span style="color: blue;">RESPONSE: ' + evt.data + '</span>');
}

function onError(evt) {
	writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}

function doSend(message) {
	websocket.send(message);
	writeToScreen("SENT: " + message);
}

function sendAdd(symbol) {
	var message = '{"command":"add", "tickerSymbol":"' + symbol + '"}';
	websocket.send(message);
	writeToScreen("SENT: " + message);
}

function sendRemove(symbol) {
	var message = '{"command":"remove", "tickerSymbol":"' + symbol + '"}'
	websocket.send(message);
	writeToScreen("SENT: " + message);
}

function sendBlockNumber(symbol) {
	var message = '{"command":"startblock", "blockNumber":"' + symbol + '"}'
	websocket.send(message);
	writeToScreen("SENT: " + message);
}

function writeToScreen(message) {
	var pre = document.createElement("p");
	pre.style.wordWrap = "break-word";
	pre.innerHTML = message;
	output.appendChild(pre);
}

window.addEventListener("load", init, false);
