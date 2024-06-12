let socket;

// Initialize WebSocket connection
function initWebSocket() {
    socket = new WebSocket("ws://localhost:8080/ws");

    socket.onopen = function (event) {
        console.log("WebSocket is open now.");
        appendMessage("WebSocket connection opened.");
    };

    socket.onmessage = function (event) {
        console.log("WebSocket message received: ", event.data);
        appendMessage(`Server: ${event.data}`);
    };

    socket.onclose = function (event) {
        console.log("WebSocket is closed now.");
        appendMessage("WebSocket connection closed.");
    };

    socket.onerror = function (error) {
        console.log("WebSocket error: ", error);
        appendMessage(`Error: ${error}`);
    };
}

// Send message through WebSocket
function sendMessage() {
    const input = document.getElementById("messageInput");
    const message = input.value;
    if (socket.readyState === WebSocket.OPEN) {
        socket.send(message);
        appendMessage(`You: ${message}`);
        input.value = "";
    } else {
        console.log("WebSocket is not open. Unable to send message.");
        appendMessage("WebSocket is not open. Unable to send message.");
    }
}

// Append message to the list
function appendMessage(message) {
    const messagesList = document.getElementById("messages");
    const li = document.createElement("li");
    li.textContent = message;
    messagesList.appendChild(li);
}

// Initialize WebSocket when the page loads
window.onload = function () {
    initWebSocket();
};
