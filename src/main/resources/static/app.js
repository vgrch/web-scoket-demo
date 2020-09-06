var stompClient = null;
var username = '';

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    $("#name").prop("disabled", connected);
    username=$("#name").val();
    // if (connected) {
    //     $("#conversation").show();
    // }
    // else {
    //     $("#conversation").hide();
    // }
    // $("#greetings").html("");
}

function connect() {
    // var socket = new SockJS('/websocket-register', null, {transports: ['xhr-streaming'], headers: {'Authorization': 'Bearer jwt'}});
    var socket = new WebSocket('ws://localhost:8080/ws-register?jwt=NWZzMGIyM3J0MXl3a2x1MjNlcDh2YXozZzhrbG9kMDhmZHp4ZnJsNGh3aWpmNHBjeXd1cmF4ZThmbWxsdGN3enRhc3kzcjh5M3d4bTh3N3BxaWR0emljaDkwOG1sY29pbHRyNWswNnF5eWM5OWVuMzdsYTU5d3F3amx1ZHkwd2Q=');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/subscribe/'+username, function (greeting) {
            console.log(" Message : "+greeting);
            showGreeting(greeting.body);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    // stompClient.send("/app/send."+username, {}, JSON.stringify({'content': $("#message").val()}));
    stompClient.send("/app/send."+username, {}, JSON.stringify({}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});